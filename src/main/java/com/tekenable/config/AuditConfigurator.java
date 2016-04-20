package com.tekenable.config;

import com.tekenable.model.audit.TrialDirectColDefMapper;
import com.tekenable.model.audit.TrialDirectTableDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditConfigurator {

    public static final Logger log = LoggerFactory.getLogger(AuditConfigurator.class);

    @Autowired
    protected DataSource dataSource;

    public void addAuditStuff() {
        System.out.println("*** Audit Configuration health check ***");

        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        /*
         this is the main query obtaining all production table names
         used for iteration
         */
        String tablesSQL = "select table_name from information_schema.tables \n" +
                "where table_schema = 'trialdirect'\n" +
                "and table_name not in ('hibernate_sequences')\n" +
                "and table_name not like '%_audit'";
        List<String> tables = jdbc.queryForList(tablesSQL, String.class);

        Map<String, String> operations = new HashMap();
        operations.put("_AI", "INSERT");
        operations.put("_AU", "UPDATE");
        operations.put("_AD", "DELETE");

        for (String table : tables) {
            String auditTableName = table.concat("_audit");

            /*
             this query gets all columns of processed audit table
             */
            String auditTabColumnsSQL = "select column_name, concat(column_name, ' ', column_type) 'column_type' from information_schema.columns \n" +
                    "where table_schema = 'trialdirect' and table_name = '#TAB#'\n" +
                    "and column_name not in ('action', 'dbUser', 'appUser', 'createdTs')\n" +
                    "order by ordinal_position";
            auditTabColumnsSQL = auditTabColumnsSQL.replace("#TAB#", auditTableName);
            List<TrialDirectTableDef> auditColumnsDef = jdbc.query(auditTabColumnsSQL, new TrialDirectColDefMapper());

            /*
             this query gets all columns of production table
             */
            String tabColumnsSQL = "select column_name, concat(column_name, ' ', column_type) 'column_type' from information_schema.columns\n" +
                    "where table_schema = 'trialdirect' and table_name = '#TAB#' order by ordinal_position";
            tabColumnsSQL = tabColumnsSQL.replace("#TAB#", table);
            List<TrialDirectTableDef> actualColumnsDef = jdbc.query(tabColumnsSQL, new TrialDirectColDefMapper());

            /*
             this functionality assumes that all the changes made to the table are the same kind
             it will freak out if there were multiple changes of a different kind
             for instance: 1 column was dropped and 2 extra columns added - such situation is not currently supported
             */

            StringBuilder asb = new StringBuilder();

            if (actualColumnsDef.size()>auditColumnsDef.size()) {
                actualColumnsDef.removeAll(auditColumnsDef);
                for (TrialDirectTableDef column : actualColumnsDef) {
                    log.info("Table modification detected in table:" + table);
                    asb.append("alter table ").append(auditTableName).append(" add ").append(column.getColumnDef());
                    int result = jdbc.update(asb.toString());
                    column.setApplied(result==0 ? true : false);
                    log.info("Add column: "+table+column.toString());
                }

            }
            else if (actualColumnsDef.size()<auditColumnsDef.size()) {
                auditColumnsDef.removeAll(actualColumnsDef);
                for (TrialDirectTableDef column : auditColumnsDef) {
                    log.info("Table modification detected in table:" + table);
                    asb.append("alter table ").append(auditTableName).append(" drop ").append(column.getColumnName());
                    int result = jdbc.update(asb.toString());
                    column.setApplied(result==0 ? true : false);
                    log.info("Drop column: "+table+column.toString());
                }
            }
            else {
                actualColumnsDef.removeAll(auditColumnsDef);
                for (TrialDirectTableDef column : actualColumnsDef) {
                    if (actualColumnsDef.size()>0)
                        log.info("Table modification detected in table:" + table);

                    asb.append("alter table ").append(auditTableName).append(" modify ").append(column.getColumnDef());
                    int result = jdbc.update(asb.toString());
                    column.setApplied(result==0 ? true : false);
                    log.info("Modify column: "+table+column.toString());
                }
            }

            /*
             recreating table triggers
             */
            log.info("Recreating audit triggers - to make it easy they are just drop and created again");
            for (Map.Entry<String, String> operation : operations.entrySet()) {
                String dropQuery = "drop trigger if exists ".concat(table).concat(operation.getKey());
                int result = jdbc.update(dropQuery);

                StringBuilder tsb = new StringBuilder();
                tsb.append("create trigger ").append(table).append(operation.getKey());
                tsb.append(" after ").append(operation.getValue());
                tsb.append(" on ").append(table).append(" for each row ");
                tsb.append("begin insert into ").append(auditTableName);

                StringBuilder csb = new StringBuilder();
                csb.append("(");
                StringBuilder vsb = new StringBuilder();
                vsb.append(" values(");
                for (TrialDirectTableDef column : actualColumnsDef) {
                    csb.append(column.getColumnName()).append(", ");
                    vsb.append(operation.getValue().equals("DELETE") ? "old." : "new.");
                    vsb.append(column.getColumnName()).append(", ");
                }
                csb.append("action, dbUser, appUser, createdTs)");
                vsb.append("'").append(operation.getValue()).append("', user(), null, CURRENT_TIMESTAMP()); end;");

                tsb.append(csb.toString());
                tsb.append(vsb.toString());

                result = jdbc.update(tsb.toString());
                log.info(String.format("create audit trigger for %1$s executed with result: %2$d", table, result));
            }
        }
        log.info("*** Audit Configuration updated ***");
    }
}

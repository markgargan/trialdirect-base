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

    public void checkAuditConfiguration() {
        log.info("*** Audit Configuration health check ***");

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
             this query gets all columns of production table for inner iteration
             */
            String tabColumnsSQL = "select column_name, concat(column_name, ' ', column_type) 'column_type' from information_schema.columns\n" +
                                   "where table_schema = 'trialdirect' and table_name = ? order by ordinal_position";            ;
            List<TrialDirectTableDef> actualColumnsDef = jdbc.query(tabColumnsSQL, new Object[]{table}, new TrialDirectColDefMapper());

            String auditTablesSQL = "select count(*) from information_schema.tables \n" +
                                    "where table_schema = 'trialdirect'\n" +
                                    "and table_name = ?";

            String auditTabColumnSQL = "select column_name, concat(column_name, ' ', column_type) 'column_type' \n" +
                                       "from information_schema.columns \n" +
                                       "where table_schema = 'trialdirect' \n" +
                                       "and table_name = ? \n" +
                                       "and column_name = ? ";

            /*
            checking if audit table exists in the first place
             */
            Integer tabExists = jdbc.queryForObject(auditTablesSQL, new Object[]{auditTableName}, Integer.class);
            if (tabExists==0) { //audit table does not exist so create it
                StringBuilder asb = new StringBuilder();
                asb.append("create table ").append(auditTableName).append("(");
                for (TrialDirectTableDef srcColumn : actualColumnsDef) {
                    asb.append(srcColumn.getColumnDef()).append(", ");
                }
                asb.append("action varchar(10),");
                asb.append("dbUser varchar(255), ");
                asb.append("appUser varchar(255), ");
                asb.append("createdTs timestamp)");
                int result = jdbc.update(asb.toString());
                log.info(String.format("create audit table %1$s executed with result: %2$d", auditTableName, result));
            }
            else {
                /*
                field comparison is executed only for existing tables only
                 */
                for (TrialDirectTableDef srcColumn : actualColumnsDef) {
                    StringBuilder asb = new StringBuilder();

                    Object[] args = new Object[]{auditTableName, srcColumn.getColumnName()};
                    List<TrialDirectTableDef> response = jdbc.query(auditTabColumnSQL, args, new TrialDirectColDefMapper());

                    if (response == null || response.isEmpty()) { // column does not exist yet so create it
                        asb.append("alter table ").append(auditTableName).append(" add ").append(srcColumn.getColumnDef());
                        int result = jdbc.update(asb.toString());
                        srcColumn.setApplied((result == 0));
                        log.info("Add column: " + table + srcColumn.toString());
                    } else { // column exist so check its type and modify if different
                        TrialDirectTableDef resColumn = response.get(0);
                        if (!resColumn.getColumnDef().equals(srcColumn.getColumnDef())) {
                            asb.append("alter table ").append(auditTableName).append(" modify ").append(srcColumn.getColumnDef());
                            int result = jdbc.update(asb.toString());
                            srcColumn.setApplied((result == 0));
                            log.info("Modify column: " + table + srcColumn.toString());
                        }
                    }
                }
            }
            /*
             recreating table triggers - to make it easy they are just drop and created again
             */
            log.info("Recreating audit triggers");
            for (Map.Entry<String, String> operation : operations.entrySet()) {
                StringBuilder dsb = new StringBuilder();
                dsb.append("drop trigger if exists ").append(table).append(operation.getKey());
                int result = jdbc.update(dsb.toString());

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

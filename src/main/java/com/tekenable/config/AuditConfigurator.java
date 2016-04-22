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

    private JdbcTemplate jdbc;

    /*
     this is the main query obtaining all production table names
     used for iteration
     */
    protected List getAuditedTables() {
        String tablesSQL = "select table_name from information_schema.tables \n" +
                           "where table_schema = 'trialdirect'\n" +
                           "and table_name not in ('hibernate_sequences')\n" +
                           "and table_name not like '%_audit'";

        return this.jdbc.queryForList(tablesSQL, String.class);
    }

    /*
     this query gets all columns of the table
     */
    protected List getTableColumns(String tableName) {
        String tabColumnsSQL = "select column_name, concat(column_name, ' ', column_type) 'column_type' " +
                               "from information_schema.columns\n" +
                               "where table_schema = 'trialdirect' and table_name = ? order by ordinal_position";

        return this.jdbc.query(tabColumnsSQL, new Object[]{tableName}, new TrialDirectColDefMapper());
    }

    protected int createAuditTable(String tableName, List<TrialDirectTableDef> columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ").append(tableName).append("(");
        for (TrialDirectTableDef srcColumn : columns) {
            sb.append(srcColumn.getColumnDef()).append(", ");
        }
        sb.append("action varchar(10),");
        sb.append("dbUser varchar(255), ");
        sb.append("appUser varchar(255), ");
        sb.append("createdTs timestamp)");

        return this.jdbc.update(sb.toString());
    }

    protected TrialDirectTableDef getColumnDefinition(String tableName, String columnName) {
        String auditTabColumnSQL = "select column_name, concat(column_name, ' ', column_type) 'column_type' \n" +
                                   "from information_schema.columns \n" +
                                   "where table_schema = 'trialdirect' \n" +
                                   "and table_name = ? \n" +
                                   "and column_name = ? ";

        List<TrialDirectTableDef> response = jdbc.query(auditTabColumnSQL, new Object[]{tableName, columnName}, new TrialDirectColDefMapper());
        return (response==null || response.isEmpty()) ? null : response.get(0);
    }

    protected int alterAuditColumn(String mode, String tableName, String columnDefinition) {
        StringBuilder sb = new StringBuilder();
        sb.append("alter table ").append(tableName).append(" ").append(mode).append(" ").append(columnDefinition);
        return jdbc.update(sb.toString());
    }

    protected int recreateTrigger(String trigerTable, String auditTable, Map.Entry operation, List<TrialDirectTableDef> columns) {
        // dropping existing trigger
        StringBuilder dsb = new StringBuilder();
        dsb.append("drop trigger if exists ").append(trigerTable).append(operation.getKey());
        jdbc.update(dsb.toString());
        // creating new one from scratch
        StringBuilder tsb = new StringBuilder();
        tsb.append("create trigger ").append(trigerTable).append(operation.getKey());
        tsb.append(" after ").append(operation.getValue());
        tsb.append(" on ").append(trigerTable).append(" for each row ");
        tsb.append("begin insert into ").append(auditTable);

        StringBuilder csb = new StringBuilder();
        csb.append("(");
        StringBuilder vsb = new StringBuilder();
        vsb.append(" values(");
        for (TrialDirectTableDef column : columns) {
            csb.append(column.getColumnName()).append(", ");
            vsb.append(operation.getValue().equals("DELETE") ? "old." : "new.");
            vsb.append(column.getColumnName()).append(", ");
        }
        csb.append("action, dbUser, appUser, createdTs)");
        vsb.append("'").append(operation.getValue()).append("', user(), null, CURRENT_TIMESTAMP()); end;");

        tsb.append(csb.toString());
        tsb.append(vsb.toString());

        return jdbc.update(tsb.toString());
    }

    public void checkAuditConfiguration() {
        log.info("*** Audit Configuration health check ***");
        this.jdbc = new JdbcTemplate(dataSource);

        List<String> tables = getAuditedTables();

        Map<String, String> operations = new HashMap();
        operations.put("_AI", "INSERT");
        operations.put("_AU", "UPDATE");
        operations.put("_AD", "DELETE");

        for (String table : tables) {
            String auditTableName = table.concat("_audit");

            List<TrialDirectTableDef> actualColumnsDef = getTableColumns(table);

            String auditCountSQL = "select count(*) from information_schema.tables \n" +
                                   "where table_schema = 'trialdirect'\n" +
                                   "and table_name = ?";

            /*
            checking if audit table exists in the first place
             */
            Integer tabExists = jdbc.queryForObject(auditCountSQL, new Object[]{auditTableName}, Integer.class);
            if (tabExists==0) { //audit table does not exist so create it
                int result = createAuditTable(auditTableName, actualColumnsDef);
                log.info(String.format("create audit table %1$s executed with result: %2$d", auditTableName, result));
            }
            else {
                /*
                field comparison is executed only for existing tables only
                 */
                for (TrialDirectTableDef srcColumn : actualColumnsDef) {

                    TrialDirectTableDef resColumn = getColumnDefinition(auditTableName, srcColumn.getColumnName());

                    if (resColumn == null) {
                        // column does not exist yet so create it
                        int result = alterAuditColumn("add", auditTableName, srcColumn.getColumnDef());
                        srcColumn.setApplied((result == 0));
                        log.info("Add column: " + table + srcColumn.toString());
                    } else {
                        // column exist so check its type and modify if different
                        if (!resColumn.getColumnDef().equals(srcColumn.getColumnDef())) {
                            int result = alterAuditColumn("modify", auditTableName, srcColumn.getColumnDef());
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
                int result = recreateTrigger(table, auditTableName, operation, actualColumnsDef);
                log.info(String.format("create audit trigger for %1$s executed with result: %2$d", table, result));
            }
        }
        log.info("*** Audit Configuration updated ***");
    }
}

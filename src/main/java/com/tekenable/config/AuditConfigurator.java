package com.tekenable.config;

import com.tekenable.model.audit.TrialDirectColDefMapper;
import com.tekenable.model.audit.TrialDirectTableDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditConfigurator {

    public static final Logger log = LoggerFactory.getLogger(AuditConfigurator.class);

    @Autowired
    protected DataSource dataSource;

    protected JdbcTemplate jdbc;

    @PostConstruct
    private void init() {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    /**
     * this is for testing purpose
     * @param jdbc
     */
    protected boolean setJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        return this.jdbc != null;
    }

    protected int runQuery(String query) {
        return this.jdbc.update(query);
    }

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

    protected TrialDirectTableDef getColumnDefinition(String tableName, String columnName) {
        String auditTabColumnSQL = "select column_name, concat(column_name, ' ', column_type) 'column_type' \n" +
                                   "from information_schema.columns \n" +
                                   "where table_schema = 'trialdirect' \n" +
                                   "and table_name = ? \n" +
                                   "and column_name = ? ";

        List<TrialDirectTableDef> response = jdbc.query(auditTabColumnSQL, new Object[]{tableName, columnName}, new TrialDirectColDefMapper());
        return (response==null || response.isEmpty()) ? null : response.get(0);
    }

    protected String getCreateTableQuery(String tableName, List<TrialDirectTableDef> columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ").append(tableName).append("(");
        for (TrialDirectTableDef srcColumn : columns) {
            sb.append(srcColumn.getColumnDef()).append(", ");
        }
        sb.append("action varchar(10),");
        sb.append("dbUser varchar(255), ");
        sb.append("appUser varchar(255), ");
        sb.append("createdTs timestamp)");

        return sb.toString();
    }

    protected String getAlterTableQuery(String mode, String tableName, String columnDefinition) {
        StringBuilder sb = new StringBuilder();
        sb.append("alter table ").append(tableName).append(" ").append(mode).append(" ").append(columnDefinition);
        return sb.toString();
    }

    protected String getDropTriggerQuery(String trigerTable, String triggerSuffix) {
        return "drop trigger if exists ".concat(trigerTable).concat(triggerSuffix);
    }

    protected String getCreateTriggerQuery(String trigerTable, String auditTable, Map.Entry operation, List<TrialDirectTableDef> columns) {
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

        return tsb.toString();
    }

    protected int checkTablePresence(String tableName) {
        String auditCountSQL = "select count(*) from information_schema.tables \n" +
                "where table_schema = 'trialdirect'\n" +
                "and table_name = ?";

        return jdbc.queryForObject(auditCountSQL, new Object[]{tableName}, Integer.class);
    }

    public Map checkAuditConfiguration() {
        log.info("*** Audit Configuration health check ***");
        Map<String, List<String>> auditSqlLog = new HashMap();

        List<String> tables = getAuditedTables();

        List<String> createTableSQL = new ArrayList<String>();
        List<String> alterTableSQL = new ArrayList<String>();
        List<String> recreateTriggerSQL = new ArrayList<String>();


        Map<String, String> operations = new HashMap();
        operations.put("_AI", "INSERT");
        operations.put("_AU", "UPDATE");
        operations.put("_AD", "DELETE");

        for (String table : tables) {
            String auditTableName = table.concat("_audit");

            List<TrialDirectTableDef> actualColumnsDef = getTableColumns(table);

            /*
            checking if audit table exists in the first place
             */
            Integer tabExists = checkTablePresence(auditTableName);
            if (tabExists==0) { //audit table does not exist so create it
                String sql = this.getCreateTableQuery(auditTableName, actualColumnsDef);
                createTableSQL.add(sql);
                int result = this.runQuery(sql);
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
                        String addColumnString = getAlterTableQuery("add", auditTableName, srcColumn.getColumnDef());
                        alterTableSQL.add(addColumnString);
                        int result = this.runQuery(addColumnString);
                        srcColumn.setApplied((result == 0));
                        log.info("Add column: " + table + srcColumn.toString());
                    }
                    else {
                        // column exist so check its type and modify if different
                        if (!resColumn.getColumnDef().equals(srcColumn.getColumnDef())) {
                            String modifyColumnSQL = getAlterTableQuery("modify", auditTableName, srcColumn.getColumnDef());
                            alterTableSQL.add(modifyColumnSQL);
                            int result = this.runQuery(modifyColumnSQL);
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
            int result=0;
            for (Map.Entry<String, String> operation : operations.entrySet()) {

                String sql = this.getDropTriggerQuery(table, operation.getKey());
                recreateTriggerSQL.add(sql);
                result = result + this.runQuery(sql);

                sql = this.getCreateTriggerQuery(table, auditTableName, operation, actualColumnsDef);
                recreateTriggerSQL.add(sql);
                result = result + this.runQuery(sql);

                log.info(String.format("create audit trigger for %1$s executed with result: %2$d", table, result));
            }
        }
        log.info("*** Audit Configuration updated ***");

        auditSqlLog.put("AuditedTables", createTableSQL);
        auditSqlLog.put("TablesAltered", alterTableSQL);
        auditSqlLog.put("TriggersRecreated", recreateTriggerSQL);

        return auditSqlLog;
    }
}

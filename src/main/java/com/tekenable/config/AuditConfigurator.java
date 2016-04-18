package com.tekenable.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditConfigurator {

    private class TrialDirectTableDef {
        private String columnName;
        private String columnDef;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnDef() {
            return columnDef;
        }

        public void setColumnDef(String columnDef) {
            this.columnDef = columnDef;
        }
    }

    private class TrialDirectColDefMapper implements RowMapper {

        @Override
        public TrialDirectTableDef mapRow(ResultSet resultSet, int i) throws SQLException {
            TrialDirectTableDef def = new TrialDirectTableDef();
            def.setColumnName(resultSet.getString("column_name"));
            def.setColumnDef(resultSet.getString("column_type"));
            return def;
        }
    }

    @Autowired
    protected DataSource dataSource;

    public void addAuditStuff() {
        System.out.println("*** Audit Configurator START ***");

        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        /*String auditTablesSQL = "select table_name from information_schema.tables \n" +
                                "where table_schema = 'trialdirect' \n" +
                                "and table_name like '%_audit'";
        List<String> auditTables = jdbc.queryForList(auditTablesSQL, String.class);
        for (String table : auditTables) {
            int result = jdbc.update("drop table if exists ".concat(table));
            System.out.println(String.format("drop table %1$s executed with result: %2$d", table, result));
        }*/

        String tablesSQL = "select table_name from information_schema.tables \n" +
                           "where table_schema = 'trialdirect'\n" +
                           "and table_name not in ('hibernate_sequences')";
        List<String> tables = jdbc.queryForList(tablesSQL, String.class);

        Map<String, String> operations = new HashMap();
        operations.put("_AI", "INSERT");
        operations.put("_AU", "UPDATE");
        operations.put("_AD", "DELETE");

        for (String table : tables) {
            String auditTableName = table.concat("_audit");

            String auditTabColumnsSQL = "select column_name, concat(column_name, ' ', column_type) 'column_type' from information_schema.columns \n" +
                                        "where table_schema = 'trialdirect' and table_name = '#TAB#' order by ordinal_position";
            auditTabColumnsSQL = auditTabColumnsSQL.replace("#TAB#", auditTableName);
            List<TrialDirectTableDef> auditColumnsDef = jdbc.query(auditTabColumnsSQL, new TrialDirectColDefMapper());

            String tabColumnsSQL = "select column_name, concat(column_name, ' ', column_type) from information_schema.columns\n" +
                                   "where table_schema = 'trialdirect' and table_name = '#TAB#' order by ordinal_position";
            tabColumnsSQL = tabColumnsSQL.replace("#TAB#", table);
            List<TrialDirectTableDef> actualColumnsDef = jdbc.query(tabColumnsSQL, new TrialDirectColDefMapper());

            actualColumnsDef.retainAll(auditColumnsDef);




            StringBuilder asb = new StringBuilder();
            asb.append("create table ").append(auditTableName).append("(");
            for (String column : columnsDef) {
                asb.append(column).append(", ");
            }
            asb.append("action varchar(10),");
            asb.append("dbUser varchar(255), ");
            asb.append("appUser varchar(255), ");
            asb.append("createdTs timestamp)");
            int result = jdbc.update(asb.toString());
            System.out.println(String.format("create table %1$s executed with result: %2$d", auditTableName, result));
            if (result==0) {
                for (Map.Entry<String, String> operation : operations.entrySet()) {
                    StringBuilder tsb = new StringBuilder();
                    tsb.append("create trigger ").append(table).append(operation.getKey());
                    tsb.append(" after ").append(operation.getValue());
                    tsb.append(" on ").append(table).append(" for each row ");
                    tsb.append("begin insert into ").append(auditTableName);

                    StringBuilder csb = new StringBuilder();
                    csb.append("(");
                    StringBuilder vsb = new StringBuilder();
                    vsb.append(" values(");
                    for (String column : columnNames) {
                        csb.append(column).append(", ");
                        vsb.append(operation.getValue().equals("DELETE") ? "old." : "new.");
                        vsb.append(column).append(", ");
                    }
                    csb.append("action, dbUser, appUser, createdTs)");
                    vsb.append("'").append(operation.getValue()).append("', user(), null, CURRENT_TIMESTAMP()); end;");

                    tsb.append(csb.toString());
                    tsb.append(vsb.toString());

                    result = jdbc.update(tsb.toString());
                    System.out.println(String.format("create audit trigger for %1$s executed with result: %2$d", table, result));
                }
            }
        }
        System.out.println("*** Audit Configurator END ***");
    }
}

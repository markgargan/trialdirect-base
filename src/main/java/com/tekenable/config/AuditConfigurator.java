package com.tekenable.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditConfigurator {

    @Autowired
    protected DataSource dataSource;

    public void addAuditStuff() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        String tablesSQL = "select table_name from information_schema.tables \n" +
                           "where table_schema = 'trialdirect'\n" +
                           "and table_name not in ('hibernate_sequences')";

        List<String> tables = jdbc.queryForList(tablesSQL, String.class);

        Map<String, String> operations = new HashMap();
        operations.put("_AI", "INSERT");
        operations.put("_UI", "UPDATE");

        System.out.println("*** Audit Configurator START ***");

        for (String table : tables) {

            String tabColumnsDefSQL = "select concat(column_name, ' ', column_type) from information_schema.columns\n" +
                                      "where table_name = '#TAB#' order by ordinal_position";
            tabColumnsDefSQL = tabColumnsDefSQL.replace("#TAB#", table);
            List<String> columnsDef = jdbc.queryForList(tabColumnsDefSQL, String.class);
            String auditTableName = table.concat("_audit");

            String tabColumnsSQL = "select column_name from information_schema.columns \n" +
                                   "where table_name = '#TAB#' order by ordinal_position";
            tabColumnsSQL = tabColumnsSQL.replace("#TAB#", table);
            List<String> columnNames = jdbc.queryForList(tabColumnsSQL, String.class);

            int result = jdbc.update("drop table if exists ".concat(auditTableName).concat(";"));
            System.out.println(String.format("drop table %1$s executed with result: %2$d", auditTableName, result));

            StringBuilder asb = new StringBuilder();
            asb.append("create table ").append(auditTableName).append("(");
            for (String column : columnsDef) {
                asb.append(column).append(", ");
            }
            asb.append("createdBy varchar(255),");
            asb.append("createdTs timestamp,");
            asb.append("lastUpdatedBy varchar(255),");
            asb.append("lastUpdatedTs timestamp);");
            result = jdbc.update(asb.toString());
            System.out.println(String.format("create table %1$s executed with result: %2$d", auditTableName, result));
            if (result==0) {
                //for (Map.Entry<String, String> operation : operations.entrySet()) {
                    StringBuilder tsb = new StringBuilder();

                //params.put("triggername", table.concat(operation.getKey()));
                //params.put("operation", operation.getValue());

                    tsb.append("create trigger ").append(table).append("_AI ");
                    tsb.append("after insert on ").append(table).append(" for each row ");
                    tsb.append("begin insert into ").append(auditTableName);

                    StringBuilder csb = new StringBuilder();
                    csb.append("(");
                    StringBuilder vsb = new StringBuilder();
                    vsb.append(" values(");
                    for (String column : columnNames) {
                        csb.append(column).append(", ");
                        vsb.append("new.").append(column).append(", ");
                    }
                    csb.append("createdBy, createdTs)");
                    vsb.append("'System', CURRENT_TIMESTAMP()); end;");

                    tsb.append(csb.toString());
                    tsb.append(vsb.toString());

                    System.out.println(tsb.toString());

                    result = jdbc.update(tsb.toString());
                    System.out.println(String.format("create audit trigger for %1$s executed with result: %2$d", table, result));
                //}
            }
        }
        System.out.println("*** Audit Configurator END ***");
    }
}

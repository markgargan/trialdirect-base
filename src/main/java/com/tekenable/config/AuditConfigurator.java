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

        System.out.println("*** Trigger Creation START ***");

        for (String table : tables) {

            String tabColumnsSQL = "select concat(column_name, ' ', column_type) \n" +
                                   "from information_schema.columns\n" +
                                   "where table_name = 'Answer'\n" +
                                   "order by ordinal_position";

            List<String> columns = jdbc.queryForList(tabColumnsSQL, String.class);
            String auditTableName = table.concat("_audit");

            int result = jdbc.update("drop table if exists ".concat(auditTableName).concat(";"));
            System.out.println(String.format("drop table %1$s executed with result: %2$d", auditTableName, result));

            StringBuilder asb = new StringBuilder();
            asb.append("create table ").append(auditTableName).append("(");
            for (String column : columns) {
                asb.append(column).append(", ");
            }
            asb.append("createdBy varchar(255),");
            asb.append("createdTs timestamp,");
            asb.append("lastUpdatedBy varchar(255),");
            asb.append("lastUpdatedTs timestamp);");

            //System.out.println(asb.toString());
            //System.out.println(" ");

            result = jdbc.update(asb.toString());
            System.out.println(String.format("create table %1$s executed with result: %2$d", auditTableName, result));
            /*if (result==0) {
                //for (Map.Entry<String, String> operation : operations.entrySet()) {
                    StringBuilder sb = new StringBuilder();

                //params.put("triggername", table.concat(operation.getKey()));
                //params.put("operation", operation.getValue());

                    sb.append("create trigger ").append(table).append("_AI ");
                    sb.append("after insert on ").append(table).append(" for each row ");
                    sb.append("begin insert into ").append(auditTableName).append("(");

                    String tabColumns = "select column_name from information_schema.columns \n" +
                                        "where table_name = '#TAB#' order by ordinal_position";
                    tabColumns.replace("#TAB#", auditTableName);
                    columns = jdbc.queryForList(tabColumnsSQL, String.class);
                    String values = ") values(";
                    for (int i=0; i<columns.size(); i++) {
                        if (i>0) {
                            sb.append(",");
                            values.concat(",");
                        }
                        sb.append(columns.get(i));
                        values.concat(":new.").concat(columns.get(i));
                    }
                    sb.append(values);
                    sb.append("'System', CURRENT_TIMESTAMP(), null, null); end;");

                    System.out.println(asb.toString());
                    System.out.println(" ");

                    result = jdbc.update(sb.toString());

                //}
            }*/
        }
        System.out.println("*** Trigger Creation END ***");
    }
}

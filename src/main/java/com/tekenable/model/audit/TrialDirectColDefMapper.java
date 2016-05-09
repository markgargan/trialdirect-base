package com.tekenable.model.audit;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TrialDirectColDefMapper implements RowMapper {

    @Override
    public TrialDirectTableDef mapRow(ResultSet resultSet, int i) throws SQLException {
        TrialDirectTableDef def = new TrialDirectTableDef();
        def.setColumnName(resultSet.getString("column_name"));
        def.setColumnDef(resultSet.getString("column_type"));
        return def;
    }
}

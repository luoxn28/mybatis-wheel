package com.intrack.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author intrack
 */
public class IntegerTypeHandler extends BaseTypeHandler<Integer> {

    @Override
    public void setNonNullParameter(PreparedStatement statement, int i, Integer parameter, JdbcType jdbcType)
        throws SQLException {
        statement.setInt(i, parameter);
    }

    @Override
    public Integer getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getInt(columnIndex);
    }

    @Override
    public Integer getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getInt(columnName);
    }

    @Override
    public Integer getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return statement.getInt(columnIndex);
    }

}

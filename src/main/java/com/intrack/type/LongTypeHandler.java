package com.intrack.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author intrack
 */
public class LongTypeHandler extends BaseTypeHandler<Long> {

    @Override
    public void setNonNullParameter(PreparedStatement statement, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        statement.setLong(i, (Long) parameter);
    }

    @Override
    public Long getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getLong(columnIndex);
    }

    @Override
    public Long getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getLong(columnName);
    }

    @Override
    public Long getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return statement.getLong(columnIndex);
    }

}

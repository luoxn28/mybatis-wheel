package com.intrack.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author intrack
 */
public class ShortTypeHandler extends BaseTypeHandler<Short> {

    @Override
    public void setNonNullParameter(PreparedStatement statement, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        statement.setShort(i, (Short) parameter);
    }

    @Override
    public Short getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getShort(columnIndex);
    }

    @Override
    public Short getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getShort(columnName);
    }

    @Override
    public Short getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return statement.getShort(columnIndex);
    }

}

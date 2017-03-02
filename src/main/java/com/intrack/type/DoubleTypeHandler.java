package com.intrack.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author intrack
 */
public class DoubleTypeHandler extends BaseTypeHandler<Double> {

    @Override
    public void setNonNullParameter(PreparedStatement statement, int i, Double parameter, JdbcType jdbcType)
            throws SQLException {
        statement.setDouble(i, parameter);
    }

    @Override
    public Double getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getDouble(columnIndex);
    }

    @Override
    public Double getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getDouble(columnName);
    }

    @Override
    public Double getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return statement.getDouble(columnIndex);
    }

}

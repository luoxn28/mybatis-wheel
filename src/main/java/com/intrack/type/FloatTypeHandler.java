package com.intrack.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author intrack
 */
public class FloatTypeHandler extends BaseTypeHandler<Float> {

    @Override
    public void setNonNullParameter(PreparedStatement statement, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        statement.setDouble(i, (Float) parameter);
    }

    @Override
    public Float getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getFloat(columnIndex);
    }

    @Override
    public Float getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getFloat(columnName);
    }

    @Override
    public Float getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return statement.getFloat(columnIndex);
    }

}

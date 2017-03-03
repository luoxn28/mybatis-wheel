package com.intrack.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author intrack
 */
public class ByteTypeHandler extends BaseTypeHandler<Byte> {

    @Override
    public void setNonNullParameter(PreparedStatement statement, int i, Byte parameter, JdbcType jdbcType)
            throws SQLException {
        statement.setByte(i, parameter);
    }

    @Override
    public Byte getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getByte(columnIndex);
    }

    @Override
    public Byte getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getByte(columnName);
    }

    @Override
    public Byte getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return statement.getByte(columnIndex);
    }

}

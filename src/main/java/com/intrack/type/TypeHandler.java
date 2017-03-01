package com.intrack.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author intrack
 */
public interface TypeHandler<T> {

    void setParameter(PreparedStatement statement, T parameter, JdbcType jdbcType) throws SQLException;

    T getResult(ResultSet resultSet, String columnName) throws SQLException;

    T getResult(ResultSet resultSet, int columnIndex) throws SQLException;

    T getResult(CallableStatement statement, int columnIndex) throws SQLException;

}

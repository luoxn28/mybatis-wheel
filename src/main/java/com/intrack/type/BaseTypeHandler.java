package com.intrack.type;

import com.intrack.exceptions.ResultMapException;
import com.intrack.exceptions.TypeException;
import com.intrack.session.Configuration;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author intrack
 */
public abstract class BaseTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {

    protected Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void setParameter(PreparedStatement statement, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }

            try {
                statement.setNull(i, jdbcType.TYPE_CODE);
            } catch (SQLException e) {
                throw new TypeException("Error setting parameter on setNull.");
            }
        } else {
            try {
                setNonNullParameter(statement, i, parameter, jdbcType);
            } catch (Exception e) {
                throw new TypeException("Error setting parameter on setNonNullParameter.");
            }
        }
    }

    @Override
    public T getResult(ResultSet resultSet, String columnName) throws SQLException {
        T result;

        try {
            result = getNullableResult(resultSet, columnName);
        } catch (Exception e) {
            throw new ResultMapException("Error attempting to get column in getResult.");
        }

        if (resultSet.wasNull()) {
            return null;
        } else {
            return result;
        }
    }

    @Override
    public T getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        T result;

        try {
            result = getNullableResult(resultSet, columnIndex);
        } catch (Exception e) {
            throw new ResultMapException("Error attempting to get column in getResult.");
        }

        if (resultSet.wasNull()) {
            return null;
        } else {
            return result;
        }
    }

    @Override
    public T getResult(CallableStatement statement, int columnIndex) throws SQLException {
        T result;

        try {
            result = getNullableResult(statement, columnIndex);
        } catch (Exception e) {
            throw new ResultMapException("Error attempting to get column in getResult.");
        }

        if (statement.wasNull()) {
            return null;
        } else {
            return result;
        }
    }

    public abstract void setNonNullParameter(PreparedStatement statement, int i, T parameter, JdbcType jdbcType) throws SQLException;

    public abstract T getNullableResult(ResultSet resultSet, String columnIndex) throws SQLException;

    public abstract T getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException;

    public abstract T getNullableResult(CallableStatement statement, int columnIndex) throws SQLException;

}

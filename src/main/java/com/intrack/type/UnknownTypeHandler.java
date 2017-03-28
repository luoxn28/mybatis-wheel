package com.intrack.type;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class UnknownTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement statement, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        return;
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return null;
    }

    public PreparedStatement setNonNullString(String statement, Object parameter, Connection connection)
            throws SQLException {
        List<String> names = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        PreparedStatement preparedStatement = null;

        if (!convertStatement(statement, stringBuffer, names)) {
            preparedStatement = connection.prepareStatement(statement);

        } else {
            preparedStatement = getPreparedStatementByNonNullString(stringBuffer, names, parameter, connection);
        }

        return preparedStatement;
    }

    // ----------------------------------------------------- private scope

    /**
     * Convert statement sql. #{xxx} -> ? & xxx
     */
    private boolean convertStatement(String statement, StringBuffer stringBuffer, List<String> names) {
        boolean start = false;
        int left = 0;
        int length = statement.length();
        for (int i = 0; i < length; i++) {

            if (statement.charAt(i) == '#') {
                start = true;
                left = i + 1;
            } else if (statement.charAt(i) == ' ') {
                if (start == true) {
                    start = false;
                    names.add(statement.substring(left, i));
                    stringBuffer.append('?');
                }

                if (start != true) {
                    stringBuffer.append(statement.charAt(i));
                }
            } else if (i == length - 1){
                if (start == true) {
                    start = false;
                    names.add(statement.substring(left, length));
                    stringBuffer.append('?');
                }
            } else {

                if (start != true) {
                    stringBuffer.append(statement.charAt(i));
                }
            }
        }

        return true;
    }

    /**
     * Get PreparedStatement by statement sql and names list.
     */
    private PreparedStatement getPreparedStatementByNonNullString(StringBuffer stringBuffer, List<String> names,
                                                                  Object parameter, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuffer.toString());

        int index = 1;
        while (!names.isEmpty()) {
            String filedName = names.remove(0);
            PropertyDescriptor descriptor = null;

            try {
                descriptor = new PropertyDescriptor(filedName, parameter.getClass());
                Method method = descriptor.getReadMethod();

                if (method == null) {
                    throw new TypeException("UnknownTypeHandler method is null");
                }

                Object value = method.invoke(parameter);

                TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(value.getClass());

                typeHandler.setParameter(preparedStatement, index++, value, null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return preparedStatement;
    }

}

package com.intrack.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public String setNonNullString(String statement, Object parameter)
            throws SQLException {
        if (!statement.contains("#")) {
            return statement;
        }

        System.out.println(statement);
        System.out.println(parameter);

        List<String> names = new ArrayList<>();

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
                }
            } else if (i == length - 1){
                if (start == true) {
                    start = false;
                    names.add(statement.substring(left, length));
                }
            }
        }

        System.out.println(names);

        return "select * from users where id = 3";
    }

}

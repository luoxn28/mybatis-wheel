package com.intrack.executor.resultset;

import com.intrack.cursor.Cursor;
import com.intrack.executor.ExecutorException;
import com.intrack.type.TypeHandler;
import javafx.beans.binding.ObjectExpression;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author intrack
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private ResultSetWrapper resultSetWrapper;

    public DefaultResultSetHandler(ResultSetWrapper resultSetWrapper) {
        this.resultSetWrapper = resultSetWrapper;
    }

    @Override
    public <E> List<E> handlerResultSets(Class<?> clazz) throws SQLException {
        List<E> resultList = new ArrayList<>();
        List<TypeHandler<?>> typeHandlers = resultSetWrapper.getTypeHandlers();
        List<String> columnNames = resultSetWrapper.getColumnNames();

        while (resultSetWrapper.next()) {
            E result = crateResultObject(clazz);

            for (int i = 0; i < typeHandlers.size(); i++) {
                TypeHandler typeHandler = typeHandlers.get(i);
                handleResultSetValue(result, columnNames.get(i),
                        typeHandler.getResult(resultSetWrapper.getResultSet(), i + 1));
            }
            resultList.add(result);
        }

        return resultList;
    }

    @Override
    public <E> Cursor<E> handleCursorResultSets(Statement statement) throws SQLException {
        return null;
    }

    @Override
    public void handleOutputParameters(CallableStatement callableStatement) throws SQLException {

    }

    private <E> E crateResultObject(Class<?> clazz) {
        E object = null;

        try {
            object = (E)clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return object;
    }

    private <E> void handleResultSetValue(E result, String name, Object value) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(name, result.getClass());
            Method method = descriptor.getWriteMethod();

            method.invoke(result, value);
        } catch (Exception e) {
            throw new ExecutorException("handle result set value error");
        }

    }

}

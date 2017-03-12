package com.intrack.executor.resultset;

import com.intrack.cursor.Cursor;
import com.intrack.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
    public <E> List<E> handlerResultSets(Statement statement) throws SQLException {
        List<TypeHandler<?>> typeHandlers = resultSetWrapper.getTypeHandlers();
        List<String> columnNames = resultSetWrapper.getColumnNames();

        while (resultSetWrapper.next()) {
            for (int i = 0; i < typeHandlers.size(); i++) {
                TypeHandler typeHandler = typeHandlers.get(i);
                System.out.print(columnNames.get(i) + ":"
                        + typeHandler.getResult(resultSetWrapper.getResultSet(), i + 1) + " ");
            }
            System.out.println();
        }
        return null;
    }

    @Override
    public <E> Cursor<E> handleCursorResultSets(Statement statement) throws SQLException {
        return null;
    }

    @Override
    public void handleOutputParameters(CallableStatement callableStatement) throws SQLException {

    }

}

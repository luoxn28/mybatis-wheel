package com.intrack.executor.resultset;

import com.intrack.cursor.Cursor;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author intrack
 */
public interface ResultSetHandler {

    <E> List<E> handlerResultSets(Class<?> clazz) throws SQLException;

    <E> Cursor<E> handleCursorResultSets(Statement statement) throws SQLException;

    void handleOutputParameters(CallableStatement callableStatement) throws SQLException;

}

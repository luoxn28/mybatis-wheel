package com.intrack.executor.statement;

import com.intrack.executor.ExecutorException;
import com.intrack.type.TypeException;
import com.intrack.type.TypeHandler;
import com.intrack.type.TypeHandlerRegistry;
import com.intrack.type.UnknownTypeHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author intrack
 */
public class DefaultStatementHandler implements StatementHandler {

    private int startIndex = 1;

    @Override
    public void resetStartIndex() {
        startIndex = 1;
    }

    @Override
    public void prepare(PreparedStatement preparedStatement, Object parameter) throws SQLException {
        if (parameter == null) {
            return;
        }

        TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(parameter.getClass());

        typeHandler.setParameter(preparedStatement, startIndex++, parameter, null);
    }

    @Override
    public PreparedStatement prepare(String statement, Object parameter, Connection connection) throws SQLException {
        if (!statement.contains("#")) {
            throw new ExecutorException("statement has not '#'");
        }

        TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(parameter.getClass());

        return ((UnknownTypeHandler)typeHandler).setNonNullString(statement, parameter, connection);
    }

}

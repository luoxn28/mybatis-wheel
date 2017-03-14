package com.intrack.executor.statement;

import com.intrack.mapping.MappedStatement;
import com.intrack.type.TypeHandler;
import com.intrack.type.TypeHandlerRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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

}

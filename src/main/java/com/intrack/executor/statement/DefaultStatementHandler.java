package com.intrack.executor.statement;

import com.intrack.mapping.MappedStatement;

import java.sql.Statement;

/**
 * @author intrack
 */
public class DefaultStatementHandler implements StatementHandler {

    private MappedStatement mappedStatement = MappedStatement.instance();

    @Override
    public String prepare(String statementPath, Object parameter) {
        String statement = mappedStatement.getStatement(statementPath);

        if (parameter == null) {
            return statement;
        }

        return statement;
    }

}

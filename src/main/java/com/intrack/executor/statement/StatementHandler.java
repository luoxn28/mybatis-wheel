package com.intrack.executor.statement;

/**
 * statement handler
 * 解析statement，组合成完整的statement
 *
 * @author intrack
 */
public interface StatementHandler {

    String prepare(String statementPath, Object parameter);

}

package com.intrack.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * statement handler
 * 解析statement，组合成完整的statement
 *
 * @author intrack
 */
public interface StatementHandler {

    void resetStartIndex();

    void prepare(PreparedStatement preparedStatement, Object parameter) throws SQLException;

    PreparedStatement prepare(String statement, Object parameter, Connection connection) throws SQLException;

}

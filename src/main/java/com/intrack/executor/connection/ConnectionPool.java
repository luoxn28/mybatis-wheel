package com.intrack.executor.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author intrack
 */
public interface ConnectionPool {

    Connection getConnection() throws SQLException;

    void release(Connection connection);

}

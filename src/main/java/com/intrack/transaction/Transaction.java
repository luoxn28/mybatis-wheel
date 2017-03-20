package com.intrack.transaction;

import com.intrack.executor.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author intrack
 */
public interface Transaction {

    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

    void setConnectionPool(ConnectionPool connectionPool);

    /**
     * 获取事务超时时间，如果设置的话
     */
    Integer getTimeout() throws SQLException;

    boolean isAutoCommit();

}

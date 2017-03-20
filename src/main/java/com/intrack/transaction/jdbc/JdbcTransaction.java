package com.intrack.transaction.jdbc;

import com.intrack.executor.ExecutorException;
import com.intrack.executor.connection.ConnectionPool;
import com.intrack.executor.connection.DefaultConnectionPoll;
import com.intrack.logging.Log;
import com.intrack.logging.LogFactory;
import com.intrack.transaction.Transaction;
import com.intrack.transaction.TransactionException;
import com.intrack.transaction.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author intrack
 */
public class JdbcTransaction implements Transaction {

    private static final Log log = LogFactory.getLog(JdbcTransaction.class);

    protected Connection connection;
    protected DataSource dataSource;
    protected TransactionIsolationLevel level;
    protected boolean autoCommit;

    private ConnectionPool connectionPool;

    public JdbcTransaction(Connection conn) {
        this.connection = conn;
    }

    public JdbcTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        if (dataSource == null) {
            throw new ExecutorException("dataSource is null");
        }

        this.dataSource = dataSource;
        this.level = level;
        this.autoCommit = autoCommit;
        this.connectionPool = null;
    }

    @Override
    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            openConnection();
        }

        return connection;
    }

    @Override
    public void commit() throws SQLException {
        if ((connection != null) && !connection.getAutoCommit()) {
            if (log.isDebugEnabled()) {
                log.debug("Committing JDBC Connection {" + connection + "]");
            }

            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            if (log.isDebugEnabled()) {
                log.debug("Rolling back JDBC Connection [" + connection + "]");
            }
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            if (log.isDebugEnabled()) {
                log.debug("Closing JDBC Connection [" + connection + "]");
            }

            if (connectionPool != null) {
                /* For use connection next time */
                connectionPool.release(connection);
            } else {
                connection.close();
            }

            connection = null;
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return null;
    }

    @Override
    public boolean isAutoCommit() {
        return autoCommit;
    }

    protected void openConnection() throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("Opening JDBC Connection.");
        }

        if (connectionPool != null) {
            connection = connectionPool.getConnection();
        } else {
            connection = dataSource.getConnection();
        }

        if (level != null) {
            connection.setTransactionIsolation(level.getLevel());
        }
        setAutoCommit(autoCommit);
    }

    protected void setAutoCommit(boolean autoCommit) {
        try {
            if (connection.getAutoCommit() != autoCommit) {
                if (log.isDebugEnabled()) {
                    log.debug("Setting autoCommit to Connection.");
                }

                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            throw new TransactionException("Error configuration AutoCommit.");
        }
    }

}

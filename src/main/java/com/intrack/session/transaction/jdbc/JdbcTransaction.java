package com.intrack.session.transaction.jdbc;

import com.intrack.logging.Log;
import com.intrack.logging.LogFactory;
import com.intrack.session.transaction.Transaction;
import com.intrack.session.transaction.TransactionException;
import com.intrack.session.transaction.TransactionIsolationLevel;

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

    public JdbcTransaction(Connection conn) {
        this.connection = conn;
    }

    public JdbcTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        this.dataSource = dataSource;
        this.level = level;
        this.autoCommit = autoCommit;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection != null) {
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

            connection.close();
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return null;
    }

    protected void openConnection() throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("Opening JDBC Connection.");
        }

        connection = dataSource.getConnection();
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

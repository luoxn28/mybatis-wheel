package com.intrack.session.transaction.jdbc;

import com.intrack.session.transaction.Transaction;
import com.intrack.session.transaction.TransactionFactory;
import com.intrack.session.transaction.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author intrack
 */
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public void setProperties(Properties props) {

    }

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }

}

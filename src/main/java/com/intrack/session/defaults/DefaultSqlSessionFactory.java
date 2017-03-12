package com.intrack.session.defaults;

import com.intrack.session.Configuration;
import com.intrack.session.ExecutorType;
import com.intrack.session.SqlSession;
import com.intrack.session.SqlSessionFactory;
import com.intrack.transaction.TransactionIsolationLevel;

import java.sql.Connection;

/**
 * @author intrack
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, DEFAULT_AUTOCOMMIT);
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, autoCommit);
    }

    @Override
    public SqlSession openSession(Connection connection) {
        return openSessionFromConnection(configuration.getDefaultExecutorType(), connection);
    }

    @Override
    public SqlSession openSession(TransactionIsolationLevel level) {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), level, DEFAULT_AUTOCOMMIT);
    }

    @Override
    public SqlSession openSession(ExecutorType execType) {
        return openSessionFromDataSource(execType, null, DEFAULT_AUTOCOMMIT);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
        return openSessionFromDataSource(execType, null, autoCommit);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, Connection connection) {
        return openSessionFromConnection(execType, connection);
    }

    @Override
    public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
        return openSessionFromDataSource(execType, level, DEFAULT_AUTOCOMMIT);
    }

    // ------------------------------------------ Private Methods

    private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level,
                                                 boolean autoCommit) {

        return new DefaultSqlSession(null);
    }

    private SqlSession openSessionFromConnection(ExecutorType execType, Connection connection) {

        return new DefaultSqlSession(null);
    }

}

package com.intrack.session;

import com.intrack.transaction.TransactionIsolationLevel;

import java.sql.Connection;

/**
 * @author intrack
 */
public interface SqlSessionFactory {

    boolean DEFAULT_AUTOCOMMIT = false;

    SqlSession openSession();

    SqlSession openSession(boolean autoCommit);
    SqlSession openSession(Connection connection);
    SqlSession openSession(TransactionIsolationLevel level);

    SqlSession openSession(ExecutorType execType);
    SqlSession openSession(ExecutorType execType, boolean autoCommit);
    SqlSession openSession(ExecutorType execType, Connection connection);
    SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);

}

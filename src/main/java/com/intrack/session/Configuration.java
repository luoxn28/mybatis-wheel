package com.intrack.session;

import com.intrack.executor.connection.ConnectionPool;
import com.intrack.executor.connection.DefaultConnectionPoll;
import com.intrack.executor.datasource.DefaultDataSource;
import com.intrack.mapping.Environment;
import com.intrack.mapping.MappedStatement;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author intrack
 */
public class Configuration {

    protected Environment environment;

    private static BasicDataSource dataSource = new DefaultDataSource();

    private ConnectionPool connectionPool = new DefaultConnectionPoll(dataSource);

    protected Map<String, MappedStatement> mappedStatementMap = new ConcurrentHashMap<>();

    protected ExecutorType executorType = getDefaultExecutorType();

    {
        mappedStatementMap.put("com.intrack.test.UserDao", new MappedStatement());
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ExecutorType getDefaultExecutorType() {
        return ExecutorType.SIMPLE;
    }

    public MappedStatement getMappedStatement(String statement) {
        String namespace = statement.substring(0, statement.lastIndexOf('.'));

        MappedStatement mappedStatement = mappedStatementMap.get(namespace);
        if (mappedStatement == null) {
            throw new SqlSessionException("Configuration getMappedStatement null");
        }

        return mappedStatement;
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(BasicDataSource dataSource) {
        Configuration.dataSource = dataSource;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}

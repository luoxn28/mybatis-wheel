package com.intrack.session;

import com.intrack.executor.connection.ConnectionPool;
import com.intrack.executor.connection.DefaultConnectionPoll;
import com.intrack.executor.datasource.DefaultDataSource;
import com.intrack.io.parser.MapperNode;
import com.intrack.logging.Log;
import com.intrack.logging.log4j.Log4jImpl;
import com.intrack.mapping.Environment;
import com.intrack.mapping.MappedStatement;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author intrack
 */
public class Configuration {

    public static Log log = new Log4jImpl("mybatis-wheel");

    protected Environment environment;

    private static BasicDataSource dataSource = new DefaultDataSource();

    private ConnectionPool connectionPool = new DefaultConnectionPoll(dataSource);

    protected MappedStatement mappedStatement = new MappedStatement();

    /**
     * key: sql
     * value: MapperNode
     */
    private Map<String, MapperNode> mappedMapperNode = new ConcurrentHashMap<>();

    protected ExecutorType executorType = getDefaultExecutorType();

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ExecutorType getDefaultExecutorType() {
        return ExecutorType.SIMPLE;
    }

    public void addMapperNode(MapperNode mapperNode) {
        mappedMapperNode.put(mapperNode.getId(), mapperNode);
        mappedStatement.addStatement(mapperNode.getId(), mapperNode.getSql());
    }

    public MappedStatement getMappedStatement() {
        return this.mappedStatement;
    }

    public MapperNode getMapperNode(String id) {
        MapperNode mapperNode = mappedMapperNode.get(id);

        if (mapperNode != null) {
            return mapperNode;
        } else {
            throw new SqlSessionException("Not find mapperNode.");
        }
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

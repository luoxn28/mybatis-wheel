package com.intrack.executor.connection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author intrack
 */
public class DefaultConnectionPoll implements ConnectionPool {

    private DataSource dataSource;
    private List<Connection> connectionPools = new LinkedList<>();

    public DefaultConnectionPoll(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (!connectionPools.isEmpty()) {
            return connectionPools.remove(0);
        }

        return dataSource.getConnection();
    }

    @Override
    public void release(Connection connection) {
        if (connection == null) {
            return;
        }

        connectionPools.add(connection);
    }

}

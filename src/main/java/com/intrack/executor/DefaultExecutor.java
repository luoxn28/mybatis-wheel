package com.intrack.executor;

import com.intrack.executor.resultset.DefaultResultSetHandler;
import com.intrack.executor.resultset.ResultSetHandler;
import com.intrack.executor.resultset.ResultSetWrapper;
import com.intrack.session.Configuration;
import com.intrack.test.User;
import com.intrack.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author intrack
 */
public class DefaultExecutor implements Executor {

    private Configuration  configuration;

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(String statementSql) {
        /* 加载sql驱动 */
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://192.168.1.150/ssh_study";
        String username = "luoxn28";
        String password = "123456";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(statementSql);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
        ResultSetWrapper resultSetWrapper = new ResultSetWrapper(preparedStatement);
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(resultSetWrapper);

        List<User> userList = null;
        try {
            userList = resultSetHandler.handlerResultSets(preparedStatement, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (List<E>) userList;
    }

}
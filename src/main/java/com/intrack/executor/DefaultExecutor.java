package com.intrack.executor;

import com.intrack.executor.cache.Cache;
import com.intrack.executor.resultset.DefaultResultSetHandler;
import com.intrack.executor.resultset.ResultSetHandler;
import com.intrack.executor.resultset.ResultSetWrapper;
import com.intrack.executor.statement.DefaultStatementHandler;
import com.intrack.executor.statement.StatementHandler;
import com.intrack.mapping.Environment;
import com.intrack.mapping.MappedStatement;
import com.intrack.session.Configuration;
import com.intrack.test.User;
import com.intrack.transaction.Transaction;
import com.intrack.transaction.jdbc.JdbcTransactionFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author intrack
 */
public class DefaultExecutor implements Executor {

    private static BasicDataSource dataSource = new BasicDataSource();
    static {
        /* 设置DataSource*/
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.1.150/ssh_study");
        dataSource.setUsername("luoxn28");
        dataSource.setPassword("123456");
    }

    private Configuration configuration;
    private StatementHandler statementHandler = new DefaultStatementHandler();



    private Transaction transaction = new JdbcTransactionFactory().newTransaction(dataSource, null, true);

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(String statementSql, Object parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        MappedStatement mappedStatement = configuration.getMappedStatement(statementSql);
        Cache cache = mappedStatement.getCache();
        if (cache != null) {
            Integer hashCode = createHashcode(statementSql, parameter);
            List<E> cacheResult = (List<E>) cache.getCacheObject(hashCode);
            if (cacheResult != null) {
                return cacheResult;
            }
        }

        try {
            connection = transaction.getConnection();
            preparedStatement = connection.prepareStatement(mappedStatement.getStatement(statementSql));

            statementHandler.resetStartIndex();
            statementHandler.prepare(preparedStatement, parameter);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSetWrapper resultSetWrapper = new ResultSetWrapper(preparedStatement);
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(resultSetWrapper);

        List<E> userList = null;
        try {
            userList = resultSetHandler.handlerResultSets(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSetWrapper.close();
                preparedStatement.close();
                transaction.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        cache.putObject(createHashcode(statementSql, parameter), userList);
        return (List<E>) userList;
    }

    @Override
    public int insert(String statement, Object parameter) {
        return updateInternal(statement, parameter);
    }

    @Override
    public int update(String statement, Object parameter) {
        return updateInternal(statement, parameter);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return updateInternal(statement, parameter);
    }

    private int updateInternal(String statement, Object parameter) {
        int updateResult = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);

            connection = transaction.getConnection();
            preparedStatement = connection.prepareStatement(mappedStatement.getStatement(statement));

            statementHandler.resetStartIndex();
            statementHandler.prepare(preparedStatement, parameter);
            updateResult = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                transaction.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return updateResult;
    }

    private int createHashcode(String statement, Object parameter) {
        if (parameter == null) {
            return statement.hashCode();
        }

        return statement.hashCode() * parameter.hashCode() + statement.hashCode() % 16;
    }

}

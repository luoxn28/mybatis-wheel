package com.intrack.executor;

import com.intrack.executor.cache.Cache;
import com.intrack.executor.cache.DefaultCache;
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

    /* 一级缓存 */
    private Cache localCache = new DefaultCache();

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(String statementSql, Object parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        MappedStatement mappedStatement = configuration.getMappedStatement(statementSql);
        /* 查询二级缓存 */
        Cache cache = mappedStatement.getCache();
        Integer hashCode = createHashcode(statementSql, parameter);
        if (cache != null) {
            List<E> cacheResult = (List<E>) cache.getCacheObject(hashCode);
            if (cacheResult != null) {
                return cacheResult;
            }
        }

        /* 查询一级缓存 */
        List<E> localCacheResult = (List<E>) localCache.getCacheObject(hashCode);
        if (localCacheResult != null) {
            return localCacheResult;
        }

        try {
            connection = transaction.getConnection();
            preparedStatement = connection.prepareStatement(mappedStatement.getStatement(statementSql));

            statementHandler.resetStartIndex();
            statementHandler.prepare(preparedStatement, parameter);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            try {
                /* Transaction rollback */
                if (!transaction.isAutoCommit()) {
                    transaction.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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

        /**
         * localCache: 一级缓存写入数据
         * cache: 二级缓存写入数据
         * 一级二级缓存资料参考: http://www.360doc.com/content/15/1205/07/29475794_518018352.shtml
         */
        localCache.putObject(hashCode, userList);
        cache.putObject(hashCode, userList);
        return userList;
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

    @Override
    public void clearCache() {
        localCache.clear();
    }

    // ----------------------------------------------------- private scope

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
            try {
                /* Transaction rollback */
                if (!transaction.isAutoCommit()) {
                    transaction.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                transaction.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /* 清空一级缓存 */
        localCache.clear();

        return updateResult;
    }

    private int createHashcode(String statement, Object parameter) {
        if (parameter == null) {
            return statement.hashCode();
        }

        return statement.hashCode() * parameter.hashCode() + statement.hashCode() % 16;
    }

}

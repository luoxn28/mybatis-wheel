package com.intrack.executor;

import com.intrack.executor.cache.Cache;
import com.intrack.executor.cache.DefaultCache;
import com.intrack.executor.resultset.DefaultResultSetHandler;
import com.intrack.executor.resultset.ResultSetHandler;
import com.intrack.executor.resultset.ResultSetWrapper;
import com.intrack.executor.statement.DefaultStatementHandler;
import com.intrack.executor.statement.StatementHandler;
import com.intrack.mapping.MappedStatement;
import com.intrack.session.Configuration;
import com.intrack.session.SqlSessionException;
import com.intrack.test.User;
import com.intrack.transaction.Transaction;
import com.intrack.transaction.jdbc.JdbcTransactionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author intrack
 */
public class DefaultExecutor implements Executor {

    private Configuration configuration;
    private StatementHandler statementHandler;
    private Transaction transaction;

    /* 一级缓存 */
    private Cache localCache;

    public DefaultExecutor(Configuration configuration) {
        if (configuration == null) {
            throw new SqlSessionException("configuration is null");
        }

        this.configuration = configuration;
        this.statementHandler = new DefaultStatementHandler();
        this.transaction = new JdbcTransactionFactory().newTransaction(configuration.getDataSource(), null, true);

        /* 设置connection连接池 */
        this.transaction.setConnectionPool(configuration.getConnectionPool());

        /* 设置一级缓存 */
        localCache = new DefaultCache();
    }

    @Override
    public <E> List<E> query(String statementSql, Object parameter) {
        return queryInternal(statementSql, parameter);
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

    private <E> List<E> queryInternal(String statementSql, Object parameter) {
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
            statementHandler.resetStartIndex();

            connection = transaction.getConnection();
            String resultStatementSql = mappedStatement.getStatement(statementSql);
            if (!isUnknownTypeStatement(resultStatementSql)) {
                preparedStatement = connection.prepareStatement(resultStatementSql);

                statementHandler.prepare(preparedStatement, parameter);
                preparedStatement.executeQuery();
            } else {
                preparedStatement = statementHandler.prepare(resultStatementSql, parameter, connection);
                preparedStatement.executeQuery();
            }
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

    /**
     * 如果statement声明职工包含字符'#'，则表示该statement的parameter为对象中的某个属性值
     */
    private boolean isUnknownTypeStatement(String statement) {
        return (statement.indexOf('#') != -1);
    }

}

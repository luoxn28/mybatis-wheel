package com.intrack.session.defaults;

import com.intrack.executor.DefaultExecutor;
import com.intrack.executor.Executor;
import com.intrack.session.Configuration;
import com.intrack.session.RowBounds;
import com.intrack.session.SqlSession;
import com.intrack.session.SqlSessionException;

import java.util.List;

/**
 * @author intrack
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = new DefaultExecutor(configuration);
    }

    @Override
    public <T> T selectOne(String statement) {
        return selectOne(statement, null);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> resultList = executor.query(statement, parameter);
        if (resultList.size() > 1) {
            throw new SqlSessionException("selectOne resultList size > 1");
        }

        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }


    @Override
    public <E> List<E> selectList(String statement) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return null;
    }

    @Override
    public void close() {

    }

}

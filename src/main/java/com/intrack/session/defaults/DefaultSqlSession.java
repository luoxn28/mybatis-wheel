package com.intrack.session.defaults;

import com.intrack.executor.DefaultExecutor;
import com.intrack.executor.Executor;
import com.intrack.session.Configuration;
import com.intrack.session.RowBounds;
import com.intrack.session.SqlSession;

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
        List<T> resultList = executor.query(statement);
        return resultList.get(0);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return null;
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

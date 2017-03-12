package com.intrack.session.defaults;

import com.intrack.session.RowBounds;
import com.intrack.session.SqlSession;

import java.util.List;

/**
 * @author intrack
 */
public class DefaultSqlSession implements SqlSession {

    @Override
    public <T> T selectOne(String statement) {
        return null;
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

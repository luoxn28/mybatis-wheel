package com.intrack.session;

import java.io.Closeable;
import java.util.List;

/**
 * The primary Java interface for working with mybatis-wheel.
 * Through this interface you can execute commands, get mappers nad manage transactions.
 *
 * @author intrack
 */
public interface SqlSession extends Closeable {

    /**
     * Retrieve a simple row mapped from the statement key.
     * <T> The return object type.
     */
    <T> T selectOne(String statement);

    /**
     * Retrieve a simple row mapped from the statement key and parameter.
     * <T> The return object type.
     */
    <T> T selectOne(String statement, Object parameter);


    /**
     * Retrieve a list of mapped objects from the statement key.
     * <T> The return object type.
     */
    <T> List<T> selectList(String statement);

    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     * <T> The return object type.
     */
    <T> List<T> selectList(String statement, Object parameter);

    @Override
    void close();

}

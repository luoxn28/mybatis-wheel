package com.intrack.session;

import com.intrack.cursor.Cursor;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

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
     * <E> The return list element type.
     */
    <E> List<E> selectList(String statement);

    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     * <E> The return list element type.
     */
    <E> List<E> selectList(String statement, Object parameter);

    /**
     * Retrieve a list of mapped objects from the statement key and parameter,
     * within the specified row bounds.
     * <E> The return list element type.
     */
    <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);

    /**
     * THe selectMap is a special case in that ti is designed to convert a list of results
     * into a Map based on one of the properties in the resulting objects.
     * Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")
     * <K> the returned Map keys type
     * <V> the returned Map values tpe
     */
    <K, V> Map<K, V> selectMap(String statement, String mapKey);

    /**
     * THe selectMap is a special case in that ti is designed to convert a list of results
     * into a Map based on one of the properties in the resulting objects with parameter.
     * Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")
     * <K> the returned Map keys type
     * <V> the returned Map values tpe
     */
    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);

    /**
     * THe selectMap is a special case in that ti is designed to convert a list of results
     * into a Map based on one of the properties in the resulting objects with parameter.
     * Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")
     * <K> the returned Map keys type
     * <V> the returned Map values tpe
     */
    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);

    /**
     * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
     */
    <T> Cursor<T> selectCursor(String statement);

    /**
     * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
     */
    <T> Cursor<T> selectCursor(String statement, Object parameter);

    /**
     * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
     */
    <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds);

    /**
     * Retrieve a single row mapped from the statement key
     * using a ResultHandler.
     */
    void select(String statement, ResultHandler resultHandler);

    /**
     * Retrieve a single row mapped from the statement key and parameter
     * using a ResultHandler.
     */
    void select(String statement, Object parameter, ResultHandler resultHandler);

    @Override
    void close();

}

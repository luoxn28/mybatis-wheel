package com.intrack.mapping;

import com.intrack.executor.cache.Cache;
import com.intrack.executor.cache.DefaultCache;
import com.intrack.type.MappedTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * store all statement
 * singleton
 *
 * @author intrack
 */
public class MappedStatement {

    private final HashMap<String, String> statements = new HashMap<>();
    private Cache cache = null;

    {
        statements.put("com.intrack.test.UserDao.getUser", "select * from users where id = 1");
        //statements.put("com.intrack.test.UserDao.getUserById", "select * from users where id = #id and name = #name");
        statements.put("com.intrack.test.UserDao.getUsersById", "select * from users where id > ?");

        statements.put("com.intrack.test.UserDao.insertOne", "insert users (id, name) value(5, 'kai')");
        statements.put("com.intrack.test.UserDao.insertOneById", "insert users (id, name) value(?, 'kai')");

        statements.put("com.intrack.test.UserDao.updateOne", "update users set name = ? where id = 2");

        statements.put("com.intrack.test.UserDao.deleteOne", "delete from users where id = 5");
        statements.put("com.intrack.test.UserDao.deleteOneById", "delete from users where id = ?");
    }

    public MappedStatement() {
        cache = new DefaultCache();
    }

    public void addStatement(String key, String value) {
        statements.put(key, value);
    }

    public String getStatement(String key) {
        System.out.println(statements.size());
        String value = statements.get(key);
        if (value == null) {
            throw new MappingException("MappedStatement get statement null");
        }

        System.out.println("------ " + key + ": " + value);
        return value;
    }

    public Cache getCache() {
        return cache;
    }

}

package com.intrack.mapping;

import com.intrack.type.MappedTypes;

import java.util.HashMap;

/**
 * store all statement
 * singleton
 *
 * @author intrack
 */
public class MappedStatement {

    private static MappedStatement mappedStatement = new MappedStatement();

    private static final HashMap<String, String> statements = new HashMap<>();

    static {
        statements.put("com.intrack.test.User.getUser", "select * from users where id = 1");
        statements.put("com.intrack.test.User.getUserById", "select * from users where id = ?");
        statements.put("com.intrack.test.User.getUsersById", "select * from users where id > ?");

        statements.put("com.intrack.test.User.insertOne", "insert users (id, name) value(5, 'kai')");
        statements.put("com.intrack.test.User.insertOneById", "insert users (id, name) value(?, 'kai')");

        statements.put("com.intrack.test.User.updateOne", "update users set name = ? where id = 2");

        statements.put("com.intrack.test.User.deleteOne", "delete from users where id = 5");
        statements.put("com.intrack.test.User.deleteOneById", "delete from users where id = ?");
    }

    private MappedStatement() { }

    public static MappedStatement instance() {
        return mappedStatement;
    }

    public String getStatement(String key) {
        String value = statements.get(key);
        if (value == null) {
            throw new MappingException("MappedStatement get statement null");
        }

        return value;
    }

}

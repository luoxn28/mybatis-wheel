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
        statements.put("com.intrack.test.User.getUser", "select * from users where id > 1");
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

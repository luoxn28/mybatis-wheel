package com.intrack.type;

import com.sun.deploy.security.ValidationState;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TypeHandler registry, save all type handler
 *
 * @author intrack
 */
public class TypeHandlerRegistry {

    /**
     *      type + type handler
     * jdbc type + type handler
     */
    private static final Map<Type, TypeHandler<?>> TYPE_HANDLER_MAP = new ConcurrentHashMap<>();
    private static final Map<JdbcType, TypeHandler<?>> JDBCTYPE_HANDLER_MAP = new ConcurrentHashMap<>();
    //private final Map<Type, Map<JdbcType, TypeHandler<?>>> TYPE_HANDLER_MAP = new ConcurrentHashMap<>();

    /* add all type handler */
    static {
        register(Byte.class, JdbcType.ARRAY, new ByteTypeHandler());
        register(Character.class, JdbcType.CHAR, new CharacterTypeHandler());
        register(Double.class, JdbcType.DOUBLE, new DoubleTypeHandler());
        register(Float.class, JdbcType.FLOAT, new FloatTypeHandler());
        register(Integer.class, JdbcType.INTEGER, new IntegerTypeHandler());
        register(Long.class, JdbcType.BIGINT, new LongTypeHandler());
        register(Object.class, JdbcType.JAVA_OBJECT, new ObjectTypeHandler());
        register(Short.class, JdbcType.SMALLINT, new ShortTypeHandler());
        register(String.class, JdbcType.VARCHAR, new StringTypeHandler());
        register(Object.class, JdbcType.VARCHAR, new ObjectTypeHandler());
    }

    public static TypeHandler<?> getTypeHandler(Type type) {
        TypeHandler<?> typeHandler = null;

        typeHandler = TYPE_HANDLER_MAP.get(type);
        if (typeHandler != null) {
            return typeHandler;
        } else {
            /**
             * 如果typeHandler为null，则表示为type为用户自定义的类
             */
            return new UnknownTypeHandler();
        }
    }

    public static TypeHandler<?> getTypeHandler(JdbcType jdbcType) {
        TypeHandler<?> typeHandler = null;

        typeHandler = JDBCTYPE_HANDLER_MAP.get(jdbcType);
        if (typeHandler == null) {
            throw new TypeException("type handler is null.");
        }

        return typeHandler;
    }

    // ----------------------------------------------------- private scope

    private static <T> void register(Class clazz, JdbcType jdbcType, TypeHandler<T> typeHandler) {
        register((Type) clazz, jdbcType, typeHandler);
    }

    private static <T> void register(Type type, JdbcType jdbcType, TypeHandler<T> typeHandler) {
        TYPE_HANDLER_MAP.put(type, typeHandler);
        JDBCTYPE_HANDLER_MAP.put(jdbcType, typeHandler);
    }
}

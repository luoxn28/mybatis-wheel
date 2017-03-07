package com.intrack.type;

import javax.swing.*;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author intrack
 */
public class TypeHandlerRegistry {

    private final Map<JdbcType, TypeHandler<?>> JDBC_TYPE_HANDLER_MAP = new EnumMap<>(JdbcType.class);
    private final Map<Type, Map<JdbcType, TypeHandler<?>>> TYPE_HANDLER_MAP = new ConcurrentHashMap<>();
    private final Map<Class<?>, TypeHandler<?>> ALL_TYPE_HANDLERS_MAP = new HashMap<>();

    private static final Map<JdbcType, TypeHandler<?>> NULL_TYPE_HANDLER_MAP = new HashMap<>();

    public void register(JdbcType jdbcType, TypeHandler<?> handler) {
        JDBC_TYPE_HANDLER_MAP.put(jdbcType, handler);
    }

    // only handler

    public <T> void register(TypeHandler<T> typeHandler) {
        boolean mappedTypeFound = false;

        MappedTypes mappedTypes = typeHandler.getClass().getAnnotation(MappedTypes.class);
        if (mappedTypes != null) {
            for (Class<?> handlerType : mappedTypes.value()) {
                register(handlerType, typeHandler);
                mappedTypeFound = true;
            }
        }

        if (!mappedTypeFound && (typeHandler instanceof TypeReference)) {
            try {
                TypeReference<T> typeReference = (TypeReference<T>) typeHandler;
                register(typeReference.getRawTpe(), typeHandler);
                mappedTypeFound = true;
            } catch (Throwable t) {

            }
        }

        if (!mappedTypeFound) {
            register((Class<T>) null, typeHandler);
        }
    }

    // java type + handler

    private <T> void register(Type javaType, TypeHandler<? extends T> typeHandler) {
        MappedJdbcTypes mappedJdbcTypes = typeHandler.getClass().getAnnotation(MappedJdbcTypes.class);
        if (mappedJdbcTypes != null) {
            for (JdbcType jdbcType : mappedJdbcTypes.value()) {
                register(javaType, jdbcType, typeHandler);
            }

            if (mappedJdbcTypes.includeNullJdbcType()) {
                register(javaType, null, typeHandler);
            }
        } else {
            register(javaType, null, typeHandler);
        }
    }

    // java type + jdbc type + handler

    public <T> void register(Class<T> type, JdbcType jdbcType, TypeHandler<? extends T> handler) {
        register((Type) type, jdbcType, handler);
    }

    /**
     * 将typeHandler注册到map中
     */
    private void register(Type javaType, JdbcType jdbcType, TypeHandler<?> handler) {
        if (javaType != null) {
            Map<JdbcType, TypeHandler<?>> map = TYPE_HANDLER_MAP.get(javaType);
            if (map != null) {
                map = new HashMap<>();
                TYPE_HANDLER_MAP.put(javaType, map);
            }

            map.put(jdbcType, handler);
        }
        ALL_TYPE_HANDLERS_MAP.put(handler.getClass(), handler);
    }

}

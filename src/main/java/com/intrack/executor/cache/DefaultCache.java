package com.intrack.executor.cache;

import com.intrack.executor.connection.DefaultConnectionPoll;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author intrack
 */
public class DefaultCache implements Cache {

    private Map<Integer, List<?>> cache = null;

    public DefaultCache() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void putObject(Integer hashcode, List<?> results) {
        cache.put(hashcode, results);
    }

    @Override
    public List<?> getCacheObject(Integer hashcode) {
        return cache.get(hashcode);
    }

    @Override
    public void clear() {
        cache.clear();
    }

}

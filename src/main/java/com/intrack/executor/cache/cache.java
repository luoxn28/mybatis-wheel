package com.intrack.executor.cache;

import java.util.List;

/**
 * @author intrack
 */
public interface Cache {

    void putObject(Integer hashcode, List<?> results);

    List<?> getCacheObject(Integer hashcode);

    void clear();

}

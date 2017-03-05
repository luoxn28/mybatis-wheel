package com.intrack.session;

/**
 * @author intrack
 */
public interface ResultContext<T> {

    T getResultObject();

    int getResultCount();

    boolean isStopped();

    void stop();

}

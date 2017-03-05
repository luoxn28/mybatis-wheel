package com.intrack.session;

/**
 * @author intrack
 */
public interface ResultHandler<T> {

    void handleResult(ResultContext<? extends T> resultContext);

}

package com.intrack.logging;

/**
 * @author intrack
 */
public interface Log {

    boolean isDebugEnabled();

    boolean isTraceEnabled();

    void error(String info, Throwable cause);

    void error(String info);

    void warn(String info);

    void debug(String info);

    void trace(String info);

}

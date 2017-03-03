package com.intrack.logging.log4j;

import com.intrack.logging.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author intrack
 */
public class Log4jImpl implements Log {

    private static final String FQCN = Log4jImpl.class.getName();

    private Logger log;

    public Log4jImpl(String clazz) {
        log = Logger.getLogger(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void error(String info, Throwable cause) {
        log.log(FQCN, Level.ERROR, info, cause);
    }

    @Override
    public void error(String info) {
        log.log(FQCN, Level.ERROR, info, null);
    }

    @Override
    public void warn(String info) {
        log.log(FQCN, Level.WARN, info, null);
    }

    @Override
    public void debug(String info) {
        log.log(FQCN, Level.DEBUG, info, null);
    }

    @Override
    public void trace(String info) {
        log.log(FQCN, Level.TRACE, info, null);
    }

}

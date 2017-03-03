package com.intrack.logging;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.lang.reflect.Constructor;

/**
 * @author intrack
 */
public class LogFactory {

    private static Constructor<? extends Log> logConstructor;

    static {
        /* 获取log4j对象构造器 */
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useLog4jLogging();
            }
        });
    }

    public static Log getLog(Class<?> clazz) {
        return getLog(clazz.getName());
    }

    public static Log getLog(String logger) {
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new LogException("Error creating logger.");
        }
    }

    public static synchronized void useLog4jLogging() {
        setImplementation(com.intrack.logging.log4j.Log4jImpl.class);
    }

    private static void tryImplementation(Runnable runnable) {
        if (logConstructor == null) {
            runnable.run();
        }
    }

    private static void setImplementation(Class<? extends Log> implClass) {
        try {
            Constructor<? extends Log> constructor = implClass.getConstructor(String.class);
            Log log = constructor.newInstance(LogFactory.class.getName());
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized.");
            }

            logConstructor = constructor;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.");
        }
    }

}

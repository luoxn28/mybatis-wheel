package com.intrack.session;

import com.intrack.mapping.Environment;

/**
 * @author intrack
 */
public class Configuration {

    protected Environment environment;

    protected ExecutorType executorType = getDefaultExecutorType();

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ExecutorType getDefaultExecutorType() {
        return ExecutorType.SIMPLE;
    }

}

package com.intrack.executor;

import com.intrack.session.ResultHandler;

import java.util.List;

/**
 * @author intrack
 */
public interface Executor {

    ResultHandler NO_RESULT_HANDLER = null;

    <E> List<E> query(String statement, Object parameter);

}

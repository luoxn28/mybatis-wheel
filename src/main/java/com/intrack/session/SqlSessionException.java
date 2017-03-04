package com.intrack.session;

import com.intrack.exceptions.PersistenceException;

/**
 * @author intrack
 */
public class SqlSessionException extends PersistenceException {

    private static final long serialVersionUID = 3833184690240265047L;

    public SqlSessionException() {
        super();
    }

    public SqlSessionException(String message) {
        super(message);
    }

    public SqlSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlSessionException(Throwable cause) {
        super(cause);
    }

}

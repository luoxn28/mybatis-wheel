package com.intrack.io;

import com.intrack.exceptions.PersistenceException;

/**
 * @author intrack
 */
public class BuilderException extends PersistenceException {

    private static final long serialVersionUID = 3832784460240265467L;

    public BuilderException() {
        super();
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }

}

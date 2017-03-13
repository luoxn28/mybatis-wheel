package com.intrack.mapping;

import com.intrack.exceptions.PersistenceException;

/**
 * @author intrack
 */
public class MappingException extends PersistenceException {

    private static final long serialVersionUID = 4060977051977364130L;

    public MappingException() {
        super();
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

}

package com.intrack.exceptions;

/**
 * @author intrack
 */
public class TypeExecption extends PersistenceException {

    private static final long serialVersionUID = 8614420898975117130L;

    public TypeExecption() {
        super();
    }

    public TypeExecption(String message) {
        super(message);
    }

    public TypeExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeExecption(Throwable cause) {
        super(cause);
    }

}

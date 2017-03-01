package com.intrack.exceptions;

/**
 * Intrack base exception.
 *
 * @author intrack
 */
public class IntrackException extends RuntimeException {

    private static final long serialVersionUID = 3880206998166270511L;

    public IntrackException() {
        super();
    }

    public IntrackException(String message) {
        super(message);
    }

    public IntrackException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntrackException(Throwable cause) {
        super(cause);
    }

}

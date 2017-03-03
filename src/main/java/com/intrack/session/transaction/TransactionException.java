package com.intrack.session.transaction;

import com.intrack.exceptions.PersistenceException;

/**
 * @author intrack
 */
public class TransactionException extends PersistenceException {

    private static final long serialVersionUID = -433589569461084605L;

    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

}

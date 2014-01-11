package org.pieter.bdc.exceptions;

/**
 * An exception raised when there's something wrong with the config.
 * @author pieter
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 6422055112415508796L;

    /**
     * Exception constructor.
     * @param message The exception message.
     */
    public ValidationException(final String message) {
        super(message);
    }
}

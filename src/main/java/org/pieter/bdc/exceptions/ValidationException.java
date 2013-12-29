package org.pieter.bdc.exceptions;

/**
 * An exception raised when there's something wrong with the config.
 * @author pieter
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}

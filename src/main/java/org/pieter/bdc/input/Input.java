package org.pieter.bdc.input;

import java.util.Properties;

import org.pieter.bdc.exceptions.ValidationException;

/**
 * This describes general inputs.
 * @author pieter
 */
public interface Input {

    public Boolean validate(Properties props) throws ValidationException;
}

package org.pieter.bdc.input;

import java.util.Properties;

/**
 * This describes general inputs.
 * @author pieter
 */
public interface Input {

    /**
     * Validate the configuration of the input.
     * @param props The properties for the input
     * @return if the input is valid or not
     */
    Boolean validate(Properties props);
}

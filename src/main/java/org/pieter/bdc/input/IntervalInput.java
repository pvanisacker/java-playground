package org.pieter.bdc.input;

/**
 * This interface described interval triggered inputs.
 * @author pieter
 */
public interface IntervalInput extends Input {

    /**
     * Return the data from the input.
     * @return String with data.
     */
    String getData();
}

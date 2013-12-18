package org.pieter.bdc.input;

/**
 * @author pieter
 */
public class HTTPIntervalInput implements IntervalInput {

    String host;
    int port;
    String request;
    String proxy_host = null;
    String proxy_port = null;

    /**
     * This creates a HTTP Interval Input object.
     */
    public HTTPIntervalInput() {

    }

    /**
     * @param host The hostname to check
     * @param port The port to use
     * @param request The actual request
     */
    public HTTPIntervalInput(final String host, final int port, final String request) {
        this.host = host;
    }

    public final String getData() {
        return "";
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }
}

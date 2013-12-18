package org.pieter.bdc.input;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author pieter
 */
public class HTTPIntervalInput implements IntervalInput {

    private final String m_host;
    private final Integer m_port;
    private final String m_request;
    private final String m_proxy_host;
    private final String m_proxy_port;

    /**
     * This creates a HTTP Interval Input object.
     */
    public HTTPIntervalInput() {
        m_host = null;
        m_port = null;
        m_request = null;
        m_proxy_host = null;
        m_proxy_port = null;
    }

    /**
     * @param host The hostname to check
     * @param port The port to use
     * @param request The actual request
     */
    public HTTPIntervalInput(final String host, final int port, final String request) {
        m_host = host;
        m_port = port;
        m_request = request;
        m_proxy_host = null;
        m_proxy_port = null;
    }

    /**
     * Fetch the http response.
     * @return the response.
     */
    public final String getData() {
        HttpClient client = HttpClients.createDefault();
        return "";
    }

    /**
     * @return the hostname
     */
    public final String getHost() {
        return this.m_host;
    }

    /**
     * @return the port number.
     */
    public final int getPort() {
        return this.m_port;
    }
}

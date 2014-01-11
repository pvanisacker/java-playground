package org.pieter.bdc.input;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pieter.bdc.exceptions.ValidationException;

/**
 * @author pieter
 */
public class HTTPIntervalInput implements IntervalInput {

    /**
     * The logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger(HTTPIntervalInput.class.getName());
    private static final String PROPS_URI = "input.uri";
    private static final String PROPS_PROXY_HOST = "input.proxy_host";
    private static final String PROPS_PROXY_PORT = "input.proxy_port";

    private static final Integer TCP_PORT_MAX = 65535;
    private static final Integer TCP_PORT_MIN = 0;

    private URI mUri;
    private String mProxyHost;
    private Integer mProxyPort;

    /**
     * Fetch the http response.
     * @return the response.
     */
    @Override
    public final String getData() {
        String content = null;
        final HttpClient client = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet(this.mUri);

        HttpResponse response;
        try {
            response = client.execute(httpGet);
            LOG.debug(response.getStatusLine().toString());
            final HttpEntity entity = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return content;
    }

    /**
     * Set the URI.
     * @param uri The uri.
     */
    public final void setURI(final URI uri) {
        this.mUri = uri;
    }

    public final URI getURI() {
        return mUri;
    }

    public final void setProxyHost(final String proxy) {
        this.mProxyHost = proxy;
    }

    public final void setProxyPort(final Integer port) {
        this.mProxyPort = port;
    }

    /**
     * Check if all the config arguments provided are ok.
     * @param props Properties provided.
     * @return If the validation was ok or not.
     */
    @Override
    public final Boolean validate(final Properties props) {
        final List<String> errors = new ArrayList<String>();

        // check the provided url
        String url = props.getProperty(HTTPIntervalInput.PROPS_URI);
        if (url == null) {
            errors.add("No URI provided, can't continue");
        } else {
            // check if the URI is valid
            try {
                URI uri = new URI(url);
            } catch (URISyntaxException use) {
                errors.add("Invalid URL provided");
            }
        }

        // check the provided proxy hostname
        if (props.getProperty(HTTPIntervalInput.PROPS_PROXY_HOST) == null) {
            LOG.info("No proxy host set for the HTTP Interval Input");
        }

        // check the provided proxy port
        final String proxyPort = props.getProperty(HTTPIntervalInput.PROPS_PROXY_PORT);
        if (proxyPort == null || proxyPort.isEmpty()) {
            LOG.info("No proxy port set for the HTTP Interval Input");
        } else {
            final Integer port = Integer.parseInt(proxyPort);
            if (port == null) {
                LOG.error("Port is an invalid number");
            } else {
                if (port <= TCP_PORT_MIN || port >= TCP_PORT_MAX) {
                    LOG.error("Port is an invalid TCP port number");
                }
            }
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Input validation errors");
        }
        return true;
    }

    public final String getProxyHost() {
        return this.mProxyHost;
    }

    public final Integer getProxyPort() {
        return this.mProxyPort;
    }
}

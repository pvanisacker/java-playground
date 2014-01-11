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

    private URI uri;
    private String proxyHost;
    private Integer proxyPort;

    /**
     * Fetch the http response.
     * @return the response.
     */
    @Override
    public final String getData() {
        String content = null;
        final HttpClient client = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet(this.uri);

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
            LOG.error(ioe);
        }
        return content;
    }

    /**
     * Set the URI.
     * @param pUri The uri.
     */
    public final void setUri(final URI pUri) {
        this.uri = pUri;
    }

    public final URI getUri() {
        return uri;
    }

    public final void setProxyHost(final String proxy) {
        this.proxyHost = proxy;
    }

    public final void setProxyPort(final Integer port) {
        this.proxyPort = port;
    }

    /**
     * Check if all the config arguments provided are ok.
     * @param props Properties provided.
     * @return If the validation was ok or not.
     */
    @Override
    public final Boolean validate(final Properties props) {
        final List<String> errors = new ArrayList<String>();
        String result;

        // check the provided url
        final String url = props.getProperty(HTTPIntervalInput.PROPS_URI);
        result = this.validateUrl(url);
        if (result != null) {
            errors.add(result);
        }

        // check the provided proxy hostname
        final String proxy = props.getProperty(HTTPIntervalInput.PROPS_PROXY_HOST);
        result = this.validateProxyHost(proxy);
        if (result != null) {
            errors.add(result);
        }

        // check the provided proxy port
        final String proxyPort = props.getProperty(HTTPIntervalInput.PROPS_PROXY_PORT);
        result = this.validateProxyPort(proxyPort);
        if (result != null) {
            errors.add(result);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Input validation errors");
        }
        return true;
    }

    /**
     * Validate the request url.
     * @param url The url to validate.
     * @return The validation message or null if ok.
     */
    private String validateUrl(final String url) {
        String result = null;
        if (url == null) {
            result = "No URI provided, can't continue";
        } else {
            // check if the URI is valid
            try {
                final URI uri = new URI(url);
                if (!uri.getScheme().equals("http://") && !uri.getScheme().equals("https://")) {
                    result = "Invalid URL provided, make sure it's a valid http:// or https:// one.";
                }
            } catch (URISyntaxException use) {
                result = "Invalid URL provided";
            }
        }
        return result;
    }

    /**
     * Validate the proxy host if one is provided.
     * @param proxy the proxy
     * @return The validation message or null if ok.
     */
    private String validateProxyHost(final String proxy) {
        return null;
    }

    /**
     * Validate the proxy port if one is provided.
     * @param proxyPort the port.
     * @return The validation message or null if ok.
     */
    private String validateProxyPort(final String proxyPort) {
        String result = null;
        if (proxyPort == null || proxyPort.isEmpty()) {
            LOG.info("No proxy port set for the HTTP Interval Input");
        } else {
            final Integer port = Integer.parseInt(proxyPort);
            if (port == null) {
                result = "Proxy port is an invalid number";
            } else {
                if (port <= TCP_PORT_MIN || port >= TCP_PORT_MAX) {
                    result = "Proxy port is an invalid TCP port number";
                }
            }
        }
        return result;
    }

    public final String getProxyHost() {
        return this.proxyHost;
    }

    public final Integer getProxyPort() {
        return this.proxyPort;
    }
}

package org.pieter.bdc.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.pieter.bdc.exceptions.ValidationException;

/**
 * Test the HTTPIntervalInput class.
 * @author pieter
 */
public class HTTPIntervalInputTest {

    private HTTPIntervalInput input;

    /**
     * Initialize an empty HTTP Interval Input.
     */
    @Before
    public final void setUp() {
        input = new HTTPIntervalInput();
    }

    /**
     * Test if the data can be fetched.
     */
    @Test
    public final void testGetData() {
        fail("Not yet implemented");
    }

    /**
     * Test if the URI get's set correctly.
     * @throws URISyntaxException when URI is invalid.
     */
    @Test
    public final void testSetURI() throws URISyntaxException {
        final String url = "http://localhost:8080/test/page.php?test1=value1";
        final URI uri = new URI(url);
        input.setUri(uri);
        assertEquals("Test if URI is set correctly", input.getUri(), uri);
    }

    /**
     * Test if the Proxy host is set correctly.
     */
    @Test
    public final void testSetProxyHost() {
        final String proxy = "localhost";
        input.setProxyHost(proxy);
        assertEquals("Test if the proxy host is set correctly", input.getProxyHost(), proxy);
    }

    /**
     * Test if the proxy get's set correctly.
     */
    @Test
    public final void testSetProxyPort() {
        final Integer proxy = 80;
        input.setProxyPort(proxy);
        assertEquals("Test if the proxy port is set correctly", input.getProxyPort(), proxy);
    }

    /**
     * Test if all the parameters are valid.
     */
    @Test
    public final void testValidate() {
        final Properties props = new Properties();
        final String uri = "http://localhost:8080/test/page.txt?query1=args1";
        props.setProperty("input.uri", uri);
        props.setProperty("input.proxy_host", "localhost");
        props.setProperty("input.proxy_port", "1234");
        input.validate(props);
    }

    /**
     * Test if URI is valid.
     */
    @Test(expected = ValidationException.class)
    public final void testValidateURI() {
        final Properties props = new Properties();
        final String uri = "htp::://///localhost:1234567";
        props.setProperty("input.uri", uri);

        input.validate(props);

    }
}

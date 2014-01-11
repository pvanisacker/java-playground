package org.pieter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pieter.bdc.Importer;
import org.pieter.bdc.exceptions.ValidationException;

/**
 * Start class for the project.
 * @author pieter
 */
public class Start {

    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger(Start.class.getName());

    /**
     * Default config file to search on classpath.
     */
    private static final String CONFIG = "fetcher.properties";

    /**
     * Protected constructor to ensure this is not subclassed.
     */
    protected Start() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Start the complete thing.
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {
        final Properties prop = new Properties();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream stream = loader.getResourceAsStream(Start.CONFIG);
        if (stream == null) {
            LOG.error("Could not find needed {} file in the classpath", Start.CONFIG);
            System.exit(1);
        }
        try {
            prop.load(stream);
        } catch (IOException e) {
            LOG.error("Could not load configuration: {}", e.getMessage());
            System.exit(1);
        }

        final Importer imp = new Importer(prop);
        try {
            imp.validate();
        } catch (ValidationException e) {
            LOG.error("Error during validation: {}", e.getMessage());
            System.exit(1);
        }
        imp.start();
        LOG.info("Done");
    }
}

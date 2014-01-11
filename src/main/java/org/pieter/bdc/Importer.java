package org.pieter.bdc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pieter.bdc.exceptions.ValidationException;
import org.pieter.bdc.input.HTTPIntervalInput;
import org.pieter.bdc.output.FileOutput;
import org.pieter.bdc.processor.TrafficIncidentProcessor;

/**
 * This brings everyting together and runs the needed things.
 * @author pieter
 */
public class Importer {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger(Importer.class.getName());

    private final Properties m_props;
    private Boolean m_valid_config = false;

    /**
     * Create the importer.
     * @param props configuration for the importers
     */
    public Importer(final Properties props) {
        m_props = props;
    }

    /**
     * Validate the config in the properties file.
     */
    public final void validate() {
        final List<String> errors = new ArrayList<String>();

        // Check if the input class is ok, exists and stuff
        final String inputClass = m_props.getProperty("input.class");
        if (inputClass.trim().isEmpty()) {
            errors.add("Input class is not defined");
        } else if (inputClass != null && !inputClass.trim().isEmpty()) {
            try {
                Class.forName(inputClass);
            } catch (ClassNotFoundException exception) {
                errors.add("Could not find input class on classpath");
            }
        }

        // Check if the processor class is ok, exists and stuff
        final String processorClass = m_props.getProperty("processor.class");
        if (processorClass.trim().isEmpty()) {
            errors.add("Processor class is not defined");
        } else if (processorClass != null && !processorClass.trim().isEmpty()) {
            try {
                Class.forName(processorClass);
            } catch (ClassNotFoundException exception) {
                errors.add("Could not find processor class on classpath");
            }
        }

        // Check if the input class is ok, exists and stuff
        final String outputClass = m_props.getProperty("output.class");
        if (outputClass.trim().isEmpty()) {
            errors.add("Output class is not defined");
        } else if (outputClass != null && !outputClass.trim().isEmpty()) {
            try {
                Class.forName(outputClass);
            } catch (ClassNotFoundException exception) {
                errors.add("Could not find output class on classpath");
            }
        }

        if (!errors.isEmpty()) {
            for (String error : errors) {
                LOG.error(error);
            }
            throw new ValidationException("Config file is not ok, exiting");
        }
        m_valid_config = true;
        LOG.info("Validation finished ok");
    }

    /**
     * This starts the complete process.
     */
    public final void start() {
        if (m_valid_config) {
            LOG.info("Start processing");
            // Do a request
            String data = null;
            URI uri = null;
            try {
                uri = new URI(m_props.getProperty("input.uri"));
            } catch (URISyntaxException ure) {
                LOG.error(ure.getMessage());
            }
            final HTTPIntervalInput input = new HTTPIntervalInput();
            input.setURI(uri);
            data = input.getData();
            LOG.info("Got data: " + data.length());

            // process file
            final TrafficIncidentProcessor tip = new TrafficIncidentProcessor();
            final List<String> results = tip.process(data);

            final FileOutput fwriter = new FileOutput();
            fwriter.writeList(results);
            LOG.info("Done");
        } else {
            LOG.error("Please validate the importer before starting it!");
        }
    }
}

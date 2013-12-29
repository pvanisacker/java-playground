package org.pieter.bdc.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Figure out the seperate traffic incidents from the JSON response.
 * @author pieter
 */
public class TrafficIncidentProcessor {

    /**
     * The logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger(TrafficIncidentProcessor.class.getName());

    /**
     * Process the data, extract the needed things and return a list of results.
     * @param data a string of data to be processed.
     * @return a list of strings containing records to written.
     */
    public final List<String> process(final String data) {
        LOG.debug("Starting with the processing");
        final ArrayList<String> results = new ArrayList<String>();
        final ObjectMapper mapper = new ObjectMapper();

        try {
            final JsonNode rootNode = mapper.readValue(data, JsonNode.class);
            final JsonNode array = rootNode.get("TRAFFICITEMS").get("TRAFFICITEM");
            for (final JsonNode node : array) {
                results.add(node + "\r\n");
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        LOG.debug("Finished with the processing");
        return results;
    }
}

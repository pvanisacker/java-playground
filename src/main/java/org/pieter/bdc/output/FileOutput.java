package org.pieter.bdc.output;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Writes all the records to a file.
 * @author pieter
 */
public class FileOutput {

    /**
     * The logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger(FileOutput.class.getName());

    /**
     * Takes a list of strings and writes them to a file.
     * @param list a list of strings
     */
    public final void writeList(final List<String> list) {
        final StringBuilder result = new StringBuilder();
        for (final String str : list) {
            result.append(str);
        }

        // store to file
        PrintWriter writer;
        try {
            writer = new PrintWriter("test.txt");
            writer.print(result);
            writer.close();
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }
}

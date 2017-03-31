package com.intrack.io;

import com.intrack.io.parser.XMLConfigParser;
import com.intrack.session.Configuration;

import java.io.InputStream;
import java.util.Properties;


/**
 * Configuration file parser.
 *
 * @author intrack
 */
public class XMLConfigBuilder {

    private boolean parsed;

    private InputStream inputStream;
    private String environment;
    private Properties properties;

    public XMLConfigBuilder(InputStream inputStream, String environment, Properties properties) {
        this.inputStream = inputStream;
        this.environment = environment;
        this.properties = properties;

        /* Default is not parsed */
        this.parsed = false;
    }

    public Configuration parse() {
        if (parsed) {
            throw new BuilderException("Each XMLConfigBuilder can only be used once.");
        }

        parsed = true;
        return new XMLConfigParser(inputStream).parse();
    }

}

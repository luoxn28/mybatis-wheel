package com.intrack.io;

import com.intrack.io.parser.XMLConfigParser;
import com.intrack.session.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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

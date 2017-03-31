package com.intrack.io.parser;

import com.intrack.io.BuilderException;
import com.intrack.session.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author intrack
 */
public class XMLConfigParser {

    private InputStream inputStream;
    private Configuration configuration;

    Document document = null;

    public XMLConfigParser(InputStream inputStream) {
        this.inputStream = inputStream;
        this.configuration = new Configuration();
    }

    public Configuration parse() {
        initParser();

        Element configurationNode = configurationNode();
        Element mappersNode = mappersNode(configurationNode);

        List<String> mappers = parseAllMapper(mappersNode);
        if (mappers.size() > 0) {
            XMLMapperParser mapperParser = new XMLMapperParser(configuration);
            for (String mapper : mappers) {
                System.out.println(mapper);
                try {
                    mapperParser.parse(mapper);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    // ----------------------------------------------------- private scope

    private void initParser() {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(inputStream);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse configuration node.
     */
    private Element configurationNode() {
        NodeList nodeList = document.getElementsByTagName("configuration");
        if (nodeList.getLength() != 1) {
            throw new BuilderException("configuration is only node in config file.");
        }

        return (Element) nodeList.item(0);
    }

    /**
     * Parse mappers node.
     */
    private Element mappersNode(Element configurationNode) {
        NodeList nodeList = configurationNode.getElementsByTagName("mappers");
        if (nodeList.getLength() != 1) {
            throw new BuilderException("mappers is only node in config file.");
        }

        return (Element) nodeList.item(0);
    }

    /**
     * Parse all mapper nodes.
     */
    private List<String> parseAllMapper(Element mappersNode) {
        List<String> mappers = new ArrayList<>();

        NodeList mapperList = mappersNode.getElementsByTagName("mapper");
        for (int i = 0; i < mapperList.getLength(); i++) {
            mappers.add(mapperList.item(i).getAttributes().getNamedItem("resource").getNodeValue());
        }

        return mappers;
    }

}

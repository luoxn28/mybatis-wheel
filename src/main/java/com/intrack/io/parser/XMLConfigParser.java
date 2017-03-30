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
import java.io.IOException;
import java.io.InputStream;

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

    private void parseInternal() {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList nodeList = document.getElementsByTagName("configuration");
            if (nodeList.getLength() != 1) {
                throw new BuilderException("configuration is only node in config file.");
            }

            Element configurationNode = (Element) nodeList.item(0);
            nodeList = configurationNode.getElementsByTagName("mappers");
            if (nodeList.getLength() != 1) {
                throw new BuilderException("mappers is only node in config file.");
            }

            Element mappersNode = (Element) nodeList.item(0);
            NodeList mapperList = mappersNode.getElementsByTagName("mapper");
            for (int i = 0; i < mapperList.getLength(); i++) {
                System.out.println(mapperList.item(i).getAttributes().getNamedItem("resource").getNodeValue());
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

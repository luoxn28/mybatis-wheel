package com.intrack.io.parser;

import com.intrack.io.BuilderException;
import com.intrack.session.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author intrack
 */
public class XMLMapperParser {

    private Configuration configuration;

    Document document = null;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(String filename) throws FileNotFoundException {
        parse(new FileInputStream(filename));
    }

    public void parse(InputStream inputStream) {
        initParser(inputStream);

        Element mapperNode = mapperNode();

        /**
         * Parse all statement. put it to configuration.
         */
        parseAllNodes(mapperNode);
    }

    // ----------------------------------------------------- private scope

    private void initParser(InputStream inputStream) {
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
     * Parse mapper node.
     */
    private Element mapperNode() {
        NodeList nodeList = document.getElementsByTagName("mapper");
        if (nodeList.getLength() != 1) {
            throw new BuilderException("mapper is only node in config file.");
        }

        return (Element) nodeList.item(0);
    }

    /**
     * Parse all mapper nodes.
     */
    private void parseAllNodes(Element mapperNode) {
        if (!mapperNode.hasAttribute("namespace")) {
            throw new BuilderException("not find namespace attribute.");
        }

        String namespace = mapperNode.getAttribute("namespace");

        /* parse select nodes */
        NodeList selectList = mapperNode.getElementsByTagName("select");
        for (int i = 0; i < selectList.getLength(); i++) {
            MapperNode node = new MapperNode("select", namespace, selectList.item(i));
            configuration.addMapperNode(node);
        }

        /* parse update node */
        NodeList updateList = mapperNode.getElementsByTagName("update");
        for (int i = 0; i < updateList.getLength(); i++) {
            MapperNode node = new MapperNode("update", namespace, updateList.item(i));
            configuration.addMapperNode(node);
        }

        /* parse insert node */
        NodeList insertList = mapperNode.getElementsByTagName("insert");
        for (int i = 0; i < insertList.getLength(); i++) {
            MapperNode node = new MapperNode("insert", namespace, insertList.item(i));
            configuration.addMapperNode(node);
        }

        /* parse delete node */
        NodeList deleteList = mapperNode.getElementsByTagName("delete");
        for (int i = 0; i < deleteList.getLength(); i++) {
            MapperNode node = new MapperNode("delete", namespace, deleteList.item(i));
            configuration.addMapperNode(node);
        }
    }



}

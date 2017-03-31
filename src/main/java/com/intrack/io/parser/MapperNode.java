package com.intrack.io.parser;

import org.w3c.dom.Node;

/**
 * @author intrack
 */
public class MapperNode {

    private String name; /* select/update/delete/insert */
    private String id;
    private String parameterType;
    private String resultType;

    public MapperNode(String name) {
        this("", null);
    }

    public MapperNode(Node node) {
        this("", node);
    }

    public MapperNode(String name, Node node) {
        if (name != null) {
            this.name = name;
        }

        if (node != null) {
            this.id = getId(node);
            this.parameterType = getParameterType(node);
            this.resultType = getResultType(node);
        }
    }

    public MapperNode(String name, String namespace, Node node) {
        if (name != null) {
            this.name = name;
        }

        if (node != null) {
            this.id = namespace + "." + getId(node);
            this.parameterType = getParameterType(node);
            this.resultType = getResultType(node);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    // ----------------------------------------------------- private scope

    /* Get id attribute */
    private String getId(Node node) {
        return node.getAttributes().getNamedItem("id").getNodeValue();
    }

    /* Get parameterType attribute */
    private String getParameterType(Node node) {
        return node.getAttributes().getNamedItem("parameterType").getNodeValue();
    }

    /* Get resultType attribute */
    private String getResultType(Node node) {
        return node.getAttributes().getNamedItem("resultType").getNodeValue();
    }
}

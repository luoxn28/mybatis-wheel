package com.intrack.io.parser;

import com.intrack.io.BuilderException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author intrack
 */
public class MapperNode {

    private String name; /* select/update/delete/insert */
    private String id;
    private String parameterType;
    private String resultType;
    private String sql;

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
            this.sql = getSql(node);
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

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "MapperNode{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", resultType='" + resultType + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }

    // ----------------------------------------------------- private scope

    /* Get id attribute */
    private String getId(Node node) {
        Node id = node.getAttributes().getNamedItem("id");
        if (id != null) {
            return id.getNodeValue();
        } else {
            return null;
        }
    }

    /* Get parameterType attribute */
    private String getParameterType(Node node) {
        Node parameterType = node.getAttributes().getNamedItem("parameterType");
        if (parameterType != null) {
            return parameterType.getNodeValue();
        } else {
            return null;
        }
    }

    /* Get resultType attribute */
    private String getResultType(Node node) {
        Node resultType = node.getAttributes().getNamedItem("resultType");
        if (resultType != null) {
            return resultType.getNodeValue();
        } else {
            return null;
        }
    }

    /* Get sql value */
    private String getSql(Node node) {
        Node sqlNode = node.getFirstChild();
        if (sqlNode != null) {
            return sqlNode.getNodeValue().trim();
        } else {
            throw new BuilderException("Not find sql in mapper config file.");
        }
    }

}

package io.github.qishr.cascara.lang.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XmlNode {
    public String name;
    public Map<String, String> attributes = new HashMap<>();
    public String value = "";
    public List<XmlNode> children = new ArrayList<>();

    public XmlNode(String name) {
        this.name = name;
    }

    public void addChild(XmlNode child) {
        this.children.add(child);
    }

    public List<XmlNode> getChildren() {
        return children;
    }

    public XmlNode getChild(String name) {
        for (XmlNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    public String getTextValue() {
        return this.value;
    }

    public String getAttribute(String attributeName) {
        return this.attributes.get(attributeName);
    }

    public Set<String> getAttributeNames() {
        return this.attributes.keySet();
    }

    public String getName() {
        return name;
    }
}
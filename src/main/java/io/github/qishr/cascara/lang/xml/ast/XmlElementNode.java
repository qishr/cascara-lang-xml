package io.github.qishr.cascara.lang.xml.ast;

// import java.util.List;

// public class XmlElementNode extends XmlNode {
//     private String tagName;
//     private XmlAttributeMapNode attributes; // Implements MapAstNode
//     private List<XmlNode> children;

//     public XmlElementNode(String name) {
//         super(name);
//     }

//     public String getName() { return tagName; }

//     // This allows your SVG code to work generically across all Cascara maps
//     public String getAttribute(String key) {
//         return attributes.getString(key);
//     }
// }

// package io.github.qishr.cascara.lang.xml.ast;

import io.github.qishr.cascara.common.lang.ast.AstNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlElementNode extends XmlNode {
    private final String name;
    private String value = "";
    private final Map<String, String> attributes = new HashMap<>();
    private final List<XmlNode> children = new ArrayList<>();

    public XmlElementNode(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public Map<String, String> getAttributes() { return attributes; }

    @Override
    public List<XmlNode> getChildren() { return children; }

    // Helper to match your SVG renderer's needs
    public String getAttributeOrDefault(String key, String defaultValue) {
        return attributes.getOrDefault(key, defaultValue);
    }
}
package io.github.qishr.cascara.lang.xml.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.qishr.cascara.common.lang.ast.AstNode;
import io.github.qishr.cascara.common.lang.ast.CommentAstNode;
import io.github.qishr.cascara.lang.xml.token.XmlToken;

public class XmlNode implements AstNode {
    private final int startLine;
    private final int startColumn;
    private final int endLine = 0;
    private final int endColumn = 0;
    private final List<CommentAstNode> comments = new ArrayList<>();
    private String anchor;
    private XmlToken token;


    public String name;
    public Map<String, String> attributes = new HashMap<>();
    public String value = "";
    public List<XmlNode> children = new ArrayList<>();

    public XmlNode(String name) {
        this.name = name;
        startLine = 0;
        startColumn = 0;
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








    /// {@inheritDoc}
    @Override public int getStartLine() { return startLine; }

    /// {@inheritDoc}
    @Override public int getStartColumn() { return startColumn; }

    /// {@inheritDoc}
    @Override public int getEndLine() { return endLine; }

    /// {@inheritDoc}
    @Override public int getEndColumn() { return endColumn; }

    /// {@inheritDoc}
    @Override public List<CommentAstNode> getComments() { return comments; }


    public String getAttributeOrDefault(String name, String dflt) {
        String value = attributes.get(name);
        if (value == null) {
            value = dflt;
        }
        return value;
    }
}
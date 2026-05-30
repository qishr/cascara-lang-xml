package io.github.qishr.cascara.lang.xml.processor;

import io.github.qishr.cascara.common.lang.processor.Parser;
import io.github.qishr.cascara.lang.xml.XmlDocument;
import io.github.qishr.cascara.lang.xml.ast.XmlNode;
import io.github.qishr.cascara.lang.xml.token.XmlToken;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class XmlParser extends AbstractXmlProcessor<XmlParser> implements Parser<XmlDocument, XmlToken> {
    private List<XmlToken> tokens;
    private URI uri;

    public XmlParser() {

    }

    @Override protected XmlParser self() { return this; }

    public XmlDocument parse(String xmlString) {
        return parse(xmlString, null);
    }

    public XmlDocument parse(String xmlString, URI uri) {
        XmlTokenizer tokenizer = new XmlTokenizer();
        this.tokens = tokenizer.tokenize(xmlString, uri);
        return parseDocument(tokens, uri);
    }

    /// {@inheritDoc}
    @Override
    public XmlDocument parse(List<XmlToken> tokens) {
        return parse(tokens, null);
    }


    /// {@inheritDoc}
    @Override
    public XmlDocument parse(List<XmlToken> tokens, URI uri) {
        return parseDocument(tokens, uri);
    }


    public XmlDocument parseDocument(List<XmlToken> tokens, URI uri) {
        this.uri = uri;
        this.tokens = tokens;
        Deque<XmlNode> nodeStack = new ArrayDeque<>();
        XmlNode rootNode = null;

        for (XmlToken token : tokens) {
            switch (token.getType()) {
                case TAG_START:
                    // Create a new node and push it onto the stack
                    XmlNode newNode = new XmlNode(token.getLexeme().replaceAll("[</>]", "")); // Clean up tag name

                    if (rootNode == null) {
                        rootNode = newNode;
                    }
                    nodeStack.push(newNode);
                    break;

                case ATTR_NAME:
                    // Attributes must immediately follow a START_TAG.
                    if (!nodeStack.isEmpty()) {
                        XmlNode current = nodeStack.peek();
                        // This logic requires parsing the lexeme (e.g., 'name="value"')
                        String lexeme = token.getLexeme();
                        String name = lexeme.substring(0, lexeme.indexOf('=')).trim();
                        String value = lexeme.substring(lexeme.indexOf('=') + 1).replaceAll("[\"']", "").trim();
                        current.attributes.put(name, value);
                    }
                    break;

                case TEXT:
                    // Append text/value to the current node at the top of the stack
                    if (!nodeStack.isEmpty()) {
                        XmlNode current = nodeStack.peek();
                        // Use a method to get the clean text value
                        current.value += token.getLexeme().trim();
                    }
                    break;

                case TAG_END:
                    // Pop the node and attach it to its parent
                    if (!nodeStack.isEmpty()) {
                        XmlNode completedNode = nodeStack.pop();

                        if (!nodeStack.isEmpty()) {
                            nodeStack.peek().addChild(completedNode);
                        }
                    }
                    break;

                // You can ignore or log other token types like COMMENT, CDATA, etc.
                default:
                    // Pass
                    break;
            }
        }
        return new XmlDocument(rootNode);
    }

    private void error(XmlToken token, String message) {
        reporter.errorAt(token, uri, message);
    }
}
package io.github.qishr.cascara.lang.xml.processor;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.lang.xml.XmlDocument;
import io.github.qishr.cascara.lang.xml.ast.XmlNode;
import io.github.qishr.cascara.lang.xml.exception.XmlException;
import io.github.qishr.cascara.lang.xml.token.XmlToken;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class XmlParser {
    private List<XmlToken> tokens;

//    public XmlParserImpl(List<XmlToken> tokens) {
//        this.tokens = tokens;
//    }

    public XmlParser() {

    }

    public XmlDocument parse(String xmlString) throws XmlException {
        XmlTokenizer tokenizer = new XmlTokenizer();
        this.tokens = tokenizer.tokenize(xmlString);
        Deque<XmlNode> nodeStack = new ArrayDeque<>();
        XmlNode rootNode = null;

        for (XmlToken token : tokens) {
            switch (token.getType()) {
                case START_TAG:
                    // Create a new node and push it onto the stack
                    XmlNode newNode = new XmlNode(token.getLexeme().replaceAll("[</>]", "")); // Clean up tag name

                    if (rootNode == null) {
                        rootNode = newNode;
                    }
                    nodeStack.push(newNode);
                    break;

                case ATTRIBUTE:
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

                case END_TAG:
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

    // @Override
    public void setReporter(Reporter reporter) {

    }
}
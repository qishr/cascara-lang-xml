package io.github.qishr.cascara.lang.xml.processor;

import io.github.qishr.cascara.common.diagnostic.code.DiagnosticCode;
import io.github.qishr.cascara.common.lang.exception.ParserException;
import io.github.qishr.cascara.common.lang.processor.Parser;
import io.github.qishr.cascara.lang.xml.ast.XmlNode;
import io.github.qishr.cascara.lang.xml.token.XmlToken;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class XmlParser extends AbstractXmlProcessor<XmlParser> implements Parser<XmlNode, XmlToken> {

    public XmlParser() {
        // Default constructor for SPI
    }

    @Override protected XmlParser self() { return this; }

    @Override
    public XmlNode parse(String text) {
        XmlTokenizer tokenizer = new XmlTokenizer();
        tokenizer.setOptions(options);
        tokenizer.setReporter(reporter);
        return parse(tokenizer.tokenize(text));
    }

    /// {@inheritDoc}
    @Override
    public XmlNode parse(List<XmlToken> tokens) {
        // this.tokens = tokens;
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
        return rootNode;
    }

    private void error(XmlToken token, DiagnosticCode code, Object... details) {
        reporter.errorAt(token, code, details);
        if (!reporter.collectsProblems()) {
            throw new ParserException(token, code, details);
        }
    }

    @Override
    public XmlNode parse(InputStream is) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parse'");
    }
}
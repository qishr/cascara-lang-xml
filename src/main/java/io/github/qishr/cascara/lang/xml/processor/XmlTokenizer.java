package io.github.qishr.cascara.lang.xml.processor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import io.github.qishr.cascara.common.lang.processor.Tokenizer;
import io.github.qishr.cascara.lang.xml.token.XmlToken;
import io.github.qishr.cascara.lang.xml.token.XmlTokenType;

public class XmlTokenizer extends AbstractXmlProcessor<XmlTokenizer> implements Tokenizer<XmlToken> {
    // private String source;
    private int line;
    private int column;
    private int offset;
    private List<XmlToken> tokens = new ArrayList<>();

    public XmlTokenizer() {
    }

    @Override protected XmlTokenizer self() { return this; }

    @Override
    public List<XmlToken> tokenize(String source) {
        tokens.clear();

        if (source == null) {
            error("Null source string", "");
            return tokens;
        }
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();

            // This is necessary to get accurate location information, especially for offsets
            factory.setProperty(XMLInputFactory.IS_COALESCING, false);
            InputStream stream = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));

            XMLStreamReader reader = factory.createXMLStreamReader(stream);

            while (reader.hasNext()) {
                int eventType = reader.next();

                // Get the location information for the CURRENT event
                Location loc = reader.getLocation();
                line = loc.getLineNumber();
                column = loc.getColumnNumber();
                // The JAXP Location API provides the exact character offset
                offset = loc.getCharacterOffset();

                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        // 1. Token for the opening tag name
                        String tagName = reader.getLocalName();
                        // tokens.add(new XmlToken(XmlTokenType.TAG_START, "<" + tagName + ">", line, column, offset));
                        addToken(XmlTokenType.TAG_START, "<" + tagName + ">");

                        // 2. Tokens for all attributes
                        for (int i = 0; i < reader.getAttributeCount(); i++) {
                            String attrLexeme = String.format("%s=\"%s\"",
                                                            reader.getAttributeLocalName(i),
                                                            reader.getAttributeValue(i));
                            // Note: Location of attributes is generally the same as the start tag event
                            // tokens.add(new XmlToken(XmlTokenType.ATTR_NAME, attrLexeme, line, column, offset));
                            addToken(XmlTokenType.ATTR_NAME, attrLexeme);
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        // Token for the closing tag
                        // token = new XmlToken(XmlTokenType.TAG_END, "</" + reader.getLocalName() + ">", line, column, offset);
                        addToken(XmlTokenType.TAG_END, "</" + reader.getLocalName() + ">");
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        // Token for the element text content (lexeme is the actual content)
                        String text = reader.getText();
                        if (text.trim().length() > 0) {
                            // token = new XmlToken(XmlTokenType.TEXT, text, line, column, offset);
                            addToken(XmlTokenType.TEXT, text);
                        }
                        break;

                    case XMLStreamConstants.COMMENT:
                        // Token for comments
                        // token = new XmlToken(XmlTokenType.COMMENT, reader.getText(), line, column, offset);
                        addToken(XmlTokenType.COMMENT, reader.getText());
                        break;

                    // Add cases for CDATA, PI, etc., as needed for full highlighting
                }

                // if (token != null) {
                //     tokens.add(token);
                // }
            }
            return tokens;
        } catch (XMLStreamException e) {
            // throw new XmlParserException("XML tokenizer error: " + e.getMessage(), e);
            error("XML tokenizer error: " + e.getMessage(), "");
            return tokens;
        }
    }

    //
    //
    //

    private void error(String message, String lexeme) {
        XmlToken token = addToken(XmlTokenType.ERROR, lexeme);
        reporter.errorAt(token, null, message);
    }

    private XmlToken addToken(XmlTokenType type, String text) {
        // String text = source.substring(start, current);
        // If we finished 'schema' at col 15, 15 - 6 = 9.
        int tokenColumn = column - text.length();
        return addToken(new XmlToken(type, text, text, offset, line, tokenColumn));
    }

    private XmlToken addToken(XmlToken token) {
        tokens.add(token);
        return token;
    }
}
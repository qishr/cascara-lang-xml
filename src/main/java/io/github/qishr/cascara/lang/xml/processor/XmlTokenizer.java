package io.github.qishr.cascara.lang.xml.processor;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.lang.xml.exception.XmlException;
import io.github.qishr.cascara.lang.xml.token.XmlToken;
import io.github.qishr.cascara.lang.xml.token.XmlTokenType;

import javax.xml.stream.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class XmlTokenizer {

    public XmlTokenizer() {
    }

    public List<XmlToken> tokenize(String xmlString) throws XmlException {
        if (xmlString == null) {
            throw new XmlException("Null source string", null);
        }
        try {
            List<XmlToken> tokens = new ArrayList<>();
            XMLInputFactory factory = XMLInputFactory.newInstance();

            // This is necessary to get accurate location information, especially for offsets
            factory.setProperty(XMLInputFactory.IS_COALESCING, false);
            InputStream stream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));

            XMLStreamReader reader = factory.createXMLStreamReader(stream);

            while (reader.hasNext()) {
                int eventType = reader.next();

                // Get the location information for the CURRENT event
                Location loc = reader.getLocation();
                int line = loc.getLineNumber();
                int column = loc.getColumnNumber();
                // The JAXP Location API provides the exact character offset
                int offset = loc.getCharacterOffset();

                XmlToken token = null;

                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        // 1. Token for the opening tag name
                        String tagName = reader.getLocalName();
                        tokens.add(new XmlToken(XmlTokenType.TAG_START, "<" + tagName + ">", line, column, offset));

                        // 2. Tokens for all attributes
                        for (int i = 0; i < reader.getAttributeCount(); i++) {
                            String attrLexeme = String.format("%s=\"%s\"",
                                                            reader.getAttributeLocalName(i),
                                                            reader.getAttributeValue(i));
                            // Note: Location of attributes is generally the same as the start tag event
                            tokens.add(new XmlToken(XmlTokenType.ATTR_NAME, attrLexeme, line, column, offset));
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        // Token for the closing tag
                        token = new XmlToken(XmlTokenType.TAG_END, "</" + reader.getLocalName() + ">", line, column, offset);
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        // Token for the element text content (lexeme is the actual content)
                        String text = reader.getText();
                        if (text.trim().length() > 0) {
                            token = new XmlToken(XmlTokenType.TEXT, text, line, column, offset);
                        }
                        break;

                    case XMLStreamConstants.COMMENT:
                        // Token for comments
                        token = new XmlToken(XmlTokenType.COMMENT, reader.getText(), line, column, offset);
                        break;

                    // Add cases for CDATA, PI, etc., as needed for full highlighting
                }

                if (token != null) {
                    tokens.add(token);
                }
            }
            return tokens;
        } catch (XMLStreamException e) {
            throw new XmlException("XML tokenizer error: " + e.getMessage(), e);
        }
    }

    // @Override
    public void setReporter(Reporter reporter) {

    }

    // public static void main(String[] args) {
    //     try {
    //         // Replace with your XML file path
    //         List<Token> tokens = new StaxTokenizer().tokenize("data.xml");

    //         // Print the first 10 tokens for verification
    //         tokens.stream().limit(10).forEach(System.out::println);

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
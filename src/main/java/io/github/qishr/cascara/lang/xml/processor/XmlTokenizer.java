// # License & Terms
//
// This file is part of **Cascara**.
//
// **Cascara** is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.
//
// ---
//
// ## Special Runtime Exception
//
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent modules,
// and to copy and distribute the resulting executable under terms of your
// choice, provided that you also meet, for each linked independent module,
// the terms and conditions of the license of that module.
//
// An independent module is a module which is not derived from or based on
// this library. If you modify this library, you may extend this exception
// to your version of the library, but you are not obligated to do so. If
// you do not wish to do so, delete this exception statement from your
// version.


package io.github.qishr.cascara.lang.xml.processor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
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
        reporter.errorAt(token, GenericDiagnosticCode.ERROR, message);
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

    @Override
    public void open(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public void open(InputStream is) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public XmlToken nextToken() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nextToken'");
    }

    @Override
    public void open(Reader reader) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'open'");
    }
}
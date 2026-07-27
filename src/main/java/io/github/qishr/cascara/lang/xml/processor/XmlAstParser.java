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

import io.github.qishr.cascara.common.diagnostic.code.DiagnosticCode;
import io.github.qishr.cascara.common.lang.exception.ParserException;
import io.github.qishr.cascara.common.lang.processor.AstParser;
import io.github.qishr.cascara.common.lang.processor.Tokenizer;
import io.github.qishr.cascara.lang.xml.ast.XmlNode;
import io.github.qishr.cascara.lang.xml.token.XmlToken;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class XmlAstParser extends AbstractXmlProcessor<XmlAstParser> implements AstParser<XmlNode, XmlToken, XmlTokenizer> {

    public XmlAstParser() {
        // Default constructor for SPI
    }

    @Override protected XmlAstParser self() { return this; }

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

    @Override
    public XmlNode parse(Reader reader) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parse'");
    }

    @Override
    public XmlNode parse(XmlTokenizer tokenizer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parse'");
    }

    @Override
    public List<XmlToken> getTokens() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTokens'");
    }

    @Override
    public XmlTokenizer getTokenizer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTokenizer'");
    }
}
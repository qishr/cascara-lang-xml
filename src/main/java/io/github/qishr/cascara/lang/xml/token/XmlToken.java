package io.github.qishr.cascara.lang.xml.token;

import io.github.qishr.cascara.common.lang.token.Token;

public class XmlToken implements Token {
    private final XmlTokenType type;
    private final String lexeme;
    private final int line;
    private final int column;
    private final int offset;

    public XmlToken(XmlTokenType type, String lexeme, int line, int column, int offset) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return String.format("Token[Type=%s, Lexeme='%.10s...', Line=%d, Col=%d, Offset=%d]",
                             type, lexeme.replace('\n', ' '), line, column, offset);
    }

    public XmlTokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getStartLine() {
        return line;
    }

    public int getStartColumn() {
        return column;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public Object getValue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValue'");
    }
}
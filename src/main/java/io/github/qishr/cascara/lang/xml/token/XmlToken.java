package io.github.qishr.cascara.lang.xml.token;

import io.github.qishr.cascara.common.lang.token.Token;

public class XmlToken implements Token {
    private final XmlTokenType type;
    private final String lexeme;
    private final String content;
    private final int offset;
    private final int line;
    private final int column;

    public XmlToken(XmlTokenType type, String lexeme, String content,  int offset, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.content = content;
        this.offset = offset;
        this.line = line;
        this.column = column;
    }

    // public XmlToken(XmlTokenType type, String lexeme, int line, int column, int offset) {
    //     this.type = type;
    //     this.lexeme = lexeme;
    //     this.line = line;
    //     this.column = column;
    //     this.offset = offset;
    // }

    @Override
    public String toString() {
        return String.format("Token[Type=%s, Lexeme='%.10s...', Line=%d, Col=%d, Offset=%d]",
                             type, lexeme.replace('\n', ' '), line, column, offset);
    }

    @Override
    public XmlTokenType getType() {
        return type;
    }

    @Override
    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getStartLine() {
        return line;
    }

    @Override
    public int getStartColumn() {
        return column;
    }

    @Override
    public int getOffset() {
        return offset;
    }
}
package io.github.qishr.cascara.lang.xml;

public class XmlToken {
    public enum Type { START_TAG, END_TAG, ATTRIBUTE, TEXT, COMMENT, CDATA, PROCESSING_INSTRUCTION }

    private final Type type;
    private final String lexeme;
    private final int line;
    private final int column;
    private final long offset;

    public XmlToken(Type type, String lexeme, int line, int column, long offset) {
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

    public Type getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public long getOffset() {
        return offset;
    }


}
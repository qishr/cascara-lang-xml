package io.github.qishr.cascara.lang.xml;

import java.net.URI;

import io.github.qishr.cascara.common.lang.exception.LocatableException;

public class XmlException extends Exception implements LocatableException {
    // Common constants for LocatableException implementations
    public static final int UNKNOWN_COORD = -1;

    private final int line;
    private final int column;
    private final URI uri;

    // Standard constructor for parser-detected logic errors
    public XmlException(String message, int line, int column, URI uri) {
        super(String.format("%s (at L:%d C:%d)", message, line, column));
        this.line = line;
        this.column = column;
        this.uri = uri;
    }

    // The "Wrapper" constructor for I/O or Stream failures
    public XmlException(String message, Throwable cause, int line, int column, URI uri) {
        super(String.format("%s (Context: L:%d C:%d)", message, line, column), cause);
        this.line = line;
        this.column = column;
        this.uri = uri;
    }

    // Constructor for when we only have a URI (e.g. File not found)
    public XmlException(String message, Throwable cause, URI uri) {
        this(message, cause, UNKNOWN_COORD, UNKNOWN_COORD, uri);
    }

    @Override public int getLine() { return line; }
    @Override public int getColumn() { return column; }
    @Override public URI getUri() { return uri; }
}
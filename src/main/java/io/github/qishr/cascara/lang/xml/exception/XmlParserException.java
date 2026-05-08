package io.github.qishr.cascara.lang.xml.exception;

import java.net.URI;

import io.github.qishr.cascara.common.lang.exception.LanguageException;

public class XmlParserException extends LanguageException {


    public XmlParserException(String message, Throwable cause) {
        super(message, cause, UNKNOWN_COORD, UNKNOWN_COORD, null);
    }

    public XmlParserException(String message, int line, int column, URI uri) {
        super(message, line, column, uri);
    }

    public XmlParserException(String message, Throwable cause, int line, int column, URI uri) {
        super(message, cause, line, column, uri);
    }
}
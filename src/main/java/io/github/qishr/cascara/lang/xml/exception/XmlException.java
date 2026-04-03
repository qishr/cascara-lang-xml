package io.github.qishr.cascara.lang.xml.exception;

import java.net.URI;

import io.github.qishr.cascara.common.lang.exception.LanguageException;

public class XmlException extends LanguageException {


    public XmlException(String message, Throwable cause) {
        super(message, cause, UNKNOWN_COORD, UNKNOWN_COORD, null);
    }

    public XmlException(String message, int line, int column, URI uri) {
        super(message, line, column, uri);
    }

    public XmlException(String message, Throwable cause, int line, int column, URI uri) {
        super(message, cause, line, column, uri);
    }
}
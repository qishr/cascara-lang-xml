module cascara.lang.xml {
    requires transitive cascara.common;

    requires java.xml;

    exports io.github.qishr.cascara.lang.xml;
    exports io.github.qishr.cascara.lang.xml.ast;
    exports io.github.qishr.cascara.lang.xml.exception;
    exports io.github.qishr.cascara.lang.xml.processor;
    exports io.github.qishr.cascara.lang.xml.token;
}

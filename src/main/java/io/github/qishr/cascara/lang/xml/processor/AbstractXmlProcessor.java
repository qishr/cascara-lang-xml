package io.github.qishr.cascara.lang.xml.processor;

import io.github.qishr.cascara.common.diagnostic.NoOpReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.lang.LanguageOptions;
import io.github.qishr.cascara.common.lang.processor.Processor;
import io.github.qishr.cascara.common.util.ContentType;
import io.github.qishr.cascara.common.util.Properties;
import io.github.qishr.cascara.lang.xml.XmlOptions;

public abstract class AbstractXmlProcessor<P extends Processor> implements Processor {
    public static final ContentType XML_CONTENT_TYPE = new ContentType("Extensible markup language")
            .withSuffix(".xml")
            .withType("text/xml")
            .withType("application/xml");

    protected XmlOptions options = new XmlOptions();
    protected Reporter reporter = new NoOpReporter();
    private Properties capabilities;

    protected abstract P self();

    public Properties getCapabilities() {
        if (capabilities == null) {
            capabilities = new Properties();
            capabilities.set("contentType", "text/xml");
        }
        return capabilities;
    }

    @Override
    public ContentType getContentType() {
        return XML_CONTENT_TYPE;
    }

    /// {@inheritDoc}
    @Override
    public P setReporter(Reporter reporter) {
        this.reporter = reporter;
        return self();
    }

    /// {@inheritDoc}
    @Override
    public P setOptions(LanguageOptions<?> options) {
        this.options = (XmlOptions) options;
        return self();
    }
}

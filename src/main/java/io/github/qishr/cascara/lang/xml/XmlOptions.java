package io.github.qishr.cascara.lang.xml;

import io.github.qishr.cascara.common.lang.util.LanguageOptions;

public class XmlOptions extends LanguageOptions<XmlOptions> {
    private boolean strict = false;

    /// Sets whether to always output the '---' document start marker.
    public XmlOptions setStrict(boolean val) {
        this.strict = val;
        return this;
    }

    public boolean isStrict() { return strict; }
}
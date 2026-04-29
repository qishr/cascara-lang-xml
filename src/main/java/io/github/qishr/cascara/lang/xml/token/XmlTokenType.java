package io.github.qishr.cascara.lang.xml.token;

import io.github.qishr.cascara.common.lang.token.TokenCategory;
import io.github.qishr.cascara.common.lang.token.TokenType;

// public enum XmlTokenType implements TokenType {
//     // Delimiters
//     TAG_START(TokenCategory.PUNCTUATION),      // <
//     TAG_END(TokenCategory.PUNCTUATION),        // >
//     TAG_SLASH(TokenCategory.PUNCTUATION),      // /
//     EQUALS(TokenCategory.OPERATOR),            // =

//     // Components
//     TAG_NAME(TokenCategory.STRUCTURAL),        // "div", "svg"
//     ATTR_NAME(TokenCategory.FIELD_NAME),       // "fill", "cx"
//     ATTR_VALUE(TokenCategory.STRING),          // "none", "20"

//     // Special Blocks
//     TEXT(TokenCategory.TEXT),
//     CDATA_START(TokenCategory.PUNCTUATION),    // <![CDATA[
//     CDATA_CONTENT(TokenCategory.TEXT),
//     CDATA_END(TokenCategory.PUNCTUATION),      // ]]>

//     // Processing Instructions
//     PI_START(TokenCategory.META),              // <?
//     PI_TARGET(TokenCategory.KEYWORD),          // "xml-stylesheet"
//     PI_CONTENT(TokenCategory.STRING),          // the rest of the PI
//     PI_END(TokenCategory.META),                // ?>

//     COMMENT(TokenCategory.COMMENT),
//     WHITESPACE(TokenCategory.WHITESPACE),
//     ERROR(TokenCategory.ERROR);

//     private final TokenCategory category;

//     XmlTokenType(TokenCategory category) {
//         this.category = category;
//     }

//     @Override
//     public String getId() {
//         return name();
//     }

//     @Override
//     public TokenCategory getCategory() {
//         return category;
//     }

// }
public enum XmlTokenType implements TokenType {
    // Delimiters
    TAG_START(TokenCategory.PUNCTUATION),        // <
    TAG_END(TokenCategory.PUNCTUATION),          // >
    TAG_SLASH(TokenCategory.PUNCTUATION),        // /
    END_TAG_START(TokenCategory.PUNCTUATION),    // </
    EQUALS(TokenCategory.OPERATOR),              // =

    // Components
    TAG_NAME(TokenCategory.STRUCTURAL),          // "div", "svg"
    ATTR_NAME(TokenCategory.FIELD_NAME),         // "fill", "cx"
    ATTR_VALUE(TokenCategory.STRING),            // "none", "20"

    // Special Blocks
    TEXT(TokenCategory.TEXT),
    CDATA_START(TokenCategory.PUNCTUATION),      // <![CDATA[
    CDATA_CONTENT(TokenCategory.TEXT),
    CDATA_END(TokenCategory.PUNCTUATION),        // ]]>

    // Processing Instructions (PIs)
    PI_START(TokenCategory.META),                // <?
    PI_TARGET(TokenCategory.KEYWORD),
    PI_CONTENT(TokenCategory.STRING),
    PI_END(TokenCategory.META),                  // ?>

    COMMENT(TokenCategory.COMMENT),
    WHITESPACE(TokenCategory.WHITESPACE),
    ERROR(TokenCategory.ERROR);

    private final TokenCategory category;

    XmlTokenType(TokenCategory category) {
        this.category = category;
    }

    @Override public String getId() { return name(); }
    @Override public TokenCategory getCategory() { return category; }
}
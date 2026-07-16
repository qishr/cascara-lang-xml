// # License & Terms
//
// This file is part of **Cascara**.
//
// **Cascara** is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.
//
// ---
//
// ## Special Runtime Exception
//
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent modules,
// and to copy and distribute the resulting executable under terms of your
// choice, provided that you also meet, for each linked independent module,
// the terms and conditions of the license of that module.
//
// An independent module is a module which is not derived from or based on
// this library. If you modify this library, you may extend this exception
// to your version of the library, but you are not obligated to do so. If
// you do not wish to do so, delete this exception statement from your
// version.


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
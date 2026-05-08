package io.github.qishr.cascara.lang.xml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;
import io.github.qishr.cascara.common.lang.StructuredDocument;
import io.github.qishr.cascara.common.lang.ast.AstNode;
import io.github.qishr.cascara.common.lang.ast.CommentAstNode;
import io.github.qishr.cascara.lang.xml.ast.XmlNode;
import io.github.qishr.cascara.lang.xml.processor.XmlParser;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.SimpleReporter;

public class XmlDocument implements StructuredDocument {
    XmlNode root = null;

   public static XmlDocument readFile(Path path, Charset encoding) throws Exception {
      byte[] encoded = Files.readAllBytes(path);
      String yamlString = new String(encoded, encoding);
      return new XmlDocument(yamlString);
   }

   public static XmlDocument load(InputStream is) throws Exception {
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();

      String line;
      while((line = br.readLine()) != null) {
         sb.append(line);
         sb.append("\n");
      }

      return new XmlDocument(sb.toString());
   }

   public XmlDocument(String xmlString) throws Exception {
//       XmlTokenizerImpl tokenizer = new XmlTokenizerImpl();
//       List<XmlToken> tokens = tokenizer.tokenize(xmlString);
       Reporter reporter = new SimpleReporter();
       reporter.setLevel(Level.INFO);
       XmlParser parser = new XmlParser(); //(tokens);
       this.root = parser.parse(xmlString).getRoot();
   }

   public XmlDocument(XmlNode root) {
      this.root = root;
   }

   public XmlNode getRoot() {
      return this.root;
   }

   @Override
   public int getStartLine() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getStartLine'");
   }

   @Override
   public int getStartColumn() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getStartColumn'");
   }

   @Override
   public int getEndLine() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getEndLine'");
   }

   @Override
   public int getEndColumn() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getEndColumn'");
   }

   @Override
   public List<? extends AstNode> getChildren() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getChildren'");
   }

   @Override
   public List<CommentAstNode> getComments() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getComments'");
   }

   @Override
   public URI getOriginUri() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getOriginUri'");
   }

   @Override
   public URI getSchemaUri() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getSchemaUri'");
   }

}

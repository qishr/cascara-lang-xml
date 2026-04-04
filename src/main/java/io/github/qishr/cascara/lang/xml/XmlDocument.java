package io.github.qishr.cascara.lang.xml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;
import io.github.qishr.cascara.lang.xml.ast.XmlNode;
import io.github.qishr.cascara.lang.xml.processor.XmlParser;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.SimpleReporter;

public class XmlDocument {
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

}

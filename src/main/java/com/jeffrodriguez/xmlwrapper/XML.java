/* This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */
package com.jeffrodriguez.xmlwrapper;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A {@link Document} wrapping utility class.
 * @author Jeff
 */
public class XML implements Cloneable {

    static {
        try {

            // Create the document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DOCUMENT_BUILDER = dbf.newDocumentBuilder();

            // Create the transformers
            TransformerFactory tf = TransformerFactory.newInstance();
            TRANSFORMER = tf.newTransformer();

            Transformer tp = tf.newTransformer();
            tp.setOutputProperty(OutputKeys.INDENT, "yes");
            TRANSFORMER_PRETTY = tp;

            // Create XPath
            XPathFactory xpf = XPathFactory.newInstance();
            XPATH = xpf.newXPath();
        } catch (Throwable t) {
            throw new Error("Failed to initialize static variables.", t);
        }
    }

    /**
     * A reusable {@link DocumentBuilder}, instantiated at class load.
     */
    private static final DocumentBuilder DOCUMENT_BUILDER;

    /**
     * A reusable {@link Transformer}, instantiated at class load.
     *
     * Transforms XML in a pretty (indented) manner.
     */
    private static final Transformer TRANSFORMER_PRETTY;

    /**
     * A reusable {@link Transformer}, instantiated at class load.
     */
    private static final Transformer TRANSFORMER;

    /**
     * A reusable {@link XPath}, instantiated at class load.
     */
    private static final XPath XPATH;

    /**
     * The wrapped document.
     */
    private Document document;

    /**
     * Creates a new Document.
     * @param rootName the name or the root element.
     * @return a new {@link XML} instance with a single root element.
     */
    public static XML create(String rootName) {
        Document document = DOCUMENT_BUILDER.newDocument();
        Element root = document.createElement(rootName);
        document.appendChild(root);
        return new XML(document);
    }

    /**
     * Parses an XML string.
     * @param xml the XML string to parse.
     * @return a new {@link XML} instance wrapping the parsed document.
     * @throws SAXException if an exception occurs during XML parsing.
     * @throws IOException if an IO error occurs.
     */
    public static XML parse(String xml) throws SAXException, IOException {
        return new XML(DOCUMENT_BUILDER.parse(new InputSource(new StringReader(xml))));
    }

    /**
     * Creates a new XML wrapper.
     * @param document the document to wrap.
     */
    public XML(Document document) {
        this.document = document;
    }

    /**
     * @return the wrapped document.
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Evaluates an XPath expression.
     * @param expression the XPath expression.
     * @return an {@link Iterable} over the {@link XMLElement}s.
     * @throws XPathExpressionException If expression cannot be compiled.
     */
    public Iterable<XMLElement> xpathElements(String expression) throws XPathExpressionException {

        // Get a node list from the XPATH expression
        final NodeList nodes = (NodeList) XML.XPATH
                                             .compile(expression)
                                             .evaluate(document, XPathConstants.NODESET);

        // Return the iterable
        NodeListIterator<Element> nodeListIterator = new NodeListIterator(nodes);

        return new XMLElementIterator(nodeListIterator).toIterable();
    }

    /**
     * Formats the XML document as a string.
     * @param pretty true if the document should be indented.
     * @return the XML document as a string.
     * @throws TransformerException If an unrecoverable error occurs during the course of the transformation.
     */
    public String toString(boolean pretty) throws TransformerException {
        StringWriter writer = new StringWriter();
        if (pretty) {
            TRANSFORMER_PRETTY.transform(new DOMSource(document), new StreamResult(writer));
        } else {
            TRANSFORMER.transform(new DOMSource(document), new StreamResult(writer));
        }
        return writer.toString();
    }

    /**
     * @return the root document element.
     */
    public XMLElement getRoot() {
        return new XMLElement(document.getDocumentElement());
    }

    /**
     * Clones this XML instance, and the underlying {@link Document}.
     * @return a new XML instance wrapping the new {@link Document} clone.
     * @see Document#cloneNode(boolean)
     */
    @Override
    public XML clone() {
        return new XML((Document) document.cloneNode(true));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.document != null ? this.document.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final XML other = (XML) obj;
        if (this.document != other.document && (this.document == null || !this.document.equals(other.document))) {
            return false;
        }
        return true;
    }

}

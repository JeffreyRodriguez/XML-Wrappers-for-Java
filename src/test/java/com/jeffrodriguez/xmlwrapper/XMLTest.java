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

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 * @author <a href="mailto:jeff@jeffrodriguez.com">Jeff Rodriguez</a>
 */
public class XMLTest {

    /**
     * Test of constructor method, of class XML.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");

        // Create a document
        String rootName = "foo";
        Document document = XML.create(rootName).getDocument();

        XML result = new XML(document);

        assertNotNull(result.getDocument());
        assertEquals(rootName, result.getDocument().getDocumentElement().getTagName());
    }

    /**
     * Test of create method, of class XML.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        String rootName = "foo";

        XML result = XML.create(rootName);

        assertNotNull(result.getDocument());
        assertEquals(rootName, result.getDocument().getDocumentElement().getTagName());
    }

    /**
     * Test of parse method, of class XML.
     */
    @Test
    public void testParse() throws Exception {
        System.out.println("parse");

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\"?>");
        xml.append("<foo/>");

        XML result = XML.parse(xml.toString());

        assertNotNull(result.getDocument());
        assertEquals("foo", result.getDocument().getDocumentElement().getTagName());
    }

    /**
     * Test of getDocument method, of class XML.
     */
    @Test
    public void testGetDocument() {
        System.out.println("getDocument");

        Document result = XML.create("foo").getDocument();

        assertNotNull(result);
    }

    /**
     * Test of xpathElements method, of class XML.
     */
    @Test
    public void testXpathElements() throws Exception {
        System.out.println("xpathElements");

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\"?>");
        xml.append("<foo>");
        xml.append("  <bar baz=\"true\"/>");
        xml.append("  <bar baz=\"false\"/>");
        xml.append("</foo>");

        XML instance = XML.parse(xml.toString());

        Iterator<XMLElement> result = instance.xpathElements("//bar").iterator();

        assertTrue(result.hasNext());
        assertEquals("true", result.next().getAttribute("baz"));

        assertTrue(result.hasNext());
        assertEquals("false", result.next().getAttribute("baz"));
    }

    /**
     * Test of toString method, of class XML.
     */
    @Test
    public void testToStringTrue() throws Exception {
        System.out.println("toString");

        // Get the system line separator
        String lineSeparator = System.getProperty("line.separator");

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        xml.append(lineSeparator);
        xml.append("<foo>");
        xml.append(lineSeparator);
        xml.append("  <bar/>");
        xml.append(lineSeparator);
        xml.append("</foo>");
        xml.append(lineSeparator);

        String expected = xml.toString();

        XML instance = XML.parse(xml.toString());

        String actual = instance.toString(true);

        assertEquals(expected, actual);
    }

    /**
     * Test of toString method, of class XML.
     */
    @Test
    public void testToStringFalse() throws Exception {
        System.out.println("toString");

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        xml.append("<foo>");
        xml.append("<bar/>");
        xml.append("</foo>");

        String expected = xml.toString();

        XML instance = XML.parse(xml.toString());

        String actual = instance.toString(false);

        assertEquals(expected, actual);
    }

    /**
     * Test of getRoot method, of class XML.
     */
    @Test
    public void testGetRoot() {
        System.out.println("getRoot");

        String rootName = "foo";
        XML instance = XML.create(rootName);

        XMLElement result = instance.getRoot();

        assertNotNull(result);
        assertEquals(rootName, result.getName());
    }

    /**
     * Test of clone method, of class XML.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        String rootName = "foo";

        // Create the original
        XML original = XML.create(rootName);

        // Create the clone
        XML clone = original.clone();

        // Set them to different values
        original.getRoot().setValue("bar");
        clone.getRoot().setValue("baz");

        // Verify they didn't affect each other
        assertEquals("bar", original.getRoot().getValue());
        assertEquals("baz", clone.getRoot().getValue());
    }

}

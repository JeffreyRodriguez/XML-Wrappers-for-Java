/*
 * This is free and unencumbered software released into the public domain.
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
import org.junit.Before;

/**
 * @author <a href="mailto:jeff@jeffrodriguez.com">Jeff Rodriguez</a>
 */
public class XMLElementTest {
    
    private XML xml;
    
    @Before
    public void setUp() {
         xml = XML.create("foo");
         xml.getRoot()
            .addChild("bar")
            .setAttribute("baz", "qux");
         
         xml.getRoot()
            .addChild("baz")
            .setAttribute("qux", "true");
         
         xml.getRoot()
            .addChild("baz")
            .setAttribute("qux", "false");
    }

    /**
     * Test of getElement method, of class XMLElement.
     */
    @Test
    public void testGetElement() {
        System.out.println("getElement");
        
        XMLElement instance = xml.getRoot();
        
        assertNotNull(instance.getElement());
        assertEquals(xml.getDocument().getDocumentElement(), instance.getElement());
    }

    /**
     * Test of getParent method, of class XMLElement.
     */
    @Test
    public void testGetParent() {
        System.out.println("getParent");
        
        XMLElement instance = xml.getRoot().getChild("bar");

        assertNotNull(instance.getParent());
        assertEquals(xml.getRoot().getElement(), instance.getParent().getElement());
    }

    /**
     * Test of getName method, of class XMLElement.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        
        XMLElement instance = xml.getRoot();
        
        assertEquals("foo", instance.getName());
    }

    /**
     * Test of addChild method, of class XMLElement.
     */
    @Test
    public void testAddChild_String() {
        System.out.println("addChild");
        
        String name = "quux";
        XMLElement instance = xml.getRoot();
        
        instance.addChild(name);
        
        assertNotNull(instance.getChild(name));
    }

    /**
     * Test of getChild method, of class XMLElement.
     */
    @Test
    public void testGetChild() {
        System.out.println("getChild");
        
        String name = "bar";
        XMLElement instance = xml.getRoot();
        
        assertNotNull(instance.getChild(name));
        assertEquals(name, instance.getChild(name).getName());
    }

    /**
     * Test of getChildren method, of class XMLElement.
     */
    @Test
    public void testGetChildren() {
        System.out.println("getChildren");
        
        XMLElement instance = xml.getRoot();
        Iterator<XMLElement> result = instance.getChildren("baz").iterator();
        
        assertTrue(result.hasNext());
        assertEquals("true", result.next().getAttribute("qux"));
        
        assertTrue(result.hasNext());
        assertEquals("false", result.next().getAttribute("qux"));
        
        assertFalse(result.hasNext());
    }

    /**
     * Test of setAttribute method, of class XMLElement.
     */
    @Test
    public void testSetAttribute() {
        System.out.println("setAttribute");
        
        XMLElement instance = xml.getRoot().getChild("bar");
        
        assertEquals("qux", instance.getAttribute("baz"));
        instance.setAttribute("baz", "test");
        assertEquals("test", instance.getAttribute("baz"));
    }

    /**
     * Test of getAttribute method, of class XMLElement.
     */
    @Test
    public void testGetAttribute() {
        System.out.println("getAttribute");
        
        XMLElement instance = xml.getRoot().getChild("bar");
        
        assertEquals("qux", instance.getAttribute("baz"));
    }

    /**
     * Test of setValue method, of class XMLElement.
     */
    @Test
    public void testGetValueSetValue() {
        System.out.println("getValue");
        System.out.println("setValue");
        
        String value = "quux";
        XMLElement instance = xml.getRoot().getChild("bar");
        
        assertEquals("", instance.getValue());
        instance.setValue(value);
        assertEquals(value, instance.getValue());
    }

    /**
     * Test of getValueAsLong method, of class XMLElement.
     */
    @Test
    public void testGetValueAsLong() {
        System.out.println("getValueAsLong");
        
        XMLElement instance = xml.getRoot().getChild("bar");
        instance.setValue(Long.toString(Long.MAX_VALUE));
        
        assertEquals(Long.valueOf(Long.MAX_VALUE), instance.getValueAsLong());
    }

    /**
     * Test of getValueAsInteger method, of class XMLElement.
     */
    @Test
    public void testGetValueAsInteger() {
        System.out.println("getValueAsInteger");
        
        XMLElement instance = xml.getRoot().getChild("bar");
        instance.setValue(Integer.toString(Integer.MAX_VALUE));
        
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), instance.getValueAsInteger());
    }
}

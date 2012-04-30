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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:jeff@jeffrodriguez.com">Jeff Rodriguez</a>
 */
public class XMLElementIteratorTest {

    private XML xml;

    private XMLElementIterator instance;

    @Before
    public void setUp() {
         xml = XML.create("foo");

         xml.getRoot()
            .addChild("baz")
            .setAttribute("qux", "true");

         xml.getRoot()
            .addChild("baz")
            .setAttribute("qux", "false");

         instance = (XMLElementIterator) xml.getRoot().getChildren("baz").iterator();
    }

    /**
     * Test of hasNext method, of class XMLElementIterator.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");

        assertTrue(instance.hasNext());
        instance.next();
        assertTrue(instance.hasNext());
        instance.next();
        assertFalse(instance.hasNext());
    }

    /**
     * Test of next method, of class XMLElementIterator.
     */
    @Test
    public void testNext() {
        assertEquals(xml.getDocument().getElementsByTagName("baz").item(0), instance.next().getElement());
        assertEquals(xml.getDocument().getElementsByTagName("baz").item(1), instance.next().getElement());
    }

    /**
     * Test of remove method, of class XMLElementIterator.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");

        assertTrue(instance.hasNext());
        instance.remove();
        assertTrue(instance.hasNext());
        instance.next();
        assertFalse(instance.hasNext());
    }

    /**
     * Test of toIterable method, of class XMLElementIterator.
     */
    @Test
    public void testToIterable() {
        System.out.println("toIterable");

        Iterable<XMLElement> iterable = instance.toIterable();
        assertNotNull(iterable);
        assertEquals(instance, iterable.iterator());
    }
}

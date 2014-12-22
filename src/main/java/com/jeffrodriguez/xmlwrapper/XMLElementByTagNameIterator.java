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

import javax.xml.soap.Node;

import org.w3c.dom.Element;

/**
 * An {@link Iterator} that wraps a {@link NodeListIterator} and returns only {@link XMLElement}s with a specific tag name.
 * Note that any call to {@link #hasNext()} will actually retrieve the next XML node(s) in the source iterator
 * until it finds an Element with the requested tag name, or until the end of the XML.
 * 
 * @author <a href="mailto:git@sorin.postelnicu.net">Sorin Postelnicu</a>
 */
public class XMLElementByTagNameIterator implements Iterator<XMLElement> {

    /**
     * The tag name to search in the source iterator.
     */
    String tagName;
    
    /**
     * The source iterator.
     */
    private final NodeListIterator<Element> iterator;

    /**
     * The next element that will be returned by this iterator, if exists.
     */
    private XMLElement nextElement = null;

    /**
     * Creates a new {@link XMLElementByTagNameIterator}.
     * @param tagName the tag name to search for in the source iterator
     * @param iterator the {@link NodeListIterator} to wrap.
     */
    public XMLElementByTagNameIterator(String tagName, NodeListIterator<Element> iterator) {
        if (tagName == null || tagName.length() == 0) {
            throw new IllegalArgumentException("The tagName cannot be empty!");
        }
        if (iterator == null) {
            throw new IllegalArgumentException("The iterator cannot be empty!");
        }
        this.tagName = tagName;
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        while (iterator.hasNext()) {
            Element next = iterator.next();
            if (next.getNodeType() == Node.ELEMENT_NODE
                    && tagName.equals(next.getNodeName())) {
                nextElement = new XMLElement(next);
                return true;
            }
        }
        return false;
    }

    @Override
    public XMLElement next() {
        return nextElement;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return an {@link Iterable} for this iterator.
     */
    public Iterable<XMLElement> toIterable() {
        return new Iterable<XMLElement>() {
            @Override
            public Iterator<XMLElement> iterator() {
                return XMLElementByTagNameIterator.this;
            }
        };
    }
}

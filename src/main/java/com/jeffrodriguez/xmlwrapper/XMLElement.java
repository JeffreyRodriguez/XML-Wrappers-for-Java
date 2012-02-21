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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * An {@link Element} wrapping utility class.
 * @author <a href="mailto:jeff@jeffrodriguez.com">Jeff Rodriguez</a>
 */
public class XMLElement {
    private final Element element;

    /**
     * Wraps an {@link Element}
     * @param element the element to wrap.
     */
    public XMLElement(Element element) {
        this.element = element;
    }
    
    /**
     * @return the wrapped element.
     */
    public Element getElement() {
        return element;
    }
    
    /**
     * @return the parent of this element.
     */
    public XMLElement getParent() {
        return new XMLElement((Element) element.getParentNode());
    }
    
    /**
     * Creates a new child element and appends it to this one.
     * @param name the name of the element.
     * @return the new element.
     */
    public XMLElement addChild(String name) {
        Element child = element.getOwnerDocument().createElement(name);
        element.appendChild(child);
        return new XMLElement(child);
    }
    
    /**
     * Adds a new child element to this one.
     * @param child the child element.
     */
    public void addChild(XMLElement child) {
        element.appendChild(child.getElement());
    }

    /**
     * Gets a child by tag name.
     * @param name the name of the tag.
     * @return the child element.
     * @throws IllegalStateException if more than one, or zero elements with the name are found.
     */
    public XMLElement getChild(String name) {
        NodeList nodes = element.getElementsByTagName(name);
        
        // Make sure there's only one
        if (nodes.getLength() > 1) {
            throw new IllegalStateException("More than one element with the name: " + name);
        } else if (nodes.getLength() < 1) {
            throw new IllegalStateException("No elements with the name: " + name);
        }
        
        // Get the element
        return new XMLElement((Element) nodes.item(0));
    }

    /**
     * Gets an {@link Iterable} for the children of this element by tag name.
     * @param name the tag name of the children.
     * @return an {@lterable} of the children.
     */
    public Iterable<XMLElement> getChildren(String name) {
        // Get the child element node list
        final NodeList nodes = element.getElementsByTagName(name);
        // Build the iterators
        final NodeListIterator<Element> nodeListIterator = new NodeListIterator(nodes);
        return new XMLElementIterator(nodeListIterator).toIterable();
    }
    
    /**
     * Sets an attribute on the element.
     * @param name the name of the attribute.
     * @param value the value of the attribute.
     * @return this element
     */
    public XMLElement setAttribute(String name, String value) {
        element.setAttribute(name, value);
        return this;
    }
    
    /**
     * Gets the value of an attribute.
     * @param name the name of the attribute.
     * @return the value of the attribute.
     */
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    /**
     * Gets the element's text content.
     * @return the element's text content.
     */
    public String getValue() {
        return element.getTextContent();
    }

    /**
     * Gets the element's text content, parsed as a Long.
     * @return the element's text content, parsed as a {@link Long} (possibly null).
     */
    public Long getValueAsLong() {
        String value = getValue();
        
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        return Long.parseLong(value);
    }

    /**
     * Gets the element's text content, parsed as an Integer.
     * @return the element's text content, parsed as an {@link Integer} (possibly null).
     */
    public Integer getValueAsInteger() {
        String value = getValue();
        
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        return Integer.parseInt(value);
    }
    
}

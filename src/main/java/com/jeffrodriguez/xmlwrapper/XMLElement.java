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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An {@link Element} wrapping utility class.
 * @author <a href="mailto:jeff@jeffrodriguez.com">Jeff Rodriguez</a>
 */
public class XMLElement {

    /**
     * The wrapped {@link Element}.
     */
    private final Element element;

    /**
     * Wraps an {@link Element}.
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
     * Gets the tag name of the element.
     * @return the tag name of the element.
     */
    public String getName() {
        return element.getTagName();
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
     * Gets a child by tag name.
     * @param name the name of the tag.
     * @return the child element, or null if the child doesn't exist.
     * @throws IllegalStateException if more than one element with the name are found.
     */
    public XMLElement getChild(String name) {
        NodeList nodes = element.getElementsByTagName(name);

        // Make sure there's only one
        if (nodes.getLength() > 1) {
            throw new IllegalStateException("More than one element with the name: " + name);
        } else if (nodes.getLength() < 1) {
            return null;
        }

        // Get the element
        return new XMLElement((Element) nodes.item(0));
    }

    /**
     * Gets the value of a child element's text content.
     * @return the child element's text content or null if the child does not exist.
     */
    public String getChildValue(String name) {

        XMLElement child = getChild(name);
        if (child != null) {
            return child.getValue();
        }

        return null;
    }

    /**
     * Sets the value of a child element's text content.
     *
     * If the child doesn't exist, a new element will be created.
     * @return the child element.
     */
    public XMLElement setChildValue(String name, String value) {

        // Get the child
        XMLElement child = getChild(name);

        // Create the child if it doesn't exist yet
        if (child == null) {
            child = addChild(name);
        }

        // Set it's value and return the child
        child.setValue(value);
        return child;
    }

    /**
     * Gets an {@link Iterable} for the children of this element by tag name.
     * @param name the tag name of the children.
     * @return an {@link Iterable} of the children.
     */
    public Iterable<XMLElement> getChildren(String name) {

        // Get the child element node list
        final NodeList nodes = element.getElementsByTagName(name);

        // Build the iterators
        final NodeListIterator<Element> nodeListIterator = new NodeListIterator(nodes);
        return new XMLElementIterator(nodeListIterator).toIterable();
    }

    /**
     * Returns true if an element has children.
     * @return true if the element has children.
     */
    public boolean hasChildren() {
        NodeListIterator iterator = new NodeListIterator(element.getChildNodes());

        while (iterator.hasNext()) {
            return iterator.next() instanceof Element;
        }

        return false;
    }
    /**
     * Returns true if a child element of the specified name exists.
     * @return true if the specified child element exists.
     */
    public boolean hasChild(String name) {
        NodeListIterator iterator = new NodeListIterator(element.getChildNodes());

        while (iterator.hasNext()) {
            Node node = iterator.next();

            return node instanceof Element && ((Element) node).getTagName().equals(name);
        }

        return false;
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
     * Sets the text content of the element.
     * @param value the value to set.
     */
    public void setValue(String value) {
        element.setTextContent(value);
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.element != null ? this.element.hashCode() : 0);
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
        final XMLElement other = (XMLElement) obj;
        if (this.element != other.element && (this.element == null || !this.element.equals(other.element))) {
            return false;
        }
        return true;
    }

}

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A {@link Iterator} that wraps a {@link NodeList}.
 * @param <T> The type of the node.
 * @author <a href="mailto:jeff@jeffrodriguez.com">Jeff Rodriguez</a>
 */
public class NodeListIterator<T extends Node> implements Iterator<T> {
    
    /**
     * The wrapped NodeList
     */
    private final NodeList nodes;
    
    /**
     * The position in the list.
     */
    private int position = 0;

    /**
     * Creates a new {@link NodeListIterator}.
     * @param nodes the {@link NodeList} to iterate over.
     */
    public NodeListIterator(NodeList nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean hasNext() {
        return position < nodes.getLength();
    }

    @Override
    public T next() {
        return (T) nodes.item(position++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * @return an {@link Iterable} for this iterator.
     */
    public Iterable<T> toIterable() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return NodeListIterator.this;
            }
        };
    }
    
}

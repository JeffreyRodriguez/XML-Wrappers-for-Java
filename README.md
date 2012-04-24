XML Wrappers for Java
=====================

A lightweight set of wrappers around the Java DOM XML classes.

Send complaints, suggestions, and thanks to jeff@jeffrodriguez.com

Maven
-----
This project uses Sonatype's OSS Nexus hosting to sync to Maven central.

    <dependencies>
        ...
        <dependency>
            <groupId>com.jeffrodriguez</groupId>
            <artifactId>xmlwrapper</artifactId>
            <version>1.3.0</version>
        </dependency>
        ...
    </dependencies>

Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, XML Wrapper will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

Creating an XML Instance
------------------------

### Wrap an existing document
    XML xml = new XML(document);

### Parse a string
    XML xml = XML.parse("<?xml version=\"1.0\"?><foo/>");

### Create from scratch
    XML xml = XML.create("foo");

### Clone an instance
    XML clone = xml.clone();


Outputting XML
------------------------

### Pretty formatting
    xml.toString(true);

### Compact formatting
    xml.toString(false);


Working with Elements
---------------------

### Getting the document element
    XMLElement root = xml.getRoot();

### Adding children to an element
    root.addChild("bar"); // foo -> bar
    root.addChild("baz"); // foo -> bar
                          //     -> baz

### Chaining
    root.addChild("bar").addChild("baz"); // foo -> bar -> baz

### Navigating
    root.getChild("bar").getParent(); // foo

### Iterating
    // Enhanced for-loop
    for (XMLElement child : root.getChildren("bar")) {
        // Do something with child
    }

    // Traditional iterator
    Iterator<XMLElement> it = root.getChildren("bar").iterator();
    while (it.hasNext()) {
        XMLElement child = it.next();
        // Do something with child
    }

### Element Text

    // String
    root.setValue("bar");
    root.getValue();      // "bar"

    // Integer
    root.setValue("1");
    root.getValueAsInteger(); // 1

    // Long
    root.setValue("1");
    root.getValueAsLong(); // 1L


Attributes
----------
    XMLElement element = xml.getRoot();
    element.setAttribute("bar", "baz");
    element.getAttribute("bar");        // "baz"

XPath Support
-------------
The `xpathElements("...")` method allows you to use XPath expressions on your
document:

    // Enhanced for-loop
    for (XMLElement child : xml.xpathElements("//bar")) {
        // Do something with child
    }

    // Traditional iterator
    Iterator<XMLElement> it = xml.xpathElements("//bar").iterator();
    while (it.hasNext()) {
        XMLElement child = it.next();
        // Do something with child
    }
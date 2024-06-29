package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeFormEntryTest {
    private AttributeFormEntry entry;
    private Attribute attribute;
    private WidgetAttribute widgetAttribute;

    @BeforeEach
    void setUp() {
        attribute = new Attribute(); // Assuming Attribute has a no-arg constructor
        widgetAttribute = new WidgetAttribute(); // Assuming WidgetAttribute has a no-arg constructor
        entry = new AttributeFormEntry(attribute, widgetAttribute);
    }

    @Test
    void testConstructorInitialization() {
        assertNotNull(entry.getAttribute(), "Attribute should not be null after being set in constructor");
        assertNotNull(entry.getWidgetAttribute(), "WidgetAttribute should not be null after being set in constructor");
    }

    @Test
    void testSettersAndGetters() {
        Attribute newAttribute = new Attribute(); // Assuming a new instance for the test
        WidgetAttribute newWidgetAttribute = new WidgetAttribute(); // Assuming a new instance for the test

        entry.setAttribute(newAttribute);
        entry.setWidgetAttribute(newWidgetAttribute);

        assertEquals(newAttribute, entry.getAttribute(), "getAttribute should return what was set by setAttribute");
        assertEquals(newWidgetAttribute, entry.getWidgetAttribute(), "getWidgetAttribute should return what was set by setWidgetAttribute");
    }

    @Test
    void testToString() {
        String expectedString = String.format("AttributeFormEntry(attribute=%s, widgetAttribute=%s)", attribute, widgetAttribute);
        assertEquals(expectedString, entry.toString(), "toString should return a formatted string with attribute and widgetAttribute values");
    }
}

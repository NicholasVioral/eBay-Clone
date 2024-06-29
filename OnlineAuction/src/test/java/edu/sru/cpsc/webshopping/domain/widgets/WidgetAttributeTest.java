package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WidgetAttributeTest {

    private WidgetAttribute widgetAttribute;
    private Attribute attribute;
    private Widget widget;

    @BeforeEach
    public void setUp() {
        attribute = new Attribute();
        attribute.setAttributeKey("color");
        widget = new Widget();
        widget.setName("Widget 1");
        widgetAttribute = new WidgetAttribute(widget, attribute);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(0, widgetAttribute.getId());
        assertEquals("color", widgetAttribute.getAttributeKey());
        assertEquals(attribute, widgetAttribute.getAttribute());
        assertNull(widgetAttribute.getValue());
        assertEquals(widget, widgetAttribute.getWidget());

        widgetAttribute.setId(1);
        assertEquals(1, widgetAttribute.getId());

        widgetAttribute.setAttributeKey("size");
        assertEquals("size", widgetAttribute.getAttributeKey());

        Attribute newAttribute = new Attribute();
        newAttribute.setAttributeKey("weight");
        widgetAttribute.setAttribute(newAttribute);
        assertEquals(newAttribute, widgetAttribute.getAttribute());
        assertEquals("weight", widgetAttribute.getAttributeKey());

        widgetAttribute.setValue("large");
        assertEquals("large", widgetAttribute.getValue());

        Widget newWidget = new Widget();
        newWidget.setName("Widget 2");
        widgetAttribute.setWidget(newWidget);
        assertEquals(newWidget, widgetAttribute.getWidget());
    }

    @Test
    public void testConstructors() {
        WidgetAttribute widgetAttribute1 = new WidgetAttribute();
        assertNotNull(widgetAttribute1);

        WidgetAttribute widgetAttribute2 = new WidgetAttribute(attribute);
        assertEquals("color", widgetAttribute2.getAttributeKey());
        assertEquals(attribute, widgetAttribute2.getAttribute());

        WidgetAttribute widgetAttribute3 = new WidgetAttribute(widget, attribute);
        assertEquals("color", widgetAttribute3.getAttributeKey());
        assertEquals(attribute, widgetAttribute3.getAttribute());
        assertEquals(widget, widgetAttribute3.getWidget());
    }
}
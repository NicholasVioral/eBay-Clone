package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WidgetFormTest {
    private WidgetForm form;

    @BeforeEach
    void setUp() {
        form = new WidgetForm();
    }

    @Test
    void testSetAndGetName() {
        String expectedName = "WidgetName";
        form.setName(expectedName);
        assertEquals(expectedName, form.getName(), "The name retrieved should be the same as the name set.");
    }

    @Test
    void testSetAndGetDescription() {
        String expectedDescription = "WidgetDescription";
        form.setDescription(expectedDescription);
        assertEquals(expectedDescription, form.getDescription(), "The description retrieved should be the same as the description set.");
    }

    @Test
    void testSetAndGetEntries() {
        List<AttributeFormEntry> expectedEntries = new ArrayList<>();
        AttributeFormEntry entry = new AttributeFormEntry(); // Assume constructor or setters to initialize
        expectedEntries.add(entry);

        form.setEntries(expectedEntries);
        assertEquals(expectedEntries, form.getEntries(), "The entries retrieved should be the same as the entries set.");
        assertTrue(form.getEntries().contains(entry), "The list should contain the entry that was added.");
    }
}

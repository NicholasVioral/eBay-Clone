package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AttributeSuggestionTest {
    private AttributeSuggestion attributeSuggestion;

    @Mock
    private Attribute associatedAttribute; // Mocked because it's a database entity

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attributeSuggestion = new AttributeSuggestion();
    }

    @Test
    void testGetAndSetId() {
        long expectedId = 42L;
        attributeSuggestion.setId(expectedId);
        assertEquals(expectedId, attributeSuggestion.getId(), "ID should match the set value.");
    }

    @Test
    void testGetAndSetAssociatedAttribute() {
        attributeSuggestion.setAssociatedAttribute(associatedAttribute);
        assertEquals(associatedAttribute, attributeSuggestion.getAssociatedAttribute(), "AssociatedAttribute should match the set mock attribute.");
    }

    @Test
    void testGetAndSetValue() {
        String expectedValue = "New Value";
        attributeSuggestion.setValue(expectedValue);
        assertEquals(expectedValue, attributeSuggestion.getValue(), "Value should match the set value.");
    }
}

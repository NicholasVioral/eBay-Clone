package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.util.enums.AttributeDataType;

@SpringBootTest(classes = {CategoryTest.class})
public class CategoryTest {

    private Category category;
    private List<AttributeRecommendation> attributes;

    @BeforeEach
    public void setUp() {
        category = new Category("Test Category");
        attributes = new ArrayList<>();
        Attribute attribute1 = new Attribute("Test Attribute 1", AttributeDataType.STRING);
        Attribute attribute2 = new Attribute("Test Attribute 2", AttributeDataType.INTEGER);
        AttributeRecommendation attributeRec1 = new AttributeRecommendation();
        attributeRec1.setAttribute(attribute1);
        AttributeRecommendation attributeRec2 = new AttributeRecommendation();
        attributeRec2.setAttribute(attribute2);
        attributes.add(attributeRec1);
        attributes.add(attributeRec2);
    }

    @Test
    public void testGettersAndSetters() {
        category.setId(1L);
        category.setName("Updated Test Category");
        category.setParent(new Category("Parent Category"));
        category.setAttributes(attributes);
        category.setVersion(2);

        assertEquals(1L, category.getId());
        assertEquals("Updated Test Category", category.getName());
        assertNotNull(category.getParent());
        assertEquals("Parent Category", category.getParent().getName());
        assertEquals(attributes, category.getAttributes());
        assertEquals(2, category.getVersion());
    }

    @Test
    public void testConstructor() {
        Category newCategory = new Category("New Test Category");
        assertEquals("New Test Category", newCategory.getName());
        assertEquals(1, newCategory.getVersion());
    }

    @Test
    public void testEmptyConstructor() {
        Category newCategory = new Category();
        assertNull(newCategory.getName());
        assertEquals(0, newCategory.getVersion());
    }
}
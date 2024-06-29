package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.util.enums.AttributeDataType;

@SpringBootTest(classes = {AttributeTest.class})
public class AttributeTest {

    @Test
    public void testGetId() {
        Attribute attribute = new Attribute();
        attribute.setId(1L);
        assertEquals(1L, attribute.getId());
    }

    @Test
    public void testGetAttributeKey() {
        Attribute attribute = new Attribute("color", AttributeDataType.STRING);
        assertEquals("color", attribute.getAttributeKey());
    }

    @Test
    public void testGetDataType() {
        Attribute attribute = new Attribute("count", AttributeDataType.INTEGER);
        assertEquals(AttributeDataType.INTEGER, attribute.getDataType());
    }

    @Test
    public void testGetCategories() {
        Attribute attribute = new Attribute();
        AttributeRecommendation recommendation1 = new AttributeRecommendation();
        AttributeRecommendation recommendation2 = new AttributeRecommendation();
        List<AttributeRecommendation> recommendations = new ArrayList<>();
        recommendations.add(recommendation1);
        recommendations.add(recommendation2);
        attribute.setCategories(recommendations);
        assertEquals(recommendations, attribute.getCategories());
    }
}
package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {AttributeRecommendationTest.class})
public class AttributeRecommendationTest {

    @Test
    public void testGettersAndSetters() {
        AttributeRecommendation ar = new AttributeRecommendation();
        assertNull(ar.getAttribute());
        assertNull(ar.getCategory());
        assertNull(ar.getRecommendation());
        ar.setAttribute(new Attribute());
        assertNotNull(ar.getAttribute());
        ar.setCategory(new Category());
        assertNotNull(ar.getCategory());
        ar.setRecommendation(1);
        assertEquals(1, ar.getRecommendation());
    }

}
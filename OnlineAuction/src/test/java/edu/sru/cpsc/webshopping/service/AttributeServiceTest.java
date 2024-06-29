package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.AttributeRecommendation;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeRecommendationRepository;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeRepository;
import edu.sru.cpsc.webshopping.util.enums.AttributeDataType;

public class AttributeServiceTest {
	
	@Mock
	private AttributeRepository attributeRepository;
	
	@InjectMocks
	private AttributeService attributeService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    void testAddOrGetAttribute() {
        String attributeKey = "1951";
        AttributeDataType dataType = AttributeDataType.YEAR;
        
        when(attributeRepository.findFirstByAttributeKey(attributeKey.toLowerCase().trim())).thenReturn(Optional.of(new Attribute(attributeKey.toLowerCase().trim(), dataType)));
        Attribute result = attributeService.addOrGetAttribute(attributeKey, dataType);
        assertEquals(attributeKey, result.getAttributeKey());
        assertEquals(dataType, result.getDataType());
    }

    @Test
    void testAssociateAttributeWithCategory() {
        String attributeKey = "1951";
        String dataType = "YEAR";
        
        Category category = new Category();
        category.setName("Vehicle Parts");
        AttributeDataType dataTypeEnum = AttributeDataType.valueOf(dataType);

        when(attributeRepository.findFirstByAttributeKey(attributeKey.toLowerCase())).thenReturn(Optional.of(new Attribute(attributeKey.toLowerCase(), dataTypeEnum)));

        Attribute result = attributeService.associateAttributeWithCategory(attributeKey, dataType, category);
        assertEquals(attributeKey, result.getAttributeKey());
        assertEquals(AttributeDataType.valueOf(dataType), result.getDataType());
    }
}

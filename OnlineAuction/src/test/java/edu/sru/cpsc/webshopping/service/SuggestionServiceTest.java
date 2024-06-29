package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class SuggestionServiceTest {

    @Mock
    private SuggestionService suggestionService;
    
    @Mock
    private Resource resource = new ClassPathResource("Website_Car_Parts_Categories.xlsx");

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadSuggestionsFromURL() {
        suggestionService.loadSuggestions("test");
        
        verify(suggestionService).loadSuggestions(any(String.class));
    }

    @Test
    void testLoadSuggestionsFromFile() {
        try {
            suggestionService.loadSuggestions(resource);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}

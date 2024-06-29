package edu.sru.cpsc.webshopping.controller.misc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest(classes = {CustomErrorControllerSpringTest.class})
@AutoConfigureMockMvc
public class CustomErrorControllerSpringTest {
	
	@Mock
    private HttpServletRequest request;

    @Mock
    private Model model;

    @InjectMocks
    private CustomErrorController errorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	/*
	 * ensures that the website can handle an error
	 */
	@Test
	@WithMockUser(username = "user", roles = {"USER"})
    public void handleErrorTest() throws Exception {
		when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(400);
        when(request.getAttribute(RequestDispatcher.ERROR_EXCEPTION)).thenReturn(null);

        errorController.handleError(request, model);

        verify(model).addAttribute("errorMessage", "No exception was thrown.");
        verify(model).addAttribute("exceptionFound", false);
        verify(model).addAttribute("statusCode", "400");
    }
	
}

	
	
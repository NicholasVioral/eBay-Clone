package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetAttribute;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetAttributeRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetImageRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;

public class WidgetServiceTest {

	@Mock
	private WidgetRepository widgetRepository;
	
	@Mock
	private WidgetAttributeRepository widgetAttributeRepository;
	
	@Mock
	private WidgetImageRepository widgetImageRepository;
	
	@Mock
	private MarketListingRepository marketListingRepository;
	
	@InjectMocks
	private WidgetService widgetService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testAddWidget() {
		Widget widget = new Widget();
		assertDoesNotThrow(() -> widgetService.addWidget(widget));
	}
	
	@Test
	void testAddWidgetAttributes() {
		WidgetAttribute[] widgetAttributeAr = {new WidgetAttribute(), new WidgetAttribute(), new WidgetAttribute()};
		Set<WidgetAttribute> widgetAttributes = Set.of(widgetAttributeAr);
		assertDoesNotThrow(() -> widgetService.addWidgetAttributes(widgetAttributes));
	}
}

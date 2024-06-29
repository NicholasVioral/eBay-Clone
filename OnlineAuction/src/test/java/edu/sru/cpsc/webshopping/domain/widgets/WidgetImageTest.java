package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;

public class WidgetImageTest {
	@Test
	
	/*
	 * Tests the WidgetImage domain
	 */
	public void testWidgetImage()
	{
		MarketListing ML = new MarketListing();
		ML.setId(12);
		WidgetImage WI = new WidgetImage();
		WI.setImageName("test.png");
		WI.setId(11L);
		WI.setMarketListing(ML);
		
		assertEquals("test.png", WI.getImageName());
		//fix this assertEquals:
		Long expected_id = 11L;
		assertEquals(expected_id, WI.getId());
		assertEquals(ML, WI.getMarketListing());
	}
	
}

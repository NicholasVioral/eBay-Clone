package edu.sru.cpsc.webshopping.controller;


import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import edu.sru.cpsc.webshopping.domain.widgets.ImagePacket;

public class ImagePacketControllerTest {
	private Logger log= Logger.getLogger(getClass());
	
	@BeforeAll
	static void initAll() {
		
	}
	@BeforeEach
	void init() {
		
	}
	@Test
	@DisplayName("get Image Packet By Id")
	public void getImagePacketById() {
		try {
			log.info("Starting Execution");
			ImagePacket expectedValue= null;

			ImagePacketController imagepacketcontroller= new ImagePacketController(null);
			ImagePacket actualValue= imagepacketcontroller.getImagePacketById(null);
			log.info("actualValue");
			System.out.println("actualValue");
		}
		catch(Exception exception) {
			log.error("exception");
			exception.printStackTrace();
			Assertions.assertFalse(false);
		}
	}
	@AfterEach
	void tearDown() {
		
	}
	@AfterAll
	static void tearDownAll() {
		
	}
}

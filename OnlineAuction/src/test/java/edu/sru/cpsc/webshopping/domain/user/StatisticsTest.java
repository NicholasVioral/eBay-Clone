package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {StatisticsTest.class})
public class StatisticsTest {

	@Test
	void testConstructor() {
		Statistics.StatsCategory category = Statistics.StatsCategory.AUCTION;
		float value = 100.0f;
		Statistics statistics = new Statistics(category, value);
		assertNotNull(statistics.getId());
		assertEquals(category, statistics.category);
		assertEquals(value, statistics.getValue());
		assertNotNull(statistics.getDate());
		assertEquals(LocalDateTime.now().getHour(), statistics.getHour());
	}

	@Test
	void testSettersAndGetters() {
		Statistics statistics = new Statistics();
		statistics.setId(1L);
		assertEquals(1L, statistics.getId());
		statistics.setValue(100.0f);
		assertEquals(100.0f, statistics.getValue());
		LocalDateTime date = LocalDateTime.now();
		statistics.setDate(date);
		assertEquals(date, statistics.getDate());
		statistics.setHour();
		assertEquals(date.getHour(), statistics.getHour());
		statistics.setDescription("Test Description");
		assertEquals("Test Description", statistics.getDescription());
	}

}
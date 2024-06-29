package edu.sru.cpsc.webshopping;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {SellingWidgetsTests.class})
@AutoConfigureMockMvc

public class SellingWidgetsTests
{

	//Tests if the application loads
	@Test
	void loadTest() {
		assertThat(SpringApplication.run(SellingWidgets.class)).isNotNull();
	}
}
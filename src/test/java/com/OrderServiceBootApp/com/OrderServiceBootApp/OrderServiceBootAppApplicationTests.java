package com.OrderServiceBootApp.com.OrderServiceBootApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OrderServiceBootAppApplicationTests {

	@Test
	void contextLoads() {
        OrderServiceBootAppApplication.main(new String[]{});
        assertThat(true).isTrue();
	}

}

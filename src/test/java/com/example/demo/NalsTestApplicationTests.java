package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class NalsTestApplicationTests {

	
	@Test
	public void testCreate() {
		 assertEquals( 1,1, "true");
	}
}

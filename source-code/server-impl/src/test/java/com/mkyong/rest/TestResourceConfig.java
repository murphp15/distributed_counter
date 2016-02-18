package com.mkyong.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.mock.web.MockServletContext;

import com.paul.rest.counter.AtomicIntegerCounter;

public class TestResourceConfig extends ResourceConfig {

	public TestResourceConfig() {
		register(AtomicIntegerCounter.class);
		register(MockServletContext.class);
	}
	
	
}

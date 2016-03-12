package com.mkyong.rest;

import static com.paul.demo.common.SharedLabels.CLIENT_FACING_COUNTER;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("file:src/test/resources/test-context.xml")
public class ClientFacingCounterResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig()
				.register(ClientFacingCounterResource.class)
				.property("contextConfigLocation", "classpath:/test-context.xml");
	}
	@Test
	public void testGet() {
		final String currentCount = target(CLIENT_FACING_COUNTER).request().get(String.class);
		assertEquals("0", currentCount);
	}

	@Test
	public void testPost() {
		final Response hello = target(CLIENT_FACING_COUNTER).request().post(
				Entity.entity("1", MediaType.TEXT_PLAIN));
		assertEquals(200, hello.getStatus());
	}

	@Test
	public void testAddTwice(){
		add(5);
		add(4);
		add(2);
		assertEquals(11, get());
	}

	private int get(){
		return Integer.parseInt(target(CLIENT_FACING_COUNTER).request().get(String.class));
	}


	private void add(int value) {
		target(CLIENT_FACING_COUNTER).request().post(
				Entity.entity(Integer.toString(value), MediaType.TEXT_PLAIN));
	}

}

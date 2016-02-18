package com.mkyong.rest;

import static com.paul.demo.common.SharedLabels.DISTRIBUTED_COUNTER;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.paul.demo.common.CurrentCountForServer;
import com.paul.demo.common.TestLabels;
import com.paul.demo.common.exception.HTTPStatusExceptionHandler;
import com.paul.demo.server.ServerEntity;

public class DistributedCounterResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig()
				.register(DistributedCounterResource.class)
				.property("contextConfigLocation", "classpath:/test-context.xml");
	}
	
	@Test
	public void testPut() {
		HTTPStatusExceptionHandler.executePut(target(DISTRIBUTED_COUNTER).request(),
				new CurrentCountForServer(
						new ServerEntity(TestLabels.DUMMY_URL), 30), true);
	}

	@Test
	public void testPost() {
		target(DISTRIBUTED_COUNTER).request().
				post(Entity.entity(new ServerEntity(TestLabels.DUMMY_URL), 
						MediaType.APPLICATION_JSON), String.class);
	}

	
	@Test
	public void testSetAndGet() {
		HTTPStatusExceptionHandler.executePut(target(DISTRIBUTED_COUNTER).request(),
				new CurrentCountForServer(
						new ServerEntity(TestLabels.DUMMY_URL), 30), true);
		final String value = target(DISTRIBUTED_COUNTER).request().
				post(Entity.entity(new ServerEntity(TestLabels.DUMMY_URL), 
						MediaType.APPLICATION_JSON), String.class);
		assertEquals(30, Integer.parseInt(value));
	}

}

package com.paul.demo.gossip;

import static com.paul.demo.common.SharedLabels.CLIENT_FACING_COUNTER;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.mkyong.rest.DistributedCounterResource;

public class GossipServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(DistributedCounterResource.class);
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



}

package com.paul.demo;

import static com.paul.demo.common.SharedLabels.LOAD_BALANCER_ENTRY_POINT;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.paul.demo.common.exception.HTTPStatusExceptionHandler;
import com.paul.demo.server.ServerEntity;

public class LoadBalancerResourceTest extends JerseyTest {
	private String[] serverUrls = new String[]{"testUrl1","testUrl2",
			"testUrl3", "testUrl4" ,"testUrl5" };
	/**
	 * The first 3 tests are single threaded testing expected behaviour. 
	 * The last test is a multithreaded test designed to catch deadlocks etc... 
	 */
	@Override
	protected Application configure() {
		return new ResourceConfig(LoadBalancerResource.class)
				.property("contextConfigLocation", "classpath:/test-context.xml");
	}
	
	@Test
	public void testServersCanBeAdded() {
		addServers();
	}

	@Test
	public void testServersCanAreReadInDeterministicOrder() {
		addServers();
		for (String url : serverUrls) {
			assertEquals(url,target(LOAD_BALANCER_ENTRY_POINT)
					.request().accept(MediaType.APPLICATION_JSON).get(ServerEntity.class).getServerUrl());
		}
	}

	private void addServers() {
		for (String url : serverUrls) {
			HTTPStatusExceptionHandler.executePost(target(LOAD_BALANCER_ENTRY_POINT)
					.request(), url, true);
		}
	}

	@Test
	public void testBalanacerCanReturnServerUrlWhenInternalCounterIsNegative() {
		
		addServers();
		for (int i = 0; i < Integer.MAX_VALUE*2; i++) {	
			target(LOAD_BALANCER_ENTRY_POINT)
			.request().accept(MediaType.TEXT_PLAIN).get(); // no assertion performed.
		}

	}

//	@Test(timeOut = 1000, threadPoolSize = 10 , invocationCount = 100)
//	public void testMutlithreadedInvocation() {
//		LoadBalancerResource balancer = new ReentrantLockControlledListLoadBalancer();
//		addServers(balancer);
//	}
}

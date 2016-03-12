package com.mkyong.rest;

import static com.paul.demo.common.SharedLabels.NEW_SERVER;
import static com.paul.demo.common.SharedLabels.SERVER_LIST;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.paul.demo.common.TestLabels;
import com.paul.demo.common.exception.HTTPStatusExceptionHandler;
import com.paul.demo.counting.api.TestCountingServerInterface;
import com.paul.demo.server.ServerEntity;

public class ServerListResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig()
				.register(ServerListResource.class)
				.property("contextConfigLocation", "classpath:/test-context.xml");
	}
	
	
	@Test
	public void test1(){
		HTTPStatusExceptionHandler.executePost(
				target(SERVER_LIST).path(NEW_SERVER).request(), 
				new ServerEntity(TestLabels.DUMMY_URL), true);
	}
	
	@Test
	public void testComingOnline(){
		TestCountingServerInterface.
				serversStoreOfYourValues.put(
						new ServerEntity(TestLabels.DUMMY_URL), 50);
		HTTPStatusExceptionHandler.executePost(
				target(SERVER_LIST).path(NEW_SERVER).request(), 
				new ServerEntity(TestLabels.DUMMY_URL), true);
	}
	
	
}

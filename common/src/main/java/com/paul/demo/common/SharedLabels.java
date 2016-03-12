package com.paul.demo.common;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class SharedLabels {
	
	public static final String LOAD_BALANCER_ENTRY_POINT= "loadbalancers";
	public static final String ALL = "all";
	public static final String SERVER_IMPL = "/server-impl/";
	public static final String CLIENT_FACING_COUNTER = "client-facing-counter";
	public static final String REST = "/rest/";
	public static final String SERVER_ALREADY_IN_THE_LIST = "server already in the list";
	public static final String HTTP_LOCALHOST = "http://localhost:";
	public static final String DISTRIBUTED_COUNTER = "distributed-counter";
	public static final int LOAD_BALANCER_PORT=5000;
	public static final String HTTP_PORT = "httpPort";
	public static final String 
		LOAD_BALANCER_URL = HTTP_LOCALHOST+ LOAD_BALANCER_PORT + "/load-balancer-impl/" + LOAD_BALANCER_ENTRY_POINT;
	public static final String NEW_SERVER = "new-server";
	public static final String SERVER_LIST =  "server-list";
	public static final String GOSSIP_SPREADER= "gossip-spreader";

	

	private static URI getLoadBalancerURI() {
		return UriBuilder.fromUri(LOAD_BALANCER_URL).build();
	}
	
	public static WebTarget getLoadBalancerWebTarget(){
		Client client = ClientBuilder.newClient(new ClientConfig());
		return client.target(getLoadBalancerURI());
	}
	
}

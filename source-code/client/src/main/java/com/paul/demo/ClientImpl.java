package com.paul.demo;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.paul.demo.server.ServerEntity;

import static com.paul.demo.common.SharedLabels.*;

public class ClientImpl {

	private static final String GET_VALUE = "getValue";
	private static final String MODIFY_VALUE = "modify -?[0-9]{0,10}";

	public static void main(String[] args) {

		String serverUrl = getServerToSendUpdatesTo().getClientFacingCounterUrl();
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget server = client.target(serverUrl);
		
		
		
		System.out.println("***************************");
		System.out.println("Connected to " + serverUrl);
		System.out.println(" to modify a value \""+ MODIFY_VALUE +"\"");
		System.out.println(" to get current value  \"" + GET_VALUE + "\"");

	
		try(Scanner scanner = new Scanner(System.in)){
			while(scanner.hasNext()) {
				String nextLine = scanner.nextLine().trim();
				
				if(nextLine.matches(MODIFY_VALUE)){
					String[] split = nextLine.split(" ");
					modifyValue(server, split[1]);
					System.out.println("value updated.");
				}else if(nextLine.equalsIgnoreCase(GET_VALUE)){
					System.out.println("Current value:" + getValue(server));
				}else{
					System.out.println("Command not recognized.");
				}

			}	
		}
	}
	private static ServerEntity getServerToSendUpdatesTo() {
		return getLoadBalancerWebTarget().request()
				.accept(MediaType.APPLICATION_JSON).get(ServerEntity.class);
	}
	
	private static Response modifyValue(WebTarget target,String value) {
		Entity<?> entity = Entity.entity(value, MediaType.TEXT_PLAIN);
		Response postResponse = 
				target.request().accept(MediaType.TEXT_PLAIN).post(entity);
		if(postResponse.getStatus()!=200){
			throw new IllegalStateException("return value was " + postResponse.getStatus());
		}
		return postResponse;
	}
	
	private static String getValue(WebTarget target) {
		return target.request().get(String.class);
	}

}

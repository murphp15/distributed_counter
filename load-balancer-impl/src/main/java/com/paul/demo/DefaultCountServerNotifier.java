package com.paul.demo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;

import com.paul.demo.common.SharedLabels;
import com.paul.demo.common.exception.HTTPStatusExceptionHandler;
import com.paul.demo.server.ServerDAO;
import com.paul.demo.server.ServerEntity;

public class DefaultCountServerNotifier implements CountServiceNotifier{

	@Autowired private ServerDAO otherServers;

	@Override
	public void notifyOtherServersOfUpdate(ServerEntity serverEntity) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		for (ServerEntity existingServer : otherServers.findAll()) {
			if( ! serverEntity.equals(existingServer)){
				WebTarget target = client.target(existingServer.getServerUrl());
				Builder builder = target.path(SharedLabels.SERVER_LIST).path(SharedLabels.NEW_SERVER)
						.request();
				HTTPStatusExceptionHandler.executePost(builder, serverEntity, false);
			}
		}
	}


}

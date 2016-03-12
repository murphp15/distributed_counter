package com.mkyong.rest;

import static com.paul.demo.common.SharedLabels.SERVER_LIST;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paul.demo.balancer.api.LoadBalancerInterface;
import com.paul.demo.common.ServerList;
import com.paul.demo.common.SharedLabels;
import com.paul.demo.counting.api.CountingServerInterface;
import com.paul.demo.server.ServerDAO;
import com.paul.demo.server.ServerEntity;
import com.paul.rest.counter.Counter;

@Component
@Path(SERVER_LIST)
public class ServerListResource {

	@Autowired private ServerDAO otherServers;
	@Autowired private CountingServerInterface countingServerInterface;
	@Autowired private LoadBalancerInterface loadBalancerInterface;
	@Autowired private Counter counter;
	public static String localServerUrl;
	
	
	@POST 
	@Path(SharedLabels.NEW_SERVER)
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNewServer(ServerEntity serverEntity){
		if( ! countingServerInterface.getLocalUrl().equals(serverEntity.getServerUrl())){
			this.otherServers.save(serverEntity);
		}
	}
	
	@PostConstruct
	public void postConstruct() {
		loadBalancerInterface.registerUrlWithLoadBalancer();
		readUrlsFromLoadBalancer();
		retrieveYourCountFromOtherServers();
	}


	private void retrieveYourCountFromOtherServers() {
		int maxCountAsReadFromOtherServers = 0;
		for(ServerEntity serverEntity:otherServers.findAll()){
			int count = countingServerInterface.getYourCountFromOtherServer(serverEntity);
			if(count>maxCountAsReadFromOtherServers){
				maxCountAsReadFromOtherServers = count;
			}
		}
		counter.set(maxCountAsReadFromOtherServers);
	}

	private void readUrlsFromLoadBalancer() {
		ServerList servers = this.loadBalancerInterface.readUrlsFromLoadBalancer();
		String localUrl =  countingServerInterface.getLocalUrl();
		for (ServerEntity serverEntity : servers.getItems()) {
			if( ! localUrl.equals(serverEntity.getServerUrl())){
				otherServers.save(serverEntity);
			}
		}
	}
}

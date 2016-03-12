package com.mkyong.rest;

import javax.annotation.PostConstruct;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paul.demo.common.SharedLabels;
import com.paul.demo.counting.api.CountingServerInterface;
import com.paul.demo.server.ServerDAO;
import com.paul.demo.server.ServerEntity;
import com.paul.rest.counter.Counter;

/**
 * Used to periodically notify the other servers of 
 * */
@Component
@Path(SharedLabels.GOSSIP_SPREADER)
public class GossipSpreaderImpl {

	@Autowired private ServerDAO otherServers;
	@Autowired private Counter localValue;
	@Autowired private CountingServerInterface countingServerInterface;
	private int sleepTime = 10000;

	public GossipSpreaderImpl(){
		
	}

	@PostConstruct
	public void start() {
		new Thread(){
			public void run(){
				while (true) {
					GossipSpreaderImpl.this.sleep();
					publishUpdatesToOtherServers();	
				}
			}
		}.start();
	}

	private void publishUpdatesToOtherServers() {
		int currentCount = localValue.getCurrentCount();
		for(ServerEntity entity:otherServers.findAll()){
			countingServerInterface.publishUpdatesToOtherServer(entity,currentCount);
			
		}
	}

	public void sleep() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// do nothing.
		}
	}

	@POST
	@Path("change-sleep-time/{time}")
	public void updateSleepTime(@PathParam("time") String time){
		sleepTime = Integer.parseInt(time);
	}
	
}
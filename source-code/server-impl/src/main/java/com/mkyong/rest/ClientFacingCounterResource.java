package com.mkyong.rest;

import static com.paul.demo.common.SharedLabels.CLIENT_FACING_COUNTER;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paul.demo.server.ServerEntity;
import com.paul.rest.counter.Counter;
import com.paul.rest.tree.primitive.CountCollatingService;
/**
 * Used by the client. 
 * the client can use post a modification to the number or to get the number.
 */
@Component
@Path(CLIENT_FACING_COUNTER)
public class ClientFacingCounterResource {
	
	@Autowired private Counter counter;
	@Autowired private CountCollatingService<ServerEntity> countCollatingService;
	
	/**
	 * returns a string representation of its own count plus its best guest of the 
	 * 	sum of the values held by all the other services. 
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		return Integer.toString(counter.getCurrentCount() + countCollatingService.getCount());
	}
	
	/**
	 * 
	 * @param incrementValue  the amount that the client wants to change the current number by.
	 * @return
	 */
	@POST
	public String updateValue(String incrementValue){
		counter.sum(Integer.parseInt(incrementValue));
		return "processed"; 
	}
	
}
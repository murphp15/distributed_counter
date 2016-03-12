package com.mkyong.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paul.demo.common.CurrentCountForServer;
import com.paul.demo.common.SharedLabels;
import com.paul.demo.server.ServerEntity;
import com.paul.rest.tree.primitive.CountCollatingService;

/**
 * @author funzo69
 *	This class is a public api used by the other servers. They can use 
 */
@Component
@Path(SharedLabels.DISTRIBUTED_COUNTER)
public class DistributedCounterResource {

	@Autowired private CountCollatingService<ServerEntity> countCollatingService;
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void postUpdate(CurrentCountForServer update){
		countCollatingService.put(update.getServer(), update.getNewValue());
	}
	/**
	 * method is called after a server restarts. 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getValueFor(ServerEntity url){
		return Integer.toString(countCollatingService.getValueFor(url));
	}
	
}

package com.paul.demo;

import static com.paul.demo.common.SharedLabels.ALL;
import static com.paul.demo.common.SharedLabels.SERVER_ALREADY_IN_THE_LIST;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paul.demo.common.ServerList;
import com.paul.demo.common.SharedLabels;
import com.paul.demo.server.ServerDAO;
import com.paul.demo.server.ServerEntity;

/* Runs as a load balancer.
 * assigns servers to  */
@Component
@Path(SharedLabels.LOAD_BALANCER_ENTRY_POINT)
public class LoadBalancerResource  {

	@Autowired private ServerDAO otherServers;
	@Autowired private CountServiceNotifier countServiceNotifier;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private ReadLock readLock = lock.readLock();
	private WriteLock writeLock = lock.writeLock();
	private static AtomicInteger counter = new AtomicInteger(0);

	private static final Log LOG = LogFactory.getLog(LoadBalancerResource.class);

	@POST
	public String updateServerList(String serverUrl){
		readLock.lock();
		LOG.info("attemping to add serverUrl "+ serverUrl + " to the list of servers.");
		try{
			if(otherServers.contains(serverUrl)){
				LOG.info(serverUrl + " already exists in the list");
				return SERVER_ALREADY_IN_THE_LIST;
			}else{
				boolean addedToList = addServerToList(serverUrl);
				if(addedToList){
					countServiceNotifier.notifyOtherServersOfUpdate(otherServers.findById(serverUrl));
				}
				return Boolean.toString(addedToList);
			}
		}finally{
			readLock.unlock();
		}
	}


	private boolean addServerToList(String serverUrl) {
		readLock.unlock();
		writeLock.lock();
		try{
			if(otherServers.contains(serverUrl)){
				return false;
			}else{
				return otherServers.save(new ServerEntity(serverUrl));
			}
		}finally{
			writeLock.unlock();
			readLock.lock();
		}
	}



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ServerEntity getNextServer(){
		readLock.lock();
		try{
			List<ServerEntity> findAll = otherServers.findAll();
			if(findAll.isEmpty()){
				return null;
			}else {
				int index = Math.abs(counter.getAndIncrement()%findAll.size());
				return findAll.get(index);
			}
		}finally{
			readLock.unlock();
		}
	}
	// the point I am stuck at now is that the URL is not going through 	
	@GET
	@Path(ALL)
	@Produces(MediaType.APPLICATION_JSON)
	public ServerList getAllServer(){
		readLock.lock();
		try{
			return new ServerList(otherServers.findAll());
		}finally{
			readLock.unlock();
		}
	}



}

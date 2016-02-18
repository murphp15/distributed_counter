package com.paul.demo.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NonPersistantServerDao implements ServerDAO {

	private static List<ServerEntity> serverEntities = new ArrayList<>();
	private static final Log LOG = LogFactory.getLog(NonPersistantServerDao.class);

	
	@Override
	public boolean save(ServerEntity entity) {
		if(serverEntities.contains(entity)){
			return false;
		}else{
			return serverEntities.add(entity);
		}
		
	}

	@Override
	public void delete(ServerEntity entity) {
		serverEntities.remove(entity);
	}

	@Override
	public ServerEntity findById(String serverId) {
		for (ServerEntity serverEntity : serverEntities) {
			if(serverEntity.getServerUrl().equals(serverId)){
				return serverEntity;
			}
		}
		return null;
	}

	@Override
	public List<ServerEntity> findAll() {
		return serverEntities;
	}


	@Override
	public boolean contains(String serverUrl) {
		return findById(serverUrl)!=null;
	}

}

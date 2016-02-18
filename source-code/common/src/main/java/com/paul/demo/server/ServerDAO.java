package com.paul.demo.server;


import com.paul.demo.common.dao.domain.GenericDao;

public interface ServerDAO extends GenericDao<ServerEntity, String> {

	
	public boolean contains(String serverUrl);

}

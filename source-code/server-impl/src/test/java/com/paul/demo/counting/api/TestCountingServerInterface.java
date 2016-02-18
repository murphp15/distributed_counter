package com.paul.demo.counting.api;

import java.util.HashMap;
import java.util.Map;

import com.paul.demo.common.TestLabels;
import com.paul.demo.server.ServerEntity;

public class TestCountingServerInterface implements CountingServerInterface {
	
	public static Map<ServerEntity, Integer> serversStoreOfYourValues
				= new HashMap<>();
	@Override
	public String getLocalUrl() {
		return TestLabels.LOCAL_URL;
	}

	@Override
	public int getYourCountFromOtherServer(ServerEntity serverEntity) {
		Integer integer = serversStoreOfYourValues.get(serverEntity);
		if(integer==null){
			return 0;
		}else{
			return integer;
		}
	}

	@Override
	public void publishUpdatesToOtherServer(ServerEntity entity, int currentCount) {
		serversStoreOfYourValues.put(entity, currentCount);
	}

}

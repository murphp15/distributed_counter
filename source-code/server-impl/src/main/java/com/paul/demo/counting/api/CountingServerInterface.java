package com.paul.demo.counting.api;

import com.paul.demo.server.ServerEntity;

public interface CountingServerInterface {

	public String getLocalUrl();

	public int getYourCountFromOtherServer(ServerEntity serverEntity);

	public void publishUpdatesToOtherServer(ServerEntity entity, int currentCount);
}

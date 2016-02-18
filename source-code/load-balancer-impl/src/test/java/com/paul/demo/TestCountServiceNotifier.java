package com.paul.demo;

import com.paul.demo.server.ServerEntity;

public class TestCountServiceNotifier implements CountServiceNotifier {


	@Override
	public void notifyOtherServersOfUpdate(ServerEntity findById) {
		// Do nothing. 
	}
}

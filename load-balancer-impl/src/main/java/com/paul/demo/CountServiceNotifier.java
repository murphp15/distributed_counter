package com.paul.demo;

import com.paul.demo.server.ServerEntity;

public interface CountServiceNotifier {

	void notifyOtherServersOfUpdate(ServerEntity findById);

}

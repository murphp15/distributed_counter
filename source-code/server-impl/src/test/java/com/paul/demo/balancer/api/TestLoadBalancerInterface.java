package com.paul.demo.balancer.api;

import java.util.Arrays;

import com.paul.demo.common.ServerList;
import com.paul.demo.common.TestLabels;
import com.paul.demo.server.ServerEntity;

public class TestLoadBalancerInterface implements
	LoadBalancerInterface {

	@Override
	public void registerUrlWithLoadBalancer() {
		// do nothing.
	}

	@Override
	public ServerList readUrlsFromLoadBalancer() {
		return new ServerList(Arrays.asList(new ServerEntity(TestLabels.DUMMY_URL)));
	}

}

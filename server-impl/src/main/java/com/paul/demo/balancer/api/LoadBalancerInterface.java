package com.paul.demo.balancer.api;

import com.paul.demo.common.ServerList;

public interface LoadBalancerInterface {

	void registerUrlWithLoadBalancer();

	ServerList readUrlsFromLoadBalancer();

}

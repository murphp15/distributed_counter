package com.paul.demo.balancer.api;

import static com.paul.demo.common.SharedLabels.ALL;
import static com.paul.demo.common.SharedLabels.getLoadBalancerWebTarget;
import static com.paul.rest.utilities.ServerUtilities.createLocaUrl;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paul.demo.common.ServerList;
import com.paul.demo.common.SharedLabels;
import com.paul.demo.common.exception.HTTPStatusExceptionHandler;

@Component
public class DefaultLoadBalanceInterface implements LoadBalancerInterface{

	@Autowired private ServletContext servletContext;
	
	@Override
	public void registerUrlWithLoadBalancer() {
		String localServerUrl = createLocaUrl(servletContext);
		HTTPStatusExceptionHandler.executePost(
				getLoadBalancerWebTarget().request(), localServerUrl, true);
	}

	
	@Override
	public ServerList readUrlsFromLoadBalancer() {
		return SharedLabels.getLoadBalancerWebTarget().path(ALL).request()
				.accept(MediaType.APPLICATION_JSON).get(ServerList.class);
	}
}

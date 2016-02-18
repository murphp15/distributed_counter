package com.paul.rest.utilities;

import static com.paul.demo.common.SharedLabels.HTTP_LOCALHOST;
import static com.paul.demo.common.SharedLabels.REST;
import static com.paul.demo.common.SharedLabels.SERVER_IMPL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.paul.demo.common.SharedLabels;

public class ServerUtilities {


	public static String createLocaUrl(ServletContextEvent servletContextEvent) {
		return createLocaUrl(servletContextEvent.getServletContext());
	}


	public static String createLocaUrl(ServletContext servletContext) {
		int port = Integer.parseInt(servletContext.getInitParameter(SharedLabels.HTTP_PORT));
		return HTTP_LOCALHOST + port + SERVER_IMPL + REST;
	}

	public static int getHttpPort(ServletContextEvent arg0) {
		int port = Integer.parseInt(arg0.getServletContext().getInitParameter(SharedLabels.HTTP_PORT));
		return port;
	}
	
}

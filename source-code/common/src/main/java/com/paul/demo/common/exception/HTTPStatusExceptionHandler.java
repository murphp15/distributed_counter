package com.paul.demo.common.exception;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTTPStatusExceptionHandler {

	private static final Log LOG = LogFactory.getLog(HTTPStatusExceptionHandler.class);
	
	public static void executePost(Builder builder, Object entity, boolean throwErrorOnFail){
		Response result = builder.post(Entity.entity(entity, MediaType.APPLICATION_JSON));
		handleError(throwErrorOnFail, result);	
	}

	public static void executePut(Builder builder, Object entity, boolean throwErrorOnFail) {
		Response result = builder.put(Entity.entity(entity, MediaType.APPLICATION_JSON));
		handleError(throwErrorOnFail, result);	
	}

	private static void handleError(boolean throwErrorOnFail, Response result) {
		if(result.getStatus()==200 || result.getStatus()==204){
			return; 
		}else if(throwErrorOnFail){
			throw new RuntimeException("unexpected http request failure " + result.getStatus());
		}else{
			LOG.error("Exception occured when executing rest POST request." + result.getStatus());	
		}
	}

}

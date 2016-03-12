package com.paul.rest.counter;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mkyong.rest.ServerListResource;

public class AtomicIntegerCounter implements Counter {

	private AtomicInteger atomicInteger = new AtomicInteger();
	private static final Log LOG = LogFactory.getLog(AtomicIntegerCounter.class);

	@Override
	public void sum(int i) {
		boolean debugEnabled = LOG.isInfoEnabled();
		if(debugEnabled){
			LOG.debug(ServerListResource.localServerUrl 
					+ ":::" + "current count in local cache is being updated by " + i);
		}

		int result = atomicInteger.addAndGet(i);

		if(debugEnabled){
			LOG.debug(ServerListResource.localServerUrl 
					+ ":::" + "current count is updated to "  + result);
		}
	}

	@Override
	public int getCurrentCount() {
		return atomicInteger.get();
	}

	@Override
	public void set(int count) {
		atomicInteger.set(count);;
	}
}

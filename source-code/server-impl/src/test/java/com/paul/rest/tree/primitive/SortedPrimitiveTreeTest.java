package com.paul.rest.tree.primitive;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.mkyong.rest.tree.ClientSimulator;
import com.paul.demo.server.ServerEntity;

public class SortedPrimitiveTreeTest {

	private static final int NUMBER_OF_SIMULTANIOUS_USERS = 1;
	private SortedPrimitiveTree<ServerEntity> primitiveTree;
	
	@Before
	public void setup(){
		primitiveTree = new SortedPrimitiveTree<>();
	}
	
	@Test
	public void testInsert(){
		primitiveTree.put(new ServerEntity("server1"), 60);
		primitiveTree.put(new ServerEntity("server2"), 12);
		assertEquals(72,primitiveTree.getCount());
	}
	
	
	@Test
	public void test() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(NUMBER_OF_SIMULTANIOUS_USERS, 
				NUMBER_OF_SIMULTANIOUS_USERS, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1));
		for (int i=0;i<NUMBER_OF_SIMULTANIOUS_USERS;i++) {
			threadPoolExecutor.submit(
					new ClientSimulator<ServerEntity>(new ServerEntity("http://www.localhost.com/test"), primitiveTree));
		}
		while(threadPoolExecutor.getActiveCount()>0){
			Thread.sleep(1000);
		}
		threadPoolExecutor.shutdown();
		while (! threadPoolExecutor.isShutdown()) {
			
		}
		threadPoolExecutor.awaitTermination(100, TimeUnit.MINUTES);
		System.out.println(primitiveTree.getCount());
		
	}
	
	

}

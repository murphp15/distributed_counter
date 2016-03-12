package com.mkyong.rest.tree;

import com.paul.rest.tree.primitive.CountCollatingService;

public final class ClientSimulator<KeyType extends Object> implements Runnable{

	private static final double READ_TO_WRITE_RATIOS = .5;
	private static final int NUMBER_OF_OPERATIONS_PERFORMED_PER_USER = 100000;
	private CountCollatingService<KeyType> allOtherServices;
	private KeyType key;
	
	public ClientSimulator(KeyType key,CountCollatingService<KeyType> allOtherServices) {
		this.key  = key;
		this.allOtherServices = allOtherServices;
	}
	@Override
	public void run() {
		for (int i=0;i<NUMBER_OF_OPERATIONS_PERFORMED_PER_USER;i++) {
			if(Math.random()>READ_TO_WRITE_RATIOS){
				allOtherServices.put(key, 15);
			}else{
				allOtherServices.getCount();
			}
		}
	}
	
}
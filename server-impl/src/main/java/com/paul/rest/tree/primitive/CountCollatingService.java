package com.paul.rest.tree.primitive;
/**
 *
 *This class is responsible for storing the current counts of other servers. 
 *The values of other servers will be passed into it through the put method.
 *It can return the total of all these counter through the getCount method. 
 *	
 */
public interface CountCollatingService<KeyType extends Object> {

	void put(KeyType serverUrl,int count);
	
	int getCount();

	int getValueFor(KeyType serverUrl);
}

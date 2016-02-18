package com.paul.rest.tree.primitive;

import java.util.concurrent.locks.ReentrantLock;

public class PrimitiveNode<KeyType extends Object> {

	private int  value;
	protected KeyType key;
	protected PrimitiveNode<KeyType> left;
	protected PrimitiveNode<KeyType> right;
	protected int computedValueOfChildren;
	protected ReentrantLock leftLock = new ReentrantLock();
	protected ReentrantLock rightLock = new ReentrantLock();
	

	public PrimitiveNode() {

	}

	public void put(KeyType newKey,int newValue){
		computedValueOfChildren = -1;
		if(this.key==null || key.equals(newKey)){
			this.key = newKey;
			this.value = newValue;
			return;
		}else{
			if(isLessThan(newKey)){
				putInLeftBranch(newKey, newValue);
			}else{
				putInRightBranch(newKey, newValue);
			}
		}
	}

	private void putInRightBranch(KeyType newKey, int newValue) {
		if(this.right==null){
			rightLock.lock();
			try{
				if(right==null){
					right = new PrimitiveNode<KeyType>();
					right.put(newKey, newValue);
					return;
				}
			}finally{
				rightLock.unlock();
			}
		}
		right.put(newKey, newValue);
	}

	private void putInLeftBranch(KeyType newKey, int newValue) {
		if(left==null){
			leftLock.lock();
			try{
				if(left==null){
					left = new PrimitiveNode<KeyType>();
					left.put(newKey, newValue);
					return;
				}
			}finally{
				leftLock.unlock();
			}
		}
		left.put(newKey, newValue);
	}

	public int getValueFor(KeyType newKey) {
		if(key ==null || newKey.equals(key)){
			return value;
		}else{
			if(isLessThan(newKey)){
				return getValueFor(newKey, left);
			}else{
				return getValueFor(newKey, right);
			}
		}

	}

	private int getValueFor(KeyType key,PrimitiveNode<KeyType> node) {
		if(node==null){
			return 0;
		}else{
			return node.getValueFor(key);
		}
	}

	

	public int get() {
		if(computedValueOfChildren==-1){
			this.computedValueOfChildren = get(right) + get(left) + this.value;
		}
		return computedValueOfChildren;
	}
	



	public int get(PrimitiveNode<KeyType> node){
		if(node==null){
			return 0;
		}else{
			return node.get();
		}
	}
	
	protected boolean isLessThan(Object server) {
		return server.hashCode()>key.hashCode();
	}
}

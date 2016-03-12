package com.paul.rest.tree.primitive;

public class SortedPrimitiveTree<KeyType extends Object> implements CountCollatingService<KeyType> {

	private PrimitiveNode<KeyType> rootNode = new PrimitiveNode<>();

	@Override
	public void put(KeyType key, int newValue) {
		this.rootNode.put(key, newValue);
	}

	@Override
	public int getCount() {
		return this.rootNode.get();
	}
	@Override
	public int getValueFor(KeyType key) {
		return this.rootNode.getValueFor(key);
		
	}
}

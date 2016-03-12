package com.paul.demo.common.dao.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Generic interface for Data Access Objects. To be extended or implemented.
 * Contains common persistence methods.
 * 
 * @author Arthur Vin
 */
public interface GenericDao<T, ID extends Serializable> {
	
	boolean save(T entity);
	void delete(T entity);
	T findById(ID id);
	List<T> findAll();
}

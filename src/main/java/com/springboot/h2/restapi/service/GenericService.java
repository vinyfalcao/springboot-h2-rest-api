package com.springboot.h2.restapi.service;

import java.util.List;

/**
 * 
 * @author Vinicius Falcão
 *
 */
public interface GenericService<T> {

	T save(T product);

	void delete(long idProduct);

	List<T> findAll();

}

package com.springboot.h2.restapi.service;

import java.util.List;

import com.springboot.h2.restapi.model.Product;

/**
 * 
 * @author Vinicius Falcão
 *
 */
public interface ProductService extends GenericService<Product> {

	List<Product> findCustomizedAll();

	Product findById(Long id);

	Product findDetailedById(Long id);

	List<Product> findChildrenByFatherId(Long id);

	List<Product> findByName(String name);
}

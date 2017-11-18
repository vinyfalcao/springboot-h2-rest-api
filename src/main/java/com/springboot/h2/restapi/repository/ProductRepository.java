package com.springboot.h2.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.springboot.h2.restapi.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Vinicius Falcão
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

	/*
	 * @Query("SELECT p FROM Product p LEFT JOIN FETCH p.parentProduct LEFT JOIN FETCH p.images "
	 * + "LEFT JOIN FETCH p.childrenProducts")
	 */
	@EntityGraph(type = EntityGraphType.LOAD, value = "Product.detail")
	List<Product> findDistinctBy();

	@EntityGraph(type = EntityGraphType.FETCH, value = "Product.detail")
	Product findById(Long id);

	@EntityGraph(type = EntityGraphType.FETCH, value = "Product.detail")
	List<Product> findByParentProduct_Id(Long id);

	@EntityGraph(type = EntityGraphType.FETCH, value = "Product.detail")
	List<Product> findByName(String name);

}

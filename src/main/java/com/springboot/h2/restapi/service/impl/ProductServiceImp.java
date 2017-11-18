package com.springboot.h2.restapi.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.h2.restapi.model.Product;
import com.springboot.h2.restapi.model.exception.AvanueChallengeBusinessException;
import com.springboot.h2.restapi.repository.ProductRepository;
import com.springboot.h2.restapi.service.ProductService;

/**
 * 
 * @author Vinicius Falcão
 *
 */
@Service
public class ProductServiceImp extends GenericServiceImp<Product, ProductRepository> implements ProductService {

	@Override
	public List<Product> findCustomizedAll() {
		return repository.findDistinctBy();
	}

	@Override
	public Product findById(Long id) {
		Product product = repository.findOne(id);
		if (product == null) {
			throw new AvanueChallengeBusinessException(HttpStatus.BAD_REQUEST, "Product not Found");
		}
		return product;
	}

	@Override
	public Product findDetailedById(Long id) {
		Product product = repository.findById(id);
		if (product == null) {
			throw new AvanueChallengeBusinessException(HttpStatus.BAD_REQUEST, "Product not Found");
		}
		return product;
	}

	@Override
	public List<Product> findChildrenByFatherId(Long id) {
		return repository.findByParentProduct_Id(id);
	}

	@Override
	public List<Product> findByName(String name) {
		return repository.findByName(name);
	}

}

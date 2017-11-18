package com.springboot.h2.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import com.springboot.h2.restapi.model.exception.AvanueChallengeBusinessException;
import com.springboot.h2.restapi.service.GenericService;

/**
 * 
 * @author Vinicius Falcão
 *
 */
public abstract class GenericServiceImp<T, R extends JpaRepository<T, Long>> implements GenericService<T> {

	@Autowired
	protected R repository;

	@Override
	public T save(T entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(long idProduct) {
		try {
			repository.delete(idProduct);
		} catch (Exception e) {
			throw new AvanueChallengeBusinessException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

}

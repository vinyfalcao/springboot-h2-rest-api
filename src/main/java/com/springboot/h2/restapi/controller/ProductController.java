package com.springboot.h2.restapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.springboot.h2.restapi.model.Product;
import com.springboot.h2.restapi.service.ProductService;
import com.springboot.h2.restapi.views.ProductViews;

/**
 * 
 * @author Vinicius Falcão
 *
 */
@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<Product> save(@RequestBody @Valid Product product) {
		return new ResponseEntity<Product>(productService.save(product), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		productService.delete(id);
	}

	@JsonView(ProductViews.LazyView.class)
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		return new ResponseEntity<List<Product>>(productService.findAll(), HttpStatus.OK);
	}

	@JsonView(ProductViews.LazyView.class)
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") Long id) {
		return new ResponseEntity<Product>(productService.findById(id), HttpStatus.OK);
	}

	@JsonView(ProductViews.DetailedView.class)
	@GetMapping("/detail/{id}")
	public ResponseEntity<Product> findDetailedById(@PathVariable("id") Long id) {
		return new ResponseEntity<Product>(productService.findDetailedById(id), HttpStatus.OK);
	}

	@JsonView(ProductViews.DetailedView.class)
	@GetMapping("/detail")
	public ResponseEntity<List<Product>> findAllEager() {
		return new ResponseEntity<List<Product>>(productService.findCustomizedAll(), HttpStatus.OK);
	}

	@JsonView(ProductViews.LazyView.class)
	@GetMapping("/children/{fatherId}")
	public ResponseEntity<List<Product>> findChildrenByFatherId(@PathVariable("fatherId") Long fatherId) {
		return new ResponseEntity<List<Product>>(productService.findChildrenByFatherId(fatherId), HttpStatus.OK);
	}

}

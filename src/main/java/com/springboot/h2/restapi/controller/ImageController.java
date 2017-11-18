package com.springboot.h2.restapi.controller;

import java.util.List;

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
import com.springboot.h2.restapi.model.Image;
import com.springboot.h2.restapi.service.ImageService;
import com.springboot.h2.restapi.views.ImageViews;

/**
 * 
 * @author Vinicius Falcão
 *
 */
@RestController
@RequestMapping("image")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@JsonView(ImageViews.Default.class)
	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<Image> save(@RequestBody Image image) {
		return new ResponseEntity<Image>(imageService.save(image), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		imageService.delete(id);
	}

	@JsonView(ImageViews.Default.class)
	@GetMapping("/{id}")
	public ResponseEntity<Image> findById(@PathVariable("id") Long id) {
		return new ResponseEntity<Image>(imageService.findById(id), HttpStatus.OK);
	}

	@JsonView(ImageViews.Default.class)
	@GetMapping("/findByProduct/{idProduct}")
	public ResponseEntity<List<Image>> findByProductId(@PathVariable("idProduct") Long idProduct) {
		return new ResponseEntity<List<Image>>(imageService.findByProduct(idProduct), HttpStatus.OK);
	}

}

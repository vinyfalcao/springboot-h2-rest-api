package com.springboot.h2.restapi.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.h2.restapi.model.Image;
import com.springboot.h2.restapi.model.exception.AvanueChallengeBusinessException;
import com.springboot.h2.restapi.repository.ImageRepository;
import com.springboot.h2.restapi.service.ImageService;

/**
 * 
 * @author Vinicius Falcão
 *
 */
@Service
public class ImageServiceImp extends GenericServiceImp<Image, ImageRepository> implements ImageService {

	@Override
	public List<Image> findByProduct(Long idProduct) {
		return repository.findByProduct_Id(idProduct);
	}

	@Override
	public Image findById(Long id) {
		Image image = repository.findOne(id);
		if (image == null) {
			throw new AvanueChallengeBusinessException(HttpStatus.BAD_REQUEST, "Image not Found");
		}
		return image;
	}

}

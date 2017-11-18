package com.springboot.h2.restapi.controller;

import static org.junit.Assert.assertNotNull;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.GsonBuilder;
import com.springboot.h2.restapi.model.Image;
import com.springboot.h2.restapi.model.Product;
import com.springboot.h2.restapi.service.ImageService;
import com.springboot.h2.restapi.service.ProductService;

/**
 * 
 * @author Vinicius Falcão
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/testImage.sql")
public class ImageControllerTest extends AbstractControllerTest {

	private static String URL = "/image";

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageService imageService;

	private Image getImageToSave(Long idProduct, String imageName) {
		Image image = new Image();
		image.setType(imageName);
		Product product = new Product();
		product.setId(idProduct);
		image.setProduct(product);
		return image;
	}

	private Image getImageToTest(String productName, String imageName) {
		Long productIdToDeleteImage = productService.findByName(productName).get(0).getId();
		return imageService.save(getImageToSave(productIdToDeleteImage, imageName));

	}

	@Test
	public void testSave() throws Exception {
		String json = new GsonBuilder().create().toJson(getImageToSave(
				productService.findByName("Test Product Save Image").get(0).getId(), "Test Product Save Image"));
		post(URL + "/save", json).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("type", Matchers.is("Test Product Save Image")));
	}

	@Test
	public void testUpdate() throws Exception {
		Image image = imageService.save(getImageToSave(
				productService.findByName("TEST_PRODUCT_UPDATE_IMAGE").get(0).getId(), "TEST_PRODUCT_UPDATE_IMAGE"));
		assertNotNull(image.getId());
		image.setType("UPDATED VALUE");
		String json = new GsonBuilder().create().toJson(image);
		post(URL + "/save", json).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("type", Matchers.is(image.getType())));
	}

	@Test
	public void testDeleteImage() throws Exception {
		Image imageToTest = getImageToTest("TEST_DELETE_IMAGE", "TEST_DELETE_IMAGE");
		delete(URL + "/" + imageToTest.getId()).andExpect(MockMvcResultMatchers.status().isOk());

		get(URL + "/" + imageToTest.getId()).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testFindByProductId() throws Exception {
		Long productIdToDeleteImage = productService.findByName("TEST_FINDBYID_IMAGE").get(0).getId();
		imageService.save(getImageToSave(productIdToDeleteImage, "TEST_FINDBYID_IMAGE"));

		get(URL + "/findByProduct/" + productIdToDeleteImage).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("[0].type", Matchers.is("TEST_FINDBYID_IMAGE")));

	}

}

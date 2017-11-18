package com.springboot.h2.restapi.controller;

import static org.junit.Assert.assertEquals;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest extends AbstractControllerTest {

	private static final String URL = "/product";

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageService imageService;

	private Product getProductToTest(String productName, Product parentProduct) {
		Product product = new Product();
		product.setName(productName);
		product.setDescription("Generic Description");
		product.setParentProduct(parentProduct);
		return product;
	}

	private void insertImages(String name, Product product) {
		Image image = new Image();
		image.setType(name);
		image.setProduct(product);
		imageService.save(image);
	}

	@Test
	public void testSave() throws Exception {
		String json = new GsonBuilder().create().toJson(getProductToTest("TEST_PRODUCT_SAVE", null));
		post(URL + "/save", json).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("TEST_PRODUCT_SAVE")));

		json = new GsonBuilder().create().toJson(getProductToTest("TEST_PRODUCT_SAVE_WITH_PARENT",
				productService.findByName("TEST_PRODUCT_SAVE").get(0)));
		post(URL + "/save", json).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("TEST_PRODUCT_SAVE_WITH_PARENT")))
				.andExpect(MockMvcResultMatchers.jsonPath("parentProduct.id", Matchers.notNullValue()));

	}

	@Test
	public void testUpdate() throws Exception {
		String json = new GsonBuilder().create().toJson(getProductToTest("TEST_PRODUCT_UPDATE", null));
		post(URL + "/save", json).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("TEST_PRODUCT_UPDATE")));

		Product productToUpdate = productService.findByName("TEST_PRODUCT_UPDATE").get(0);
		productToUpdate.setName("TEST_PRODUCT_UPDATED_NOW");
		productToUpdate.setDescription("DESCRIPTION UPDATED");
		json = new GsonBuilder().create().toJson(productToUpdate);

		post(URL + "/save", json).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is(productToUpdate.getName()))).andExpect(
						MockMvcResultMatchers.jsonPath("description", Matchers.is(productToUpdate.getDescription())));

		Product productToCheck = productService.findById(productToUpdate.getId());

		assertEquals(productToCheck.getDescription(), productToUpdate.getDescription());
		assertEquals(productToCheck.getName(), productToUpdate.getName());
	}

	@Test
	public void testDelete() throws Exception {
		delete(URL + "/" + 987987).andExpect(MockMvcResultMatchers.status().isNotFound());

		Long idToDelete = productService.save(getProductToTest("PRODUCT_DELETE_TEST", null)).getId();
		get(URL + "/" + idToDelete).andExpect(MockMvcResultMatchers.status().isOk());

		delete(URL + "/" + idToDelete).andExpect(MockMvcResultMatchers.status().isOk());

		get(URL + "/" + idToDelete).andExpect(MockMvcResultMatchers.status().isBadRequest());

		Product fatherProduct = productService.save(getProductToTest("PRODUCT_FATHER_DELETE_TEST", null));
		Product childProduct = productService.save(getProductToTest("PRODUCT_CHILD_DELETE_TEST", fatherProduct));
		insertImages("IMAGE_FATHER_tEST", fatherProduct);
		insertImages("IMAGE_FATHER_tEST2", fatherProduct);
		insertImages("IMAGE_FATHER_tEST", childProduct);
		insertImages("IMAGE_FATHER_tEST2", childProduct);

		delete(URL + "/" + fatherProduct.getId()).andExpect(MockMvcResultMatchers.status().isOk());
		get(URL + "/" + fatherProduct.getId()).andExpect(MockMvcResultMatchers.status().isBadRequest());
		get(URL + "/" + childProduct.getId()).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	@Sql("/sql/testProductFindAll.sql")
	public void testFindAll() throws Exception {
		get(URL).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(4)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].childrenProducts").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].images").doesNotExist());
	}

	@Test
	@Sql("/sql/testProductFindAll.sql")
	public void testFindAllEager() throws Exception {
		get(URL + "/detail").andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(4)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].childrenProducts").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].images").exists());
	}

	@Test
	@Sql("/sql/testProductFindById.sql")
	public void testFindById() throws Exception {
		get(URL + "/" + 456654).andExpect(MockMvcResultMatchers.status().isBadRequest());

		Product product = productService.findByName("PRODUCT_FIND_BY_ID").get(0);
		get(URL + "/" + product.getId()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("PRODUCT_FIND_BY_ID")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.childrenProducts").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("$.images").doesNotExist());

	}

	@Test
	@Sql("/sql/testProductFindById.sql")
	public void testFindDetailedById() throws Exception {
		get(URL + "/detail/" + 456654).andExpect(MockMvcResultMatchers.status().isBadRequest());

		Product product = productService.findByName("PRODUCT_FIND_BY_ID").get(0);
		get(URL + "/detail/" + product.getId()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("PRODUCT_FIND_BY_ID")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.childrenProducts").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.images").exists());
	}

	@Test
	public void testFindChildrenByFatherId() throws Exception {
		Product productFather = productService.save(getProductToTest("TEST_PRODUCT_FATHER_TEST", null));
		Product product = productService.save(getProductToTest("TEST_PRODUCT_SAVE_WITH_PARENT", productFather));

		get(URL + "/children/" + productFather.getId()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("[0].name", Matchers.is(product.getName())));
	}

}

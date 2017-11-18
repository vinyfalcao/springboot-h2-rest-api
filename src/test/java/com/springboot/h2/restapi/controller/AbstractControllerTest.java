package com.springboot.h2.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 
 * @author Vinicius Falcão
 *
 */
public abstract class AbstractControllerTest {

	@Autowired
	private MockMvc mockMvc;

	protected ResultActions get(String url) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.get(url));
	}

	protected ResultActions post(String url, String json) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(json));
	}

	protected ResultActions delete(String url) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.delete(url));

	}

}

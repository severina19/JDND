package com.example.demo;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class SareetaApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<CreateUserRequest> json;

	@Autowired
	private JacksonTester<User> userJson;

	@Test
	public void contextLoads() {
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
	/*@Before
	public void setup() throws Exception{
		createUser();
	}*/

	@Test
	public void createUser() throws Exception {
		CreateUserRequest userRequest = getUserRequest("Anja", "asdfghjk");

		mvc.perform(
				post(new URI("/api/user/create"))
						.content(convertObjectToJsonBytes(userRequest))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void loginUser() throws Exception{
		createUser();
		User user = new User();
		user.setUsername("Anja");
		user.setPassword("asdfghjk");
		mvc.perform(
				post(new URI("/login"))
						.content(convertObjectToJsonBytes(user))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						)
				.andExpect(status().isOk());
	}

	/**
	 * Creates an example User object for use in testing.
	 * @return an example User object
	 */
	private CreateUserRequest getUserRequest(String username, String password) {
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setPassword(password);
		userRequest.setUsername(username);
		userRequest.setConfirmPassword("asdfghjk");

		return userRequest;
	}

}

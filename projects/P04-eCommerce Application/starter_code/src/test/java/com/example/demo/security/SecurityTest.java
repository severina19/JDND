package com.example.demo.security;

import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void dontAllowAnonymousUserRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(status().isForbidden());
    }

    @Test
    public void allowUserCreation() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Anja");
        createUserRequest.setPassword("asdfghjk");
        createUserRequest.setConfirmPassword("asdfghjk");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create").content(asJsonString(createUserRequest))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void notAllowUserCreationPasswordShort() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Anja");
        createUserRequest.setPassword("asdfgh");
        createUserRequest.setConfirmPassword("asdfgh");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create").content(asJsonString(createUserRequest))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

}
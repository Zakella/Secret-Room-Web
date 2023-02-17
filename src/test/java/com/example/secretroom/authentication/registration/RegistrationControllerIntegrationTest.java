package com.example.secretroom.authentication.registration;

import com.example.secretroom.utils.JsonObjectConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Test
    void itShouldRegister() throws Exception {
        //Given
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "Joe", "Doe", "joe@gmail.com", "password"
        );


        ResponseEntity<String> responseEntity = ResponseEntity.ok("User successfully signed up");
        doReturn(responseEntity).when(registrationService).register(any());

        String jsonUserRequest = JsonObjectConverter.objectToJson(registrationRequest);

        //When

        assert jsonUserRequest != null;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserRequest))
                .andExpect(status().isOk())
                .andReturn();
        //Then

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("User successfully signed up", response);

    }

    @Test
    void itNotShouldRegister() throws Exception {
        //Given
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "Joe", "Doe", "joe@gmail.com", "password"
        );


        doReturn(ResponseEntity.status(HttpStatus.CONFLICT)
                .body("User with email already exists")).when(registrationService)
                .register(any(RegistrationRequest.class));

        String jsonUserRequest = JsonObjectConverter.objectToJson(registrationRequest);

        //When

        assert jsonUserRequest != null;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserRequest))
                .andExpect(status().isConflict())
                .andReturn();
        //Then

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("User with email already exists", response);

    }


}
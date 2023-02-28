package com.example.secretroom.customer;

import com.example.secretroom.authentication.registration.RegistrationRequest;
import com.example.secretroom.authentication.registration.RegistrationService;
import com.example.secretroom.customer.registration.CustomerRegistrationRequest;
import com.example.secretroom.utils.JsonObjectConverter;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void itShouldCreateCustomer() throws Exception {
        //Given
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
                "Slava",
                "Joe",
                "zapolski@gmail.com",
                "0798888844"

        );

        ResponseEntity<String> responseEntity = ResponseEntity.ok("Customer successfully saved");
        doReturn(responseEntity).when(customerService).saveCustomer(any());

        //When

        String jsonCustomerRequest = JsonObjectConverter.objectToJson(customerRegistrationRequest);

        assert jsonCustomerRequest != null;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCustomerRequest))
                .andExpect(status().isOk())
                .andReturn();
        //Then

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals(responseEntity.getBody(), response);
    }

    @Test
    void itShouldGetCustomerThrowsException() throws Exception {
        //Given
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
                "Slava",
                "Joe",
                "zapolski@gmail.com",
                "0798888844"

        );

        //When
        ResponseEntity<String> responseEntity = ResponseEntity.ok(
                String.format("Customer with email %s already exists", customerRegistrationRequest.email())
        );


        doReturn(ResponseEntity.status(HttpStatus.CONFLICT)
                .body(responseEntity.getBody())).when(customerService)
                .saveCustomer(any(Customer.class));

        // Then

        String jsonCustomerRequest = JsonObjectConverter.objectToJson(customerRegistrationRequest);

        assert jsonCustomerRequest != null;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCustomerRequest))
                .andExpect(status().isConflict())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals(responseEntity.getBody(), response);

    }
}
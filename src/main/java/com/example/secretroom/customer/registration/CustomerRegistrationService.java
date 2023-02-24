package com.example.secretroom.customer.registration;

import com.example.secretroom.customer.Customer;
import com.example.secretroom.customer.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService {

    private CustomerService customerService;

    public CustomerRegistrationService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ResponseEntity<String> registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        //Todo validation register

        Customer customer = Customer.builder().
                name(customerRegistrationRequest.firstname())
                .lastname(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .phone(customerRegistrationRequest.phone())
                .build();

       return customerService.saveCustomer(customer);


    }
}

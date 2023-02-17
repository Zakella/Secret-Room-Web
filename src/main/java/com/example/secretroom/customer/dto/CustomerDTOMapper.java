package com.example.secretroom.customer.dto;

import com.example.secretroom.customer.Customer;

import java.util.function.Function;

public class CustomerDTOMapper implements Function <Customer, CustomerDTO> {
    @Override
    public CustomerDTO apply(Customer customer) {
        return new CustomerDTO(customer);
    }
}

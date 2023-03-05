package com.secretroom.customer.dto;

import com.secretroom.customer.Customer;
import lombok.AllArgsConstructor;


public record CustomerDTO(

        Long id,
        String name,
        String lastname,
        String phone,
        String email) {

}

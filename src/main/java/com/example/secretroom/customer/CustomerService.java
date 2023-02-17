package com.example.secretroom.customer;

import exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }


    public Customer getCustomer(Long id) {
        return customerRepository.
                findById(id)
                .orElseThrow(() ->
                {
                    return new NotFoundException("Customer width id " + id + " not found");
                });
    }


}

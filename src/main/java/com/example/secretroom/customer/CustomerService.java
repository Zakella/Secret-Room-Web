package com.example.secretroom.customer;

import com.example.secretroom.utils.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public ResponseEntity<String> saveCustomer(Customer customer) {

        String email = customer.getEmail();
        boolean customerExist = customerRepository.findByEmail(email).isPresent();
        if (customerExist){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(String.format("Customer with email %s already exists", email));
        }

        customerRepository.save(customer);
        return  ResponseEntity.ok("Customer successfully saved");
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

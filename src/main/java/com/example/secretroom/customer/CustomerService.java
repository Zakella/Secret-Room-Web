package com.example.secretroom.customer;

import com.example.secretroom.customer.dto.CustomerDTO;
import com.example.secretroom.customer.dto.CustomerDTOMapper;
import com.example.secretroom.utils.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDTOMapper customerDTOMapper;

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



    public CustomerDTO getCustomer(Long id) {
        return customerRepository.
                findById(id).
                map(customerDTOMapper)
                .orElseThrow(() ->
                {
                    return new NotFoundException("Customer width id " + id + " not found");
                });
    }


}

package com.secretroom.customer;

import com.secretroom.customer.dto.CustomerDTO;
import com.secretroom.customer.registration.CustomerRegistrationRequest;
import com.secretroom.customer.registration.CustomerRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(path = "api/v1/customers")
@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerRegistrationService customerRegistrationService;
    private final CustomerService customerService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {

        return customerRegistrationService.registerCustomer(customerRegistrationRequest);
    }

    @GetMapping(path = "{id}")
    CustomerDTO getCustomer(@PathVariable("id") Long id) {
        return this.customerService.getCustomer(id);
    }

    @PutMapping()
    public void updateCustomer(@RequestBody Customer customer) {
        System.out.println("Updating customer");
    }

}

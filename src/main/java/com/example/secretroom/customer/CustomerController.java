package com.example.secretroom.customer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(path = "api/v1/customers")
@RestController
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostMapping()
    public void createCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);

    }

    @GetMapping(path = "{id}")
    Customer getCustomer(@PathVariable("id") Long id) {
        return this.customerService.getCustomer(id);
    }

    @PutMapping()
    public void updateCustomer(@RequestBody Customer customer) {
        System.out.println("Updating customer");
    }

}

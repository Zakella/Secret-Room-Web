package com.example.secretroom.customer;

import com.example.secretroom.customer.dto.CustomerDTO;
import com.example.secretroom.customer.dto.CustomerDTOMapper;
import com.example.secretroom.utils.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;


    CustomerDTOMapper customerDTOMapper;

    CustomerService underTest;




    @BeforeEach
    void setUp() {
          customerDTOMapper = new CustomerDTOMapper();
          underTest = new CustomerService(customerRepository, customerDTOMapper);

    }

    @Test
    void itShouldSaveCustomer() {
        //Given
        Customer customer = new Customer
                (1L , "Joe", "Doe", "joe@gmail.com", "0798798889");
        //When

        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.empty());

        //Then
        ResponseEntity<String> responseEntity = underTest.saveCustomer(customer);

        assertEquals(responseEntity.getStatusCode() , HttpStatus.OK);
        assertEquals("Customer successfully saved", responseEntity.getBody());
        verify(customerRepository, times(1)).findByEmail(customer.getEmail());
        verify(customerRepository, times(1)).save(customer);
        verifyNoMoreInteractions(customerRepository);

    }

    @Test
    void itShouldThrowExceptionCustomer() {
        //Given
        Customer customer = new Customer
                (1L , "Joe", "Doe", "joegmail.com", "0798798889");
        //When

        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.of(customer));

        //Then
        ResponseEntity<String> responseEntity = underTest.saveCustomer(customer);
        assertEquals(responseEntity.getStatusCode() , HttpStatus.CONFLICT);
        assertEquals(String.format("Customer with email %s already exists", customer.getEmail()), responseEntity.getBody());

    }

    @Test
    void itShouldGetCustomer() {
        //Given
        Customer customer = new Customer
                (1L , "Joe", "Doe", "joe@gmail.com", "0798798889");

        CustomerDTO expected = new CustomerDTO(1L, "Joe", "Doe", "0798798889","joe@gmail.com");


        when(customerRepository.findById(customer.getId()))
                .thenReturn(Optional.of(customer));

        //When

        CustomerDTO actual = underTest.getCustomer(customer.getId());

        //Then

        assertEquals(expected.id(),actual.id());
        assertEquals(expected.email(),actual.email());
        assertEquals(expected.name(),actual.name());
        assertEquals(expected.lastname(),actual.lastname());
        assertEquals(expected.phone(),actual.phone());


        verify(customerRepository, times(1)).findById(customer.getId());
        verifyNoMoreInteractions(customerRepository);



    }


    @Test
    void getCustomerThrowsNotFoundException() {
        //Given
        Customer customer = new Customer
                (1L , "Joe", "Doe", "joe@gmail.com", "0798798889");


        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        //When

        //Then

        NotFoundException notFoundException =
                assertThrows(NotFoundException.class,
                        () -> underTest.getCustomer(customer.getId())
                );

        assertEquals(notFoundException.getMessage(),
                "Customer width id " + customer.getId() + " not found");


    }
}
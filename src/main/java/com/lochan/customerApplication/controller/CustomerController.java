package com.lochan.customerApplication.controller;

import com.lochan.customerApplication.dto.CustomerFilterDTO;
import com.lochan.customerApplication.dto.ResponseDTO;
import com.lochan.customerApplication.model.Customer;
import com.lochan.customerApplication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api")
public class CustomerController {

    // - Create a customer
    // - Update a customer
    // - Get a list of customer (API with pagination sorting and searching )
    // - Get a single customer based on ID
    // - Delete a customer

    @Autowired
    CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        return this.customerService.createCustomer(customer);
    }
    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable Integer id){
        return this.customerService.updateCustomerById(id);
    }
    @GetMapping("/customers")
    public ResponseEntity<ResponseDTO> getAllCustomer(CustomerFilterDTO customerFilterDTO) {
        Pageable pageable = (Pageable) PageRequest.of(customerFilterDTO.getPage(), customerFilterDTO.getSize());
        Page<Customer> customerPage = customerService.getAllCustomer(customerFilterDTO,pageable);
        return new ResponseEntity<>(new ResponseDTO(HttpStatus.OK, null, customerPage),
                new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id){
        return this.customerService.getCustomerById(id);
    }
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable Integer id){
        return this.customerService.deleteCustomerById(id);
    }

}

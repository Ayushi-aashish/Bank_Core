package com.first.bank.Controller;



import com.first.bank.Entity.Customer;
import com.first.bank.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/customers")
    public class CustomerController {

        private final CustomerService customerService;

        public CustomerController(CustomerService customerService) {
            this.customerService = customerService;
        }

        @PostMapping
        public Customer createCustomer(
                @RequestBody Customer customer) {

            return customerService.createCustomer(customer);
        }

        @GetMapping("/{id}")
        public Customer getCustomer(
                @PathVariable Long id) {

            return customerService.getCustomer(id);
        }

        @GetMapping
        public List<Customer> getAllCustomers() {
            return customerService.getAllCustomers();
        }
    }


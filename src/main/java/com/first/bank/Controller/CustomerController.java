package com.first.bank.Controller;



import com.first.bank.Dto.CustomerResponse;
import com.first.bank.Entity.Customer;
import com.first.bank.Service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
        public Page<CustomerResponse> getAllCustomers(
                @PageableDefault(
                        size = 10,
                        sort = "id",
                        direction = Sort.Direction.ASC
                ) Pageable pageable) {

            return customerService.getAllCustomers(pageable);
        }
    }


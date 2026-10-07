package com.first.bank.Service;

import com.first.bank.Entity.Customer;
import com.first.bank.Repository.CustomerRepo;
import com.first.bank.Exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepository;

    public CustomerService(CustomerRepo customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}

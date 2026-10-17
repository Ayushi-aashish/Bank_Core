package com.first.bank.Service;

import com.first.bank.Dto.CustomerResponse;
import com.first.bank.Entity.Customer;
import com.first.bank.Repository.CustomerRepo;
import com.first.bank.Exception.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<CustomerResponse> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail()
                ));
    }

}

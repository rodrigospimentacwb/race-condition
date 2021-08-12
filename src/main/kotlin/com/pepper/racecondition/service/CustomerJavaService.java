package com.pepper.racecondition.service;

import com.pepper.racecondition.entity.Customer;
import com.pepper.racecondition.redisson.lock.java.LockManager;
import com.pepper.racecondition.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerJavaService {

    @Autowired
    @Qualifier("LockManagerJava")
    LockManager lockManagerJava;

    @Autowired
    CustomerRepository customerRepository;

    public Customer createWithoutLock (Customer customer) {
        Optional<Customer> customerFound = customerRepository.findByNameAndSecondName(customer.getName(),customer.getSecondName());
        try {
            Thread.sleep(300); // Simular lentidão na consulta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(customerFound.isPresent()){
            return customerFound.get();
        }
        return customerRepository.save(customer);
    }

    public Customer createWithLock (Customer customer) {

        String key = "${customer.name}-${customer.secondName}";

        return lockManagerJava.applyLock(
                key,
                () -> {
                    Optional<Customer> customerFound = customerRepository.findByNameAndSecondName(customer.getName(),customer.getSecondName());
                    try {
                        Thread.sleep(300); // Simular lentidão na consulta
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(customerFound.isPresent()){
                        return customerFound.get();
                    }
                    return customerRepository.save(customer);
                }
        );
    }
}

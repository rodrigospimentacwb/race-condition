package com.example.demo.model.repository;

import java.util.Optional;

import com.example.demo.model.entity.Payment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    Optional<Payment> findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);
}

package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.entity.Payment;
import com.example.demo.model.repository.PaymentRepository;
import com.example.demo.resource.request.PaymentRequest;
import com.example.demo.service.component.LockManager;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class PaymentService {

    @Resource
    LockManager lockManager;

    @Resource
    PaymentRepository repository;

    public Payment createPaymentWithoutLock (PaymentRequest request, String idempotencyKey) {
        return persistPayment(request, idempotencyKey);
    }

    public Payment createPaymentWithLock (PaymentRequest request, String idempotencyKey) {
        return lockManager.applyLock(idempotencyKey, () -> persistPayment(request, idempotencyKey));
    }

    private Payment persistPayment(PaymentRequest request, String idempotencyKey) {
        Optional<Payment> paymentFound = repository.findByIdempotencyKey(idempotencyKey);
        try {
            Thread.sleep(300); // Simular lentidão na consulta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return paymentFound.orElseGet(() -> repository.save(fillPayment(request, idempotencyKey)));
    }
    
    public Optional<Payment> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    private Payment fillPayment(PaymentRequest request, String idempotencyKey) {
        return new Payment(request.getAmount(), request.getProductName(), idempotencyKey);
    }
}

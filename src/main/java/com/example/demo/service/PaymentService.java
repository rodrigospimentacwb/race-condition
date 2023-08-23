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

    public Payment createPaymentWithoutLock (PaymentRequest request) {
        return persistPayment(request);
    }

    public Payment createPaymentWithLock (PaymentRequest request) {

        String key = request.getProductName() + request.getAmount();

        return lockManager.applyLock(key, () -> { return persistPayment(request); });
    }

    private Payment persistPayment(PaymentRequest request) {
        Optional<Payment> paymentFound = repository.findByProductName(request.getProductName());
        try {
            Thread.sleep(300); // Simular lentidão na consulta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(paymentFound.isPresent()) {
            return paymentFound.get();
        }
        return repository.save(fillPayment(request));
    }

    private Payment fillPayment(PaymentRequest request) {
        return new Payment(request.getAmount(), request.getProductName());
    }
}

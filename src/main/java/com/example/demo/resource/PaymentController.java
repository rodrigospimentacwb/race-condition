package com.example.demo.resource;

import com.example.demo.resource.request.PaymentRequest;
import com.example.demo.service.PaymentService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
public class PaymentController {

    @Resource
    PaymentService service;

    @PostMapping(value = "/pay", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pay(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(service.createPaymentWithLock(request));
    }
}

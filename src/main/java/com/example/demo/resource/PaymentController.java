package com.example.demo.resource;

import com.example.demo.resource.request.PaymentRequest;
import com.example.demo.service.PaymentService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
public class PaymentController {

    @Resource
    PaymentService service;

    @PostMapping(value = "/pay/without-lock", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> payWithoutLock(@RequestBody PaymentRequest request, @RequestHeader(name = "X-Idempotency-Key") String idempotencyKey) {
        return ResponseEntity.ok(service.createPaymentWithoutLock(request, idempotencyKey));
    }

    @PostMapping(value = "/pay/with-lock", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> payWithLock(@RequestBody PaymentRequest request, @RequestHeader(name = "X-Idempotency-Key") String idempotencyKey) {
        return ResponseEntity.ok(service.createPaymentWithLock(request, idempotencyKey));
    }

    @GetMapping(value = "/pay/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPayment(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/pay/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        if (service.existsById(id)) {
            service.deleteById(id);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

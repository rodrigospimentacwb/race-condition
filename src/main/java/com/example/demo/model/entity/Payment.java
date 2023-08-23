package com.example.demo.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String productName;
    private String idempotencyKey;

    public Payment(BigDecimal amount, String productName, String idempotencyKey) {
        this.amount = amount;
        this.productName = productName;
        this.idempotencyKey = idempotencyKey;
    }
}
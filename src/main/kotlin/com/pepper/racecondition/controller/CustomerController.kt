package com.pepper.racecondition.controller

import com.pepper.racecondition.entity.Customer
import com.pepper.racecondition.service.CustomerJavaService
import com.pepper.racecondition.service.CustomerKotlinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController {

    @Autowired
    lateinit var customerKotlinService:CustomerKotlinService

    @Autowired
    lateinit var customerJavaService:CustomerJavaService

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        value = ["/api/kotlin/customers"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createKotlinCustomerWithoutLock(@RequestBody customer: Customer): Customer {
        return customerKotlinService.createWithoutLock(customer)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        value = ["/api/kotlin/customers/lock"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createKotlinCustomerWithLock(@RequestBody customer: Customer): Customer {
        return customerKotlinService.createWithLock(customer)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        value = ["/api/java/customers"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createJavaCustomerWithoutLock(@RequestBody customer: Customer): Customer {
        return customerJavaService.createWithoutLock(customer)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        value = ["/api/java/customers/lock"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createJavaCustomerWithLock(@RequestBody customer: Customer): Customer {
        return customerJavaService.createWithLock(customer)
    }
}
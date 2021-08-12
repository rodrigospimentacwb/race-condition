package com.pepper.racecondition.service

import com.pepper.racecondition.entity.Customer
import com.pepper.racecondition.redisson.lock.kotlin.LockManager
import com.pepper.racecondition.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CustomerKotlinService(val customerRepository: CustomerRepository) {

    @Autowired
    @Qualifier("LockManagerKotlin")
    lateinit var lockManagerKotlin: LockManager

    fun createWithoutLock (customer:Customer): Customer{
        val customerFound = customerRepository.findByNameAndSecondName(customer.name,customer.secondName)
        Thread.sleep(300) // Simular lentidão na consulta
        if(customerFound.isPresent){
            return customerFound.get()
        }
        return customerRepository.save(customer)
    }

    fun createWithLock (customer:Customer): Customer{

        var key:String = "${customer.name}-${customer.secondName}"

        return lockManagerKotlin.applyLock(key) {
            val customerFound = customerRepository.findByNameAndSecondName(customer.name,customer.secondName)
            Thread.sleep(300) // Simular lentidão na consulta
            if(customerFound.isPresent){
                return@applyLock customerFound.get()
            }
            return@applyLock customerRepository.save(customer)
        }
    }
}
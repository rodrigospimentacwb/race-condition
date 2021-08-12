package com.pepper.racecondition.repository

import com.pepper.racecondition.entity.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository:CrudRepository<Customer,Integer> {

    fun findByNameAndSecondName(@Param("name") name:String, @Param("secondName") secondName:String):Optional<Customer>
}
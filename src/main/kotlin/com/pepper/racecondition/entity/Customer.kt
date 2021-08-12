package com.pepper.racecondition.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "customer")
class Customer(
    @Id
    @SequenceGenerator(name="customer_id_seq",
        sequenceName="customer_id_seq",
        allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator="customer_id_seq")
    @Column(name = "id", updatable=false)
    var id:Integer? = null,
    @Column(name = "name")
    val name:String = "",
    @Column(name = "secondName")
    val secondName:String = "",
    @Column(name = "age")
    val age:Integer? = null
)
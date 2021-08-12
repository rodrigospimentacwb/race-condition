package com.pepper.racecondition.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(
    basePackages = [
        "com.pepper.racecondition.entity"
    ]
)
@EnableJpaRepositories(
    basePackages = [
        "com.pepper.racecondition.repository"
    ]
)
open class ApplicationConfiguration {
}
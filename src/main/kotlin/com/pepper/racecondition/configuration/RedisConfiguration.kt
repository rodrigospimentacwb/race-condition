package com.pepper.racecondition.configuration

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfiguration {

    @Bean
    @Qualifier("redissonClient")
    fun redissonSingleClient(@Value("\${spring.redis.host}") host: String,
                             @Value("\${spring.redis.port}") port: Int,
                             @Value("\${spring.redis.password}") password: String?,
                             @Value("\${redis.timeout.milliseconds}") connectionTimeOut: Int?
    ): RedissonClient {
        val nodeAddress = "$host:$port"
        val config = Config()
        val singleServerConfig = config.useSingleServer()
        if (password?.isBlank() == true) {
            singleServerConfig.setAddress(nodeAddress).timeout = connectionTimeOut!!
        } else {
            singleServerConfig.setAddress(nodeAddress)
                .setPassword(password)
                .setSslEnableEndpointIdentification(true).timeout = connectionTimeOut!!
        }
        return Redisson.create(config)
    }
}
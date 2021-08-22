package com.pepper.racecondition.redisson.lock.kotlin

import com.pepper.racecondition.redisson.lock.LockAcquisitionException
import com.pepper.racecondition.redisson.lock.LockInterruptException
import org.apache.logging.log4j.LogManager
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component("LockManagerKotlin")
class LockManager(private val client: RedissonClient,
                  @Value("\${redis.lock.wait.time.seconds}") private val lockWaitTime: Long,
                  @Value("\${redis.auto.unlock.time.seconds}") private val autoUnlockTime: Long) {

    private val logger = LogManager.getLogger(this::class.java)

    @Throws(LockInterruptException::class)
    fun <T> applyLock(key: String, supplier: () -> (T)): T {

        logger.info("Locking {}...", key)
        val lockInstance = client.getLock(key)

        try {
            val locked = lockInstance.tryLock(lockWaitTime, autoUnlockTime, TimeUnit.SECONDS)
            if (!locked) {
                logger.info("{} locked.", key)
                val msg = String.format("Lock can not be getting after %s seconds", lockWaitTime)
                throw LockAcquisitionException(msg)
            }else{
                logger.info("run *****************************", key)
                return supplier()
            }
        } catch (e: InterruptedException) {
            val msg = String.format("Error locking with key [%s]", key)
            logger.error(msg, e)
            Thread.currentThread().interrupt()
            throw LockInterruptException(msg, e)
        } finally {
            logger.info("Unlocking {}...", key)
            lockInstance.unlock()
            logger.info("{} unlocked.", key)
        }
    }
}
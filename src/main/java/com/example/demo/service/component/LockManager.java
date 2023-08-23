package com.example.demo.service.component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.example.demo.service.exceptions.LockAcquisitionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("LockManager")
public class LockManager {

    static final Logger LOGGER = LogManager.getLogger(LockManager.class.getName());
    private final RedissonClient client;
    private final Long lockWaitTime;
    private final Long autoUnlockTime;

    public LockManager(RedissonClient client,
                       @Value("${redis.lock.wait.time.seconds}") Long lockWaitTime,
                       @Value("${redis.auto.unlock.time.seconds}") Long autoUnlockTime) {
        this.client = client;
        this.lockWaitTime = lockWaitTime;
        this.autoUnlockTime = autoUnlockTime;
    }

    public <T> T applyLock(String key, Supplier<T> supplier) {

        LOGGER.info("Locking {}...", key);
        RLock balance = client.getLock(key);
        try {
            boolean locked = balance.tryLock(lockWaitTime, autoUnlockTime, TimeUnit.SECONDS);
            if (!locked) {
                String msg = String.format("Lock can not be getting after %s seconds", lockWaitTime);
                throw new LockAcquisitionException(msg);
            }
            LOGGER.info("{} locked.", key);
            return supplier.get();
        } catch (InterruptedException e) {
            String msg = String.format("Error locking with key [%s]", key);
            LOGGER.error(msg, e);

            Thread.currentThread().interrupt();

            throw new RuntimeException(msg, e);
        } finally {
            if (balance.isLocked() && balance.isHeldByCurrentThread()) {
                LOGGER.info("Unlocking {}...", key);
                balance.unlock();
            }
            LOGGER.info("{} unlocked.", key);
        }
    }
}

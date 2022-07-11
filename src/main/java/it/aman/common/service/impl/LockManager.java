package it.aman.common.service.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import it.aman.common.service.ILockManager;
import it.aman.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockManager implements ILockManager {

    private final LockRegistry lockRegistry;
    
    private ThreadLocal<Lock> lockTl = new ThreadLocal<>();
    
    private ThreadLocal<String> lockKeyTl = new ThreadLocal<>();

    @Override
    public boolean acquireLock(final String lockKey) {
        try {
            if(StringUtils.isBlank(lockKey)) {
                log.error("Lock key can not be null.");
                return false;
            }
            lockKeyTl.set(lockKey);
            log.info("Aquiring lock for key: {}", lockKey);
            Lock lk = lockRegistry.obtain(lockKey);
            if (lk.tryLock(1500, TimeUnit.MILLISECONDS)) {
                lockTl.set(lk);
                return true;
            }
        } catch (InterruptedException e) {
            log.error("Unable to lock with key: {}.", lockKey);
            Thread.currentThread().interrupt(); //?
        }
        return false;
    }

    @Override
    public boolean releaseLock() {
        if (lockTl == null || lockTl.get() == null) {
            log.error("Unable to release lock, not yet aquired.");
            return false;
        }
        try {
            lockTl.get().unlock();
            log.info("Released lock for: {}", lockKeyTl.get());
        } catch (Exception e) {
            log.error("Error unlocking: {}", e);
        } finally {
            lockTl.remove(); // prevent memory leak
            lockKeyTl.remove();
        }
        return true;
    }

    @Override
    public boolean isLockAcquired() {
        // TODO
        return false;
    }
}

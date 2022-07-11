package it.aman.common.service.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import it.aman.common.service.ILockManager;
import it.aman.common.util.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockManager implements ILockManager {

    private final LockRegistry lockRegistry;
    
    private ThreadLocal<Lock> lock = new ThreadLocal<>();

    @Override
    public boolean acquireLock() {
        String lockKey = "";
        try {
            lockKey = GeneralUtils.getLockKey();
            log.info("Aquiring lock for key: {}", lockKey);
            Lock lk = lockRegistry.obtain(lockKey);
            if (lk.tryLock(1500, TimeUnit.MILLISECONDS)) {
                lock.set(lk);
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
        if (lock == null || lock.get() == null) {
            log.error("Unable to release lock, not yet aquired.");
            return false;
        }
        try {
            lock.get().unlock();
        } catch (Exception e) {
            log.error("Error unlocking: {}", e);
        } finally {
            lock.remove(); // prevent memory leak
        }
        return true;
    }

    @Override
    public boolean isLockAquired() {
        // TODO
        return false;
    }
}

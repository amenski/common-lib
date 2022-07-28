package it.aman.common.service.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Component;

import it.aman.common.service.IPessimistLockManager;
import it.aman.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Pessimist lock impl
 * 
 * Locks data from the start to the end of a transaction. Should explicitely be
 * used when necessary. Uses spring integrations' {@code LockRepository}
 * and {@code LockRegistry} <br>
 * <br>
 * 
 * e.g.<br>
 * 
 * <pre>
 * try {
 *     Employee empl = employeeRepository.findActiveEmployeeById(employeeId).orElseThrow(ERPExceptionEnums.EMPLOYEE_NOT_FOUND);
 *     if (lockManager.acquireLock(String.format("empl-%s", empl.getId()))) {
 *         employeeRepository.save(empl);
 *         return true;
 *     }
 *     return false;
 * } catch (Exception e) {
 *     throw e;
 * } finally {
 *     lockManager.releaseLock();
 * }
 * </pre>
 * 
 * @author Aman
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PessimistLockManager implements IPessimistLockManager {

    private final LockRegistry lockRegistry;

    private ThreadLocal<Lock> lockTl = new ThreadLocal<>();

    private ThreadLocal<String> lockKeyTl = new ThreadLocal<>();

    @Override
    public boolean acquireLock(final String lockKey) {
        try {
            if (StringUtils.isBlank(lockKey)) {
                log.error("Lock key can not be null.");
                return false;
            }
            lockKeyTl.set(lockKey);
            Lock lk = lockRegistry.obtain(lockKey);
            if (lk.tryLock(1500, TimeUnit.MILLISECONDS)) {
                lockTl.set(lk);
                log.info("Acquired lock for key: {}", lockKey);
                return true;
            }
        } catch (InterruptedException e) {
            log.error("Unable to lock with key: {}.", lockKey);
            Thread.currentThread().interrupt(); // ?
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

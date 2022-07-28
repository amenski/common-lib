package it.aman.common.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import it.aman.common.service.IOptimisticLockManager;

@Component
public class OptimisticLockManagerImpl implements IOptimisticLockManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> void lock(T entity) {
            entityManager.lock(entity, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }
}

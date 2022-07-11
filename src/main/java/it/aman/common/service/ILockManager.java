package it.aman.common.service;

public interface ILockManager {

    public abstract boolean acquireLock(final String key);

    public abstract boolean releaseLock();

    public abstract boolean isLockAcquired();
}

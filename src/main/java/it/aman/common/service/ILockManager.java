package it.aman.common.service;

public interface ILockManager {

    public abstract boolean acquireLock();

    public abstract boolean releaseLock();

    public abstract boolean isLockAcquired();
}

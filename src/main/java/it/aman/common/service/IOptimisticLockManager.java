package it.aman.common.service;

public interface IOptimisticLockManager {

    public abstract <T> void lock(T entity);
}

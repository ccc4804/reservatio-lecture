package com.apply.course.util.lock;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ThreadLockUtil {

    private final ConcurrentHashMap<Long, Lock> lockMap = new ConcurrentHashMap<>();

    public boolean lock(Long lockKey) throws InterruptedException {
        Lock lock = getLockForCustomer(lockKey);

        // 데드락 방지를 위한 1초 대기 시간 추가.
        return lock.tryLock(1, TimeUnit.SECONDS);
    }

    public void unlock(Long lockKey) {
        Lock lock = getLockForCustomer(lockKey);

        // 현재 스레드가 락을 소유하고 있는 경우만 해제
        if (lock instanceof ReentrantLock reentrantLock && reentrantLock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    private Lock getLockForCustomer(Long lockKey) {
        return lockMap.computeIfAbsent(lockKey, id -> new ReentrantLock());
    }
}

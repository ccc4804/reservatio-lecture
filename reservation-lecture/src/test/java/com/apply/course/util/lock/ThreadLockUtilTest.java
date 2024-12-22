package com.apply.course.util.lock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class ThreadLockUtilTest {

    @Autowired
    private ThreadLockUtil threadLockUtil;

    @Test
    @DisplayName("동시성 락 테스트 - 한 번에 하나의 스레드만 락을 획득해야 함")
    void testConcurrentLocking() throws InterruptedException, ExecutionException {
        Long lockKey = 3L;

        // CompletableFuture로 두 개의 비동기 스레드 생성
        CompletableFuture<Boolean> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                return threadLockUtil.lock(lockKey); // 첫 번째 스레드에서 락 시도
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        CompletableFuture<Boolean> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                return threadLockUtil.lock(lockKey); // 두 번째 스레드에서 락 시도
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        // 각각의 결과를 기다림
        boolean isLocked1 = future1.get();
        boolean isLocked2 = future2.get();

        // 검증: 한 스레드만 락을 획득해야 함
        assertTrue(isLocked1 || isLocked2, "적어도 하나의 스레드는 락을 획득해야 함");
        assertFalse(isLocked1 && isLocked2, "동시에 두 스레드가 락을 획득해서는 안 됨");

        // 락 해제 (획득한 스레드만 해제)
        if (isLocked1) {
            threadLockUtil.unlock(lockKey);
        }
        if (isLocked2) {
            threadLockUtil.unlock(lockKey);
        }
    }

    @Test
    @DisplayName("락 해제 후 다른 스레드가 락을 획득해야 함")
    void testUnlockAndRelock() throws InterruptedException, ExecutionException {
        Long lockKey = 5L;

        // 첫 번째 스레드가 락을 획득
        boolean isLocked1 = threadLockUtil.lock(lockKey);
        assertTrue(isLocked1, "첫 번째 스레드가 락을 획득해야 함");

        // 두 번째 스레드가 락을 기다리며 실패해야 함
        CompletableFuture<Boolean> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                return threadLockUtil.lock(lockKey);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        // 현재 락 해제
        threadLockUtil.unlock(lockKey);

        // 두 번째 스레드가 락을 획득
        boolean isLocked2 = future2.get();
        assertTrue(isLocked2, "첫 번째 스레드가 락을 해제한 후, 두 번째 스레드가 락을 획득해야 함");

        // 두 번째 스레드의 락 해제
        threadLockUtil.unlock(lockKey);
    }

    @Test
    @DisplayName("동일하지 않은 키로 여러 스레드가 동시에 락을 획득해야 함")
    void testDifferentLockKeys() throws ExecutionException, InterruptedException {
        Long lockKey1 = 1L;
        Long lockKey2 = 2L;

        CompletableFuture<Boolean> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                return threadLockUtil.lock(lockKey1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        CompletableFuture<Boolean> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                return threadLockUtil.lock(lockKey2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        boolean isLocked1 = future1.get();
        boolean isLocked2 = future2.get();

        // 두 스레드가 서로 다른 키로 락을 획득
        assertTrue(isLocked1, "첫 번째 스레드가 lockKey1에 대해 락을 획득해야 함");
        assertTrue(isLocked2, "두 번째 스레드가 lockKey2에 대해 락을 획득해야 함");

        // 락 해제 (락을 획득한 경우에만 해제)
        if (isLocked1) {
            threadLockUtil.unlock(lockKey1);
        }
        if (isLocked2) {
            threadLockUtil.unlock(lockKey2);
        }
    }
}

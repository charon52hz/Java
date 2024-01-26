package monitor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockTestTimeout {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread( () -> {
            log.debug("尝试获得锁..");
            try {
                if ( !lock.tryLock(2, TimeUnit.SECONDS)){
                    log.debug("超时未获得锁.");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("被打断，未获得锁");
                return;
            }
            try {
                log.debug("获得到锁，执行临界区代码");
            } finally {
                lock.unlock();
            }
        },"t1");

        lock.lock();
        t1.start();
        Thread.sleep(3000);
//        t1.interrupt();
        lock.unlock();

    }
}

package monitor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "testInterruptibly")
public class ReentrantLockTestInterruptibly {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                log.debug("尝试获取一个可打断的锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得到锁,直接返回");
                return;
            }

            log.debug("获取到锁");
            lock.unlock();

        },"t1");


        //主线程加锁
        lock.lock();
        t1.start();

        Thread.sleep(2000);

        t1.interrupt();
    }
}

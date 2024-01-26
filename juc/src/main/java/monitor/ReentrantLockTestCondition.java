package monitor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "testCondition")
public class ReentrantLockTestCondition {
    static ReentrantLock ROOM = new ReentrantLock();

    static Condition waitSet1 = ROOM.newCondition();
    static Condition waitSet2 = ROOM.newCondition();

    static boolean flag1 = false;
    static boolean flag2 = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            ROOM.lock();
            try {
                while (!flag1){
                    try {
                        log.debug("条件不满足，继续等待..");
                        waitSet1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("条件满足，执行临界区代码");
            } finally {
                ROOM.unlock();
            }
        },"t1").start();


        new Thread(()->{
            ROOM.lock();
            try {
                while (!flag2){
                    try {
                        log.debug("条件不满足，继续等待..");
                        waitSet2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("条件满足，执行临界区代码");
            } finally {
                ROOM.unlock();
            }
        },"t2").start();

        Thread.sleep(2000);

        new Thread(()->{
            ROOM.lock();
            try {
                log.debug("flag1置为true");
                flag1 = true;
                waitSet1.signal();
            } finally {
                ROOM.unlock();
            }
        },"c1").start();


        new Thread(()->{
            ROOM.lock();
            try {
                log.debug("flag2置为true");
                flag2 = true;
                waitSet2.signal();
            } finally {
                ROOM.unlock();
            }
        },"c2").start();
    }

}


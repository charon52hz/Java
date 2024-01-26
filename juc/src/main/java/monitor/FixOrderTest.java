package monitor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j(topic = "fixedOrder")
public class FixOrderTest {
    static Thread t1,t2,t3;
    public static void main(String[] args) {
        //wait/notify
//        monitor.WaitNotify obj = new monitor.WaitNotify(1, 5);
//
//        new Thread(()->{obj.print("a",1,2);},"t1").start();
//        new Thread(()->{obj.print("b",2,3);},"t2").start();
//        new Thread(()->{obj.print("c",3,1);},"t3").start();

        //await/signal
//        monitor.AwaitSignal awaitSignal = new monitor.AwaitSignal(5);
//        Condition a = awaitSignal.newCondition();
//        Condition b = awaitSignal.newCondition();
//        Condition c = awaitSignal.newCondition();
//
//        new Thread(()->{awaitSignal.print("a",a,b);},"t1").start();
//        new Thread(()->{awaitSignal.print("b",b,c);},"t2").start();
//        new Thread(()->{awaitSignal.print("c",c,a);},"t3").start();
//
//        //启动
//        awaitSignal.lock();
//        try {
//            a.signal();
//        } finally {
//            awaitSignal.unlock();
//        }
        //park/unpark


        ParkUnpark parkUnpark = new ParkUnpark(5);
        t1 = new Thread(()->{parkUnpark.print("a",t2);},"t1");
        t2 = new Thread(()->{parkUnpark.print("b",t3);},"t2");
        t3 = new Thread(()->{parkUnpark.print("c",t1);},"t3");

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);

    }
}

@Slf4j(topic = "waitNotify")
class WaitNotify{
    private int flag;
    private int loopNum;

    public WaitNotify(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public void print(String str,int flag,int nextFlag){
        for (int i = 0; i < loopNum; i++) {
            synchronized (this) {
                while (flag != this.flag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info(str);
                this.flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

@Slf4j(topic = "awaitSignal")
class AwaitSignal extends ReentrantLock {
    private int loopNum;

    public AwaitSignal(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String str, Condition cur ,Condition next){
        for (int i = 0; i < loopNum; i++) {
            lock();
            try {
                cur.await();
                log.info(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}

@Slf4j(topic = "parkUnpark")
class ParkUnpark{
    private int loopNum;

    public ParkUnpark(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String str,Thread next){
        for (int i = 0; i < loopNum; i++) {
            LockSupport.park();
            log.info(str);
            LockSupport.unpark(next);
        }

    }
}

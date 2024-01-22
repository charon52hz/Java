import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


@Slf4j
public class Test {
    public static void main(String[] args) {
        //1. Thread

        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println("线程1 run....");
                log.info("线程1");
            }
        };
            //lambda写法
        Thread t2 = new Thread(() -> {
            System.out.println("线程2 run....");
            log.info("线程2");
        });
        t1.start();
        t2.start();

        //2. Runnable
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程3 run...");
                log.info("线程3");
            }
        };
            //lambda
        Runnable r2 = () -> {
            System.out.println("线程4 run...");
            log.info("线程4");
        };
        new Thread(r1).start();
        new Thread(r2).start();

        //3. FutureTask
        FutureTask task = new FutureTask<>(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                log.info("线程5");
                Thread.sleep(2000);
                return 10;
            }
        });
        Thread t3 = new Thread(task);
        t3.start();
        try {
            Object res = task.get();
            System.out.println(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}

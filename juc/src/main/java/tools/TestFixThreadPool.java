package tools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TestFixThreadPool {
    public static void main(String[] args) {
/*        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("run......"+1);
                    }
                }
        );

        pool.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("run......"+2);
                    }
                }
        );

        pool.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("run......"+3);
                    }
                }
        );*/

        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger integer = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"myThread_t"+integer.getAndIncrement());
            }
        });

        pool.execute(()->{
            log.info("run..");
        });

        pool.execute(()->{
            log.info("run..");
        });

        pool.execute(()->{
            log.info("run..");
        });
    }
}

package tools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class TestCacheThreadPool {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();

        pool.execute(()->{log.info("run");});
        pool.execute(()->{log.info("run");});
        pool.execute(()->{log.info("run");});
        pool.execute(()->{log.info("run");});
    }
}

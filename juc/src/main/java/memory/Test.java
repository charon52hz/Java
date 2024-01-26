package memory;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

@Slf4j(topic = "test")
public class Test {
//    volatile static boolean flag = true;
    static boolean flag = true;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (flag){
            }
        },"t1").start();

        Thread.sleep(1000);
        log.info("stop");
        flag = false;
    }
}

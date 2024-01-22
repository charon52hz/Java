package src.thread;

import javax.swing.plaf.FontUIResource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) {
        MyCallable myCallable = new MyCallable();

        FutureTask futureTask = new FutureTask(myCallable);

        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            Object sum = futureTask.get();  //要获取分线程结果，主线程受阻塞
            System.out.println(sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 20; i++) {
            System.out.println(i);
            sum += i;
        }
        return sum;
    }
}

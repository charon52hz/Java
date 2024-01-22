package src.thread;

public class MyThread2 {
    public static void main(String[] args) {
        MyRunnable r1 = new MyRunnable();
        MyRunnable r2 = new MyRunnable();

        Thread t1 = new Thread(r1);
        System.out.println(t1.getPriority());
        t1.setPriority(Thread.MAX_PRIORITY);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

        new Thread("匿名内部类"){
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println(getName() + " " + i);
                }
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println(Thread.currentThread().getName()+" " + i);
                }
            }
        }).start();
    }
}

class MyRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}

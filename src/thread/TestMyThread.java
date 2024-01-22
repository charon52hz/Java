package src.thread;

public class TestMyThread {
    public static void main(String[] args) {
        MyThread thread = new MyThread("线程1");
        thread.start();

        MyThread thread1 = new MyThread("线程2");
        thread1.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("主线程正在运行" + i);
        }
    }
}
class MyThread extends Thread {
    public MyThread(String name){
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName() + "正在运行" + i);
        }
    }
}

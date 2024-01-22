package src.pattern;


public class SingletonTest {

    static Singleton3 s2 = null;
    static Singleton3 s2_1 = null;

    public static void main(String[] args) {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                s2 = Singleton3.getInstance();
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                s2_1 = Singleton3.getInstance();
            }
        };

        t1.start();
        t2.start();

        try {
            t1.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        try {
            t2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(s2);
        System.out.println(s2_1);
        System.out.println(s2 == s2_1);
    }
}

//饿汉模式
class Singleton1 {
    private Singleton1(){
    }
    private static Singleton1 singleton1 = new Singleton1();

    public static Singleton1 getInstance(){
        return singleton1;
    }
}
//懒汉模式（线程不安全）
class Singleton2 {
    private Singleton2(){
    }

    private static Singleton2 singleton2 = null;

    public static Singleton2 getInstance(){
        if (singleton2 == null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            singleton2 = new Singleton2();
        }
        return singleton2;
    }
}
//懒汉模式，线程安全
class Singleton3 {
    private Singleton3(){
    }

    private static Singleton3 singleton3= null;

    public synchronized static Singleton3 getInstance(){
        if (singleton3 == null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            singleton3 = new Singleton3();
        }
        return singleton3;
    }

    public static Singleton3 getInstance2(){
        synchronized (Singleton3.class){
            if (singleton3 == null) {
                singleton3 = new Singleton3();
            }
            return singleton3;
        }
    }

    public static Singleton3 getInstance3(){
        if (singleton3 == null){
            synchronized (Singleton3.class){
                if (singleton3 ==null){
                    singleton3 = new Singleton3();
                }
            }
        }
        return singleton3;
    }
}
//懒汉模式，线程安全（内部类实现，推荐）
class Singleton4 {
    private Singleton4(){
    }
    //内部类只有在第一次使用内部类时才加载，产生INSTANCE实例，同时又不需要加锁
    private static class inner{
        static final Singleton4 INSTANCE = new Singleton4();
    }

    public static Singleton4 getInstance(){
        return inner.INSTANCE;
    }

}



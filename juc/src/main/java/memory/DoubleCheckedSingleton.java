package memory;

public class DoubleCheckedSingleton {

}

class Singleton {
    private Singleton(){};

    private static volatile Singleton INSTANCE = null;

    public static Singleton getINSTANCE() {
        //多线程首次访问时可能会同步，之后的使用没有进入synchronized
        if (INSTANCE == null){
            synchronized (Singleton.class){
                if (INSTANCE == null){
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}

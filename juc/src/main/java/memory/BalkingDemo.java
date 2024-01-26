package memory;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "balking")
public class BalkingDemo {
    public static void main(String[] args) {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        twoPhaseTermination.start();
    }
}

@Slf4j(topic = "test")
class TwoPhaseTermination{
    private Thread monitorThread;   //监控线程

    private volatile boolean stop = false; //停止标记

    private boolean started = false;    //是否执行过方法

    public void start(){
        synchronized (this) {
            if (started) {  //犹豫模式
                return;
            }
            started = true;
        }

        monitorThread = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.info("料理后事");
                    break;
                }
                log.info("执行监控程序");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"monitor");
        monitorThread.start();
    }

    public void stop(){
        stop = true;
        monitorThread.interrupt();
    }
}

package monitor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwoPhaseTerminationDemo{
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination_volatile twoPhaseTermination = new TwoPhaseTermination_volatile();
        twoPhaseTermination.start();

        Thread.sleep(5500);

        twoPhaseTermination.stop();
    }
}
@Slf4j
class TwoPhaseTermination {
    private Thread monitor;

    public void start(){
        monitor = new Thread(()->{
           while (true){
               Thread cur = Thread.currentThread();
               if (cur.isInterrupted()){
                    log.info("处理后事");
                   break;
               }
               try {
                   Thread.sleep(2000);
                   log.info("进行监控");
               } catch (InterruptedException e) {
                   e.printStackTrace();
                   cur.interrupt();
               }
           }
        });
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }

}


@Slf4j
class TwoPhaseTermination_volatile {
    private Thread monitorThread;

    private volatile boolean stop = false;

    public void start(){
        monitorThread = new Thread(()->{
            while (true){
                if (stop){
                    log.info("处理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.info("进行监控");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        monitorThread.start();
    }

    public void stop(){
        stop = true;
        monitorThread.interrupt();
    }

}
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwoPhaseTerminationDemo{
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();

        Thread.sleep(10000);

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

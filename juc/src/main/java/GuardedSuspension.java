import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Guard")
public class GuardedSuspension {
    public static void main(String[] args) {
        GuardObject guardObject = new GuardObject();

        new Thread(()->{
            log.info("等待res结果...");
            log.info("结果为{}",guardObject.getRes());
        },"t1").start();


        new Thread(()->{
            log.info("制造结果...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardObject.setRes(12345);

            //do other work
        },"t2").start();
    }
}

class GuardObject{
    private Object res;

    public Object getRes(){
        synchronized (this){
            while (res == null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return res;
        }
    }

    public void setRes(Object result){
        synchronized (this){
            this.res = result;
            this.notifyAll();
        }
    }
}

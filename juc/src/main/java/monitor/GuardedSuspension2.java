package monitor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuardedSuspension2 {
    public static void main(String[] args) {
        GuardObject2 guardObject = new GuardObject2();

        new Thread(()->{
            log.info("等待res结果...");
            log.info("结果为{}",guardObject.getRes(2000));
        },"t1").start();


        new Thread(()->{
            log.info("制造结果...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardObject.setRes(12345);

            //do other work
        },"t2").start();
    }
}
class GuardObject2{
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

    //增加超时等待
    public Object getRes(long timeout){
        synchronized (this){
            long begin = System.currentTimeMillis();
            long passTime = 0L;
            while (res == null){
                long waitTime = timeout - passTime;
                //经历的时间超过了最大等待时间，直接退出循环，返回null
                if (waitTime <= 0){
                    break;
                }
                try {
                    this.wait(timeout - passTime);    //有可能虚假唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passTime = System.currentTimeMillis() - begin;
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

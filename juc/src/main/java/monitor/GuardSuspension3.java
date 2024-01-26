package monitor;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

@Slf4j(topic = "guardTest")
// 解耦等待和生产
public class GuardSuspension3 {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Resident().start();
        }
        Thread.sleep(3000);

        for (Integer id : MailBoxes.getIds()) {
            new PostMan(id).start();
        }
    }
}
@Slf4j(topic = "resident")
class Resident extends Thread{
    @Override
    public void run() {
        GuardObject3 object = MailBoxes.createGuardObject();
        log.info("去拿信..信封id：{}",object.getId());
        Object res = object.getRes(5000);
        log.info("收到信封，id：{}，信的内容：{}",object.getId(),res);
    }
}

@Slf4j(topic = "postman")
class PostMan extends Thread{
    private int mailId;

    public PostMan(int mailId){
        this.mailId = mailId;
    }

    @Override
    public void run() {
        GuardObject3 guardObject3 = MailBoxes.getGuardObject3(mailId);
        guardObject3.setRes("信封的id是"+mailId);
        log.info("送信，id：{}，内容：{}",guardObject3.getId(),"信封的id是"+mailId);

    }
}

class MailBoxes {
    private static Map<Integer, GuardObject3> boxes = new Hashtable<>();

    private static int id;

    private static synchronized int generateId(){   //产生唯一id
        return id++;
    }

    public static GuardObject3 createGuardObject(){ //创建
        GuardObject3 guardedObject3 = new GuardObject3(generateId());
        boxes.put(guardedObject3.getId(), guardedObject3);
        return  guardedObject3;
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }

    public static GuardObject3 getGuardObject3(int id){
        return boxes.remove(id);    //物品获取后就不再需要了
    }
}

class GuardObject3 {

    private int id;
    private Object res;

    public GuardObject3(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

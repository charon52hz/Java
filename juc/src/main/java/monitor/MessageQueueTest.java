package monitor;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j(topic = "monitor.MessageQueue")
public class MessageQueueTest {
    public static void main(String[] args) throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue(3);
        for (int i = 0; i < 5; i++) {
            int id = i;
            new Thread(()->{
                messageQueue.put(new Message(id,"值为"+id));
            },"生产者"+i).start();
        }

        Thread.sleep(2000);

        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = messageQueue.get();
//                log.info("消费：{}",message.toString());
            }

        },"消费者").start();
    }
}

@Slf4j(topic = "monitor.MessageQueue")
class MessageQueue {
    private LinkedList<Message> list = new LinkedList<>();  //消息队列集合
    private int capacity;    //消息队列的容量

    public MessageQueue(int capacity){
        this.capacity = capacity;
    }

    public Message get(){   //获取消息
        synchronized (list){
            while (list.isEmpty()){ //如果消息队列为空，则停止消费
                try {
                    log.info("队列已空，停止消费");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = list.removeFirst();
            log.info("消费：{}",message.toString());
            list.notifyAll();
            return message;
        }
    }

    public void put(Message message){   //生产消息
        synchronized (list){
            while (list.size() == capacity){    //如果消息队列满，则停止生产
                try {
                    log.info("队列已满，停止生产");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            list.addLast(message);
            log.info("生产消息：{}",message.toString());
            list.notifyAll();
        }
    }
}

class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "monitor.Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

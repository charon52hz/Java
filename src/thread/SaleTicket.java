package src.thread;

public class SaleTicket {
    public static void main(String[] args) {
        Windows w1 = new Windows();
        Windows w2 = new Windows();
        Windows w3 = new Windows();

        new Thread(w1).start();
        new Thread(w2).start();
        new Thread(w3).start();
    }
}

class Windows implements Runnable {
    public static int ticket = 100;
//    @Override
//    public void run() {
//        while (ticket>0){
//            System.out.println(Thread.currentThread().getName()+"卖出第"+ticket+"张票");
//            ticket--;
//        }
//    }

    public void run() {
        while (ticket>0){
            sale();
        }
    }

    public synchronized static void sale(){
        if (ticket>0){
            System.out.println(Thread.currentThread().getName() + "卖出第" + ticket + "张票");
            ticket--;
        }
    }
}

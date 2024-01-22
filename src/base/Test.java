package src.base;

//@SuppressWarnings("all")
public class Test {
    public static void main(String[] args) {
        int i = 1;
        double d = 1.0;
        System.out.println(i == d);

        Integer i1 = 1;
        Double d1 = 1.0;
        System.out.println(i1.intValue() == d1.doubleValue());

        System.out.println(true?new Integer(1):new Double(2.0));
    }
}
class Demo{
    public static void hello(){
        System.out.println("hello");
    }
}

class Someting{

}

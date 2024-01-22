package src.string;

import java.util.Objects;

public class StringTest {
    public static void main(String[] args) {
//        Person p1 = new Person();
//        p1.setName("Tom");
//
//        Person p2 = new Person();
//        p2.setName("Tom");
//
//        System.out.println(p1.name == p2.name);
//        System.out.println(Objects.equals(p1.name, p2.name));
//        System.out.println(p1.name.equals(p2.name));
//        System.out.println(p1.name == "Tom");

        String s1 = "abc";
        String s2 = new String("abc");
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        System.out.println(s1==s2);
        System.out.println(s1.equals(s2));

    }
}

class Person{
    String name;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}

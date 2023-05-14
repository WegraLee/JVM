package org.fenixsoft.jvm.chapter8;

/**
 * 메서드 정적 디스패치 시연
 * 
 * @author zzm
 */
public class StaticDispatch {
    static abstract class Human {
    }

    static class Man extends Human {
    }

    static class Woman extends Human {
    }

    // 오버로딩된 메서드 1
    public void sayHello(Human guy) {
        System.out.println("Hello, guy!");
    }

    // 오버로딩된 메서드 2
    public void sayHello(Man guy) {
        System.out.println("Hello, gentleman!");
    }

    // 오버로딩된 메서드 3
    public void sayHello(Woman guy) {
        System.out.println("Hello, lady!");
    }

    public static void main(String[] args) {
        Human man = new Man(); // 정적 타입 = Human, 실제 타입 = Man
        Human woman = new Woman(); // 정적 타입 = Human, 실제 타입 = Woman
        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man); // 메서드 버전 선택 필요
        sr.sayHello(woman); // 메서드 버전 선택 필요
    }
}

package org.fenixsoft.jvm.chapter8;

/**
 * 메서드 정적 해결 예
 *
 * @author zzm
 */
public class StaticResolution {
    public static void sayHello() {
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}

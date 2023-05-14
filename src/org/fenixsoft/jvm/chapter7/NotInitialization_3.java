package org.fenixsoft.jvm.chapter7;

/**
 * 클래스 필드를 통한 수동 참조 예시 3:
 * 상수는 컴파일 과정에서 호출하는 클래스의 상수 풀에 저장된다.
 * 상수를 정의한 클래스로의 직접적인 참조가 없는 셈이므로
 * 상수를 정의한 클래스의 초기화를 촉발하지 않는다.
 **/
class ConstClass {
    static {
        System.out.println("ConstClass 초기화!");
    }

    public static final String HELLO_WORLD = "hello world";
}

/**
 * 클래스 필드를 통한 수동 참조 예시 3:
 **/
public class NotInitialization_3 {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO_WORLD);
    }
}

package org.fenixsoft.jvm.chapter8;

import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

/**
 * JSR 292 메서드 핸들 기본 사용법
 * @author zzm
 */
public class MethodHandleTest {
    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        // obj의 타입이 무엇이든 다음 문장은 println() 메서드를 문제없이 호출한다.
        getPrintlnMH(obj).invokeExact("icyfenix");
    }

    private static MethodHandle getPrintlnMH(Object reveiver) throws Throwable {
        // MethodType은 메서드의 반환값(methodType()의 첫 번째 매개변수)과 
        // 특정 매개변수(methodType() 두 번째 이후 매개변수들)를 포함하는
        // '메서드 타입'을 뜻한다.
        MethodType mt = MethodType.methodType(void.class, String.class);

        // lookup() 메서드는 MethodHandles.lookup을 뜻한다.
        // 이 문장의 기능은 주어진 클래스에서 메서드 이름, 메서드 타입, 호출 권한이 일치하는
        // 메서드 핸들을 찾는 것이다.
        // 이때 가상 메서드가 호출되기 때문에 자바 언어의 규칙에 따라 메서드의 첫 번째
        // 매개변수는 암묵적으로 메서드의 수신자, 즉 this가 가리키는 객체를 나타낸다.
        // 이 매개변수는 원래 매개변수 목록으로 전달되었지만,
        // 지금은 bindTo() 메서드가 이 기능을 제공한다.
        return lookup().findVirtual(reveiver.getClass(), "println", mt).bindTo(reveiver);
    }
}

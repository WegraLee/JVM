package org.fenixsoft.jvm.chapter7;

/**
 * 클래스 필드를 통한 수동 참조 예시 2:
 * 배열 정의에서 클래스를 참조하는 경우 클래스 초기화를 촉발하지 않는다.
 **/
public class NotInitialization_2 {
    public static void main(String[] args) {
        SuperClass[] sca = new SuperClass[10];
    }
}

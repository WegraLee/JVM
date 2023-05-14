package org.fenixsoft.jvm.chapter8;

public class DynamicDispatch {
    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Man said hello");
        }
    }

    static class Woman extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Woman said hello");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();      // 정적 타입 = Human, 실제 타입 = Man
        Human woman = new Woman();  // 정적 타입 = Human, 실제 타입 = Woman

        man.sayHello();     // Man의 메서드 호출
        woman.sayHello();   // Woman의 메서드 호출

        man = new Woman();  // 실제 타입 = Woman
        man.sayHello();     // Woman의 메서드 호출
    }
}

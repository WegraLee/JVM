package org.fenixsoft.jvm.chapter8;

/**
 * 필드는 다형성과 무관함
 * @author zzm
 */
public class FieldHasNoPolymorphic {
    static class Father {
        public int money = 1;

        public Father() {
            money = 2;
            showMeTheMoney();
        }

        public void showMeTheMoney() {
            System.out.println("I am a Father, I have $" + money);  // 필드 출력
        }
    }

    static class Son extends Father {
        public int money = 3;  // 상위 클래스의 필드와 이름이 같다.

        public Son() {
            money = 4;
            showMeTheMoney();
        }

        public void showMeTheMoney() {
            System.out.println("I am a Son, I have $" + money);  // 필드 출력
        }
    }

    public static void main(String[] args) {
        Father guy = new Son();
        System.out.println("This guy has $" + guy.money); // 필드 출력
    }
}
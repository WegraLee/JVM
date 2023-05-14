package org.fenixsoft.jvm.chapter8;

/**
 * 필드는 다형성과 무관하다.
 * @author zzm
 */
public class Dispatch {
    static class QQ {}
    static class _360 {}

    public static class Father {
        public void hardChoice(QQ arg) {
            System.out.println("Father chose a qq");
        }

        public void hardChoice(_360 arg) {
            System.out.println("Father chose a 360");
        }
    }

    public static class Son extends Father {
        public void hardChoice(QQ arg) {
            System.out.println("Son chose a qq");
        }

        public void hardChoice(_360 arg) {
            System.out.println("Son chose a 360");
        }
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }
}

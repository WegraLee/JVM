package org.fenixsoft.jvm.chapter13;

import java.util.Vector;

/**
 * Vector의 스레드 안전성 테스트
 * 
 * @author zzm
 */
public class VectorTest_1 {
    private static Vector<Integer> vector = new Vector<Integer>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i); // ❶
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) { // ❷
                        System.out.println((vector.get(i)));  // ❸
                    }
                }
            });

            removeThread.start();
            printThread.start();

            // 스레드를 너무 많이 만들지 말자. 너무 많으면 운영체제가 느려질 수 있다.
            while (Thread.activeCount() > 20);
        }
    }
}

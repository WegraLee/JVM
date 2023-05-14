package org.fenixsoft.jvm.chapter13;

import java.util.Vector;

/**
 * Vector에 스레드 안전하게 접근하려면 추가 조치가 필요함
 * 
 * @author zzm
 */
public class VectorTest_2 {
    private static Vector<Integer> vector = new Vector<Integer>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (vector) { // 동기화 블록으로 감싸 추가 조치
                        for (int i = 0; i < vector.size(); i++) {
                            vector.remove(i);
                        }
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (vector) { // 동기화 블록으로 감싸 추가 조치
                        for (int i = 0; i < vector.size(); i++) {
                            System.out.println((vector.get(i)));
                        }
                    }
                }
            });

            removeThread.start();
            printThread.start();

            // 스레드를 너무 많이 만들지 말자. 너무 많으면 운영체제가 느려질 수 있다.
            while (Thread.activeCount() > 20)
                ;
        }
    }
}

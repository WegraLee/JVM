package org.fenixsoft.jvm.chapter13;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger 클래스의 원자적 증가 연산 예시
 * 
 * @author zzm
 */
public class AtomicTest {
    // int 대신 AtomicInteger 타입 변수를 카운터로 이용
    public static AtomicInteger race = new AtomicInteger(0);

    public static void increase() {
        race.incrementAndGet(); // 원자적 증가
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 1)
            Thread.yield();

        System.out.println(race);
    }
}

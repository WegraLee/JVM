package org.fenixsoft.jvm.chapter4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author zzm
 */
public class ThreadMonitoringTest {
    /**
     * 무한 루프를 도는 스레드 생성
     */
    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)  // 17번째 줄
                    ;
            }
        }, "testBusyThread");
        thread.start();
    }

    /**
     * 락을 대기하는 스레드 생성
     */
    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");
        thread.start();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 콘솔(터미널)에서 '엔터' 키를 치면 다음 줄 실행
        br.readLine();
        createBusyThread();

        // 콘솔(터미널)에서 '엔터' 키를 치면 다음 줄 실행
        br.readLine();
        Object obj = new Object();
        createLockThread(obj);
    }
}
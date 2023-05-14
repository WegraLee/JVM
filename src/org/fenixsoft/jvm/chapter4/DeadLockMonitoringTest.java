package org.fenixsoft.jvm.chapter4;

/**
 * @author zzm
 */
public class DeadLockMonitoringTest {
    static class SynAddRunnalbe implements Runnable {
        int a, b;
        public SynAddRunnalbe(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    System.out.println(a + b);
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new SynAddRunnalbe(1, 2)).start();  // 1 + 2를 수행하는 스레드
            new Thread(new SynAddRunnalbe(2, 1)).start();  // 2 + 1을 수행하는 스레드
        }
    }
}
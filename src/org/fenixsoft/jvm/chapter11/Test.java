package org.fenixsoft.jvm.chapter11;

public class Test {
    public static final int NUM = 15000;

    public static int doubleValue(int i) {
        for(int j = 0; j < 100000; j++);  // JIT 코드 최적화 과정 시연용 '빈' 순환문
        return i * 2;
    }

    public static long calcSum() {
        long sum = 0;
        for (int i = 1; i <= 100; i++) {  // doubleValue()를 100번 호출
            sum += doubleValue(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {  // calcSum()을 15,000번 호출
            calcSum();
        }
    }
}

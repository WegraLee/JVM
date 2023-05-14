package org.fenixsoft.jvm.chapter3;

/**
 * @author zzm
 */
public class TestSerialGCAllocation {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        if (args.length == 1) {
            int methodIndex = Integer.valueOf(args[0]);
            switch (methodIndex) {
                case 1: testAllocation(); break;
                case 2: testPretenureSizeThreshold(); break;
                case 3: testTenuringThreshold(); break;
                case 4: testTenuringThreshold2(); break;
            }
        } else {
            System.out.println("사용법: java <VM 매개변수> TestSerialGCAllocation <호출할 메서드 번호(1~4)>");
            System.out.println("  메서드 번호: 1 = testAllocation()");
            System.out.println("  메서드 번호: 2 = testPretenureSizeThreshold()");
            System.out.println("  메서드 번호: 3 = testTenuringThreshold()");
            System.out.println("  메서드 번호: 4 = testTenuringThreshold2()");
        }
    }

    /**
     * 메서드 번호: 1
     * VM 매개변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc*
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];  // 마이너 GC 발행
    }

    /**
     * 메서드 번호: 2
     * VM 매개변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc* -XX:PretenureSizeThreshold=3M
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];  // 곧장 구세대에 할당
    }

    /**
     * 메서드 번호: 3
     * VM 매개변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc* -Xlog:gc+age=trace -XX:MaxTenuringThreshold=1
     * 혹은
     * VM 매개변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc* -Xlog:gc+age=trace -XX:MaxTenuringThreshold=15 -XX:TargetSurvivorRatio=80
     */
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 8];  // 구세대 이동 시기는 -XX:MaxTenuringThreshold 값이 결정
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];  // 첫 번째 GC 발생
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];  // 두 번째 GC 발생
    }
    
    /**
     * 메서드 번호: 4
     * VM 매개변수: -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc* -Xlog:gc+age=trace -XX:MaxTenuringThreshold=15 -XX:TargetSurvivorRatio=80
     */
    public static void testTenuringThreshold2() {
        byte[] allocation1, allocation_new, allocation2, allocation3;
        allocation1 = new byte[_1MB / 8];
        allocation_new = new byte[_1MB / 16]; // allocation1 + allocation_new = 생존자 영역의 80% 초과
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];  // 첫 번째 GC 발생
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];  // 두 번째 GC 발생
    }
}
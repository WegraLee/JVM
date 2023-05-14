package org.fenixsoft.jvm.chapter4;

import java.util.ArrayList;
import java.util.List;

/**
 * VM 매개변수: -XX:+UseSerialGC -Xms100m -Xmx100m
 * 
 * @author zzm
 */
public class MemoryMonitoringTest {
    /**
     * 메모리 영역 확보 객체(placeholder), OOMObject의 크기는 약 64KB다.
     */
    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            // 모니터링 곡선의 변화를 더 분명하게 만들기 위한 약간의 지연
            Thread.sleep(50); // ❶
            list.add(new OOMObject()); // ❶
        }
        System.gc(); // ❷
    }

    public static void main(String[] args) throws Exception {
        fillHeap(1000); // ❸
        while(true) {    // ❹ 강제종료 시까지 대기
            System.out.println("대기 시작");
            Thread.sleep(1000);
        }
    }
}

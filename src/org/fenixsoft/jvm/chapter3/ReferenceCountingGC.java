package org.fenixsoft.jvm.chapter3;

/**
 * VM 매개변수：-Xlog:gc*
 * testGC() 메서드가 끝나면 objA와 objB는 수거될까?
 * 
 * @author zzm
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    // 메모리를 많이 차지하여 GC 로그에서 수거 여부를 명확히 알아볼 수 있게 한다.
    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGC() {
        // 두 객체 생성
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        // 내부 필드로 서로를 참조
        objA.instance = objB;
        objB.instance = objA;
        // 참조 해제
        objA = null;
        objB = null;

        // 이 라인에서 GC가 수행된다면 objA와 objB가 수거될까?
        System.gc();
    }
    
    public static void main(String[] args) {
        testGC();
    }
}

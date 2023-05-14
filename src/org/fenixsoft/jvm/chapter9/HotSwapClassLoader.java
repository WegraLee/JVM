package org.fenixsoft.jvm.chapter9;

/**
 * 실행 클래스를 여러 번 로드하기 위해 추가한 클래스로더.
 * defineClass() 메서드를 공개하는 역할의 loadByte() 메서드는
 * 외부에서 명시적으로 호출할 때만 사용된다.
 * 가상 머신에서 호출할 때는 여전히 loadClass() 메서드를 이용하여
 * 부모 위임 모델의 규칙에 따라 클래스를 로드한다.
 *
 * @author zzm
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

}

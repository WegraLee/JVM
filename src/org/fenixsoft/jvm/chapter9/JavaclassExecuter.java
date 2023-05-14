package org.fenixsoft.jvm.chapter9;

import java.lang.reflect.Method;

/**
 * 자바 클래스 실행 도구
 *
 * @author zzm
 */
public class JavaclassExecuter {

    /**
     * 외부에서 전달된 바이트 배열(자바 클래스)을 실행한다.
     * 입력 클래스의 바이트 배열에서 java.lang.System을 나타내는 CONSTANT_Utf8_info 상수를
     * 해킹된 HackSystem 클래스로 변경한다.
     * 실행 메서드는 클래스의 main(String[] args) 메서드이며,
     * 출력 결과는 클래스가 System.out과 System.err에 출력하는 정보다.
     *
     * @param classByte 자바 클래스의 바이트 배열
     * @return 실행 결과
     */
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "org/fenixsoft/classloading/execute/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);
        try {
            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (Throwable e) {
            e.printStackTrace(HackSystem.out);
        }
        return HackSystem.getBufferString();
    }
}

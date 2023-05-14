package org.fenixsoft.jvm.chapter4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zzm
 */
public class BTraceTest {

    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) throws IOException {
        BTraceTest test = new BTraceTest();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10; i++) {                       // ❶ 10회 반복
            reader.readLine();                               // ❷ 엔터 키 입력 대기
            int a = (int) Math.round(Math.random() * 1000);  // ❸ 무작위 정수 생성
            int b = (int) Math.round(Math.random() * 1000);
            System.out.println(test.add(a, b));              // ❹ 두 정수 더하기
        }
    }
}

/*
// BTrace 스크립트 템플릿
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class TracingScript {
    @OnMethod(
        clazz="org.fenixsoft.monitoring.BTraceTest",
        method="add",
        location=@Location(Kind.RETURN)
    )

    public static void func(@Self org.fenixsoft.monitoring.BTraceTest instance,
                            int a, int b, @Return int result) {
        println("콜 스택:");
        jstack();
        println(strcat("메서드 매개변수 A:", str(a)));
        println(strcat("메서드 매개변수 B:", str(b)));
        println(strcat("메서드 결과:", str(result)));
    }
}
*/
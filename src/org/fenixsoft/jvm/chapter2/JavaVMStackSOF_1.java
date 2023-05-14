package org.fenixsoft.jvm.chapter2;

/**
 * VM 매개변수：-Xss180k
 * 
 * @author zzm
 */
public class JavaVMStackSOF_1 {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF_1 oom = new JavaVMStackSOF_1();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("스택 길이:" + oom.stackLength);
            throw e;
        }
    }
}
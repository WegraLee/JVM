package org.fenixsoft.jvm.chapter2;

/**
 * @author zzm
 */
public class RuntimeConstantPoolOOM_2 {
    public static void main(String[] args) {
        String str1 = new StringBuilder("컴퓨터ss").append(" 소프dd트웨어").toString();
        System.out.println(str1.intern() == str1);
    }
}

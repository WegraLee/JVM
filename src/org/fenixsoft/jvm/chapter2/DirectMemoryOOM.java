package org.fenixsoft.jvm.chapter2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * VM 매개변수：-Xmx20M -XX:MaxDirectMemorySize=10M
 * 
 * @author zzm
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}

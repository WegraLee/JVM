package org.fenixsoft.jvm.chapter12;

/**
 * DCL 싱글턴 패턴
 * 
 * @author zzm
 */
public class Singleton {
    private volatile static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
            Singleton.getInstance();
    }
}

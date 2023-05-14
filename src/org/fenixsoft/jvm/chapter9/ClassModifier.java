package org.fenixsoft.jvm.chapter9;

/**
 * 클래스 파일을 수정한다.
 * 상수 풀 안의 상수들을 수정하는 기능만 제공한다.
 *
 * @author zzm
 */
public class ClassModifier {

    /**
     * 클래스 파일에서 상수 풀의 시작 오프셋
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    /**
     * CONSTANT_Utf8_info 상수의 태그 플래그
     */
    private static final int CONSTANT_Utf8_info = 1;

    /**
     * 상수 풀에서 CONSTANT_Utf8_info 타입 상수를 제외한 11가지 상수가 차지하는 길이
     * (CONSTANT_Utf8_info는 고정 길이가 아님)
     */
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};

    private static final int u1 = 1;
    private static final int u2 = 2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    /**
     * 상수 풀에서 CONSTANT_Utf8_info 상수의 내용을 수정한다.
     *
     * @param oldStr 수정 전의 문자열
     * @param newStr 수정된 문자열
     * @return 수정된 결과
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for (int i = 0; i < cpc; i++) {
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);
            if (tag == CONSTANT_Utf8_info) {
                int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);
                offset += (u1 + u2);
                String str = ByteUtils.bytes2String(classByte, offset, len);
                if (str.equalsIgnoreCase(oldStr)) {
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
                    classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
                    return classByte;
                } else {
                    offset += len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classByte;
    }

    /**
     * 상수 풀 안의 상수 개수 반환한다.
     *
     * @return 상수 풀 내의 상수 개수
     */
    public int getConstantPoolCount() {
        return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}

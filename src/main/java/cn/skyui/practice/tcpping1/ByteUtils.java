package cn.skyui.practice.tcpping1;

import java.nio.ByteBuffer;

public class ByteUtils {

    public static int byteArrayToInt(byte[] b) {
        int value = 0;
        int offset = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }

    public static byte[] intToByteArray(int value) {
        final byte[] bytes = {(byte) (value >>> 24), (byte) (value >>> 16),
                (byte) (value >>> 8), (byte) value};
        return bytes;
    }

    public static long byteArrayToLong(byte[] buf) {
        ByteBuffer bb = ByteBuffer.wrap(buf);
        return bb.getLong();
    }

    public static byte[] longToByteArray(long longNr) {
        byte[] bytes = ByteBuffer.allocate(8).putLong(longNr).array();
        return bytes;
    }
}

package cn.skyui.practice;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class HsEncrypt {

    public static void main(String[] args) {
        HsEncrypt.encode("13521468432");
    }

    private static final String TAG = HsEncrypt.class.getSimpleName();

    private static byte[] encodeKey;
    private static final byte[] encodingTable = {
            (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N',
            (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) '+', (byte) '-',
            (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'U',
            (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'h', (byte) 'i',
            (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'a', (byte) 'b',
            (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'O', (byte) 'P',
            (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'A', (byte) 'B', (byte) 'C',
            (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G', (byte) '0', (byte) '1', (byte) '2',
            (byte) '3', (byte) '4', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y',
            (byte) 'z',
    };

    private static final char[] GeneralEncodeTable = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    private static final byte[] GeneralDecodingTable;

    private static final byte[] decodingTable;

    static {
        encodeKey = new byte[] { 'h', 'u', 'n', 'd', 's', 'u', 'n' };

        decodingTable = new byte[128];

        int len = encodingTable.length;
        for (int i = 0; i < len; i++) {
            decodingTable[encodingTable[i]] = (byte) i;
        }

        GeneralDecodingTable = new byte[128];
        int len2 = GeneralEncodeTable.length;
        for (int i = 0; i < len2; i++) {
            GeneralDecodingTable[GeneralEncodeTable[i]] = (byte) i;
        }
    }

    public static void setEncodeKey(byte[] key) {
        encodeKey = key;
    }

    /**
     * 对输入的数据进行Base64编码，生成Base64的字串
     */
    public static byte[] base64Encode(byte[] data) {
        byte[] bytes;

        int modulus = data.length % 3;
        if (modulus == 0) {
            bytes = new byte[4 * data.length / 3];
        } else {
            bytes = new byte[4 * ((data.length / 3) + 1)];
        }

        int dataLength = (data.length - modulus);
        int a1, a2, a3;
        for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
            a1 = data[i] & 0xff;
            a2 = data[i + 1] & 0xff;
            a3 = data[i + 2] & 0xff;

            bytes[j] = encodingTable[(a1 >>> 2) & 0x3f];
            bytes[j + 1] = encodingTable[((a1 << 4) | (a2 >>> 4)) & 0x3f];
            bytes[j + 2] = encodingTable[((a2 << 2) | (a3 >>> 6)) & 0x3f];
            bytes[j + 3] = encodingTable[a3 & 0x3f];
        }

        /*
         * 处理Base64的尾端字符（生成n（n小于2）个“=”号）
         */
        /*
         * process the tail end.
         */
        int b1, b2, b3;
        int d1, d2;

        switch (modulus) {
            case 0: /* nothing left to do */
                break;
            case 1:
                d1 = data[data.length - 1] & 0xff;
                b1 = (d1 >>> 2) & 0x3f;
                b2 = (d1 << 4) & 0x3f;

                bytes[bytes.length - 4] = encodingTable[b1];
                bytes[bytes.length - 3] = encodingTable[b2];
                bytes[bytes.length - 2] = (byte) '=';
                bytes[bytes.length - 1] = (byte) '=';
                break;
            case 2:
                d1 = data[data.length - 2] & 0xff;
                d2 = data[data.length - 1] & 0xff;

                b1 = (d1 >>> 2) & 0x3f;
                b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
                b3 = (d2 << 2) & 0x3f;

                bytes[bytes.length - 4] = encodingTable[b1];
                bytes[bytes.length - 3] = encodingTable[b2];
                bytes[bytes.length - 2] = encodingTable[b3];
                bytes[bytes.length - 1] = (byte) '=';
                break;
        }

        return bytes;
    }

    /**
     * 对输入的Base 64数据进行解码，返回解码后的数据
     */
    private static byte[] base64Decode(byte[] data) {
        byte[] bytes;
        byte b1, b2, b3, b4;

        if (data[data.length - 2] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
        } else if (data[data.length - 1] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
        } else {
            bytes = new byte[((data.length / 4) * 3)];
        }

        for (int i = 0, j = 0; i < data.length - 4; i += 4, j += 3) {
            b1 = decodingTable[data[i]];
            b2 = decodingTable[data[i + 1]];
            b3 = decodingTable[data[i + 2]];
            b4 = decodingTable[data[i + 3]];

            bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[j + 2] = (byte) ((b3 << 6) | b4);
        }

        if (data[data.length - 2] == '=') {
            b1 = decodingTable[data[data.length - 4]];
            b2 = decodingTable[data[data.length - 3]];

            bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
        } else if (data[data.length - 1] == '=') {
            b1 = decodingTable[data[data.length - 4]];
            b2 = decodingTable[data[data.length - 3]];
            b3 = decodingTable[data[data.length - 2]];

            bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
        } else {
            b1 = decodingTable[data[data.length - 4]];
            b2 = decodingTable[data[data.length - 3]];
            b3 = decodingTable[data[data.length - 2]];
            b4 = decodingTable[data[data.length - 1]];

            bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
        }

        return bytes;
    }

    /**
     * 对输入的Base 64数据进行解码，返回解码后的数据
     */
    private static byte[] base64GeneralDecode(byte[] data) {
        byte[] bytes;
        byte b1, b2, b3, b4;

        if (data[data.length - 2] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
        } else if (data[data.length - 1] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
        } else {
            bytes = new byte[((data.length / 4) * 3)];
        }

        for (int i = 0, j = 0; i < data.length - 4; i += 4, j += 3) {
            b1 = GeneralDecodingTable[data[i]];
            b2 = GeneralDecodingTable[data[i + 1]];
            b3 = GeneralDecodingTable[data[i + 2]];
            b4 = GeneralDecodingTable[data[i + 3]];

            bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[j + 2] = (byte) ((b3 << 6) | b4);
        }

        if (data[data.length - 2] == '=') {
            b1 = GeneralDecodingTable[data[data.length - 4]];
            b2 = GeneralDecodingTable[data[data.length - 3]];

            bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
        } else if (data[data.length - 1] == '=') {
            b1 = GeneralDecodingTable[data[data.length - 4]];
            b2 = GeneralDecodingTable[data[data.length - 3]];
            b3 = GeneralDecodingTable[data[data.length - 2]];

            bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
        } else {
            b1 = GeneralDecodingTable[data[data.length - 4]];
            b2 = GeneralDecodingTable[data[data.length - 3]];
            b3 = GeneralDecodingTable[data[data.length - 2]];
            b4 = GeneralDecodingTable[data[data.length - 1]];

            bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
        }

        return bytes;
    }

    /**
     * 把密码添加到原文中
     */
    private static byte[] insertKey(byte[] content, byte[] key) {
        if (content == null || key == null) {
            return content;
        }

        int lenContent = content.length;
        int lenKey = key.length;
        ByteArrayOutputStream tempContent = new ByteArrayOutputStream();
        if (lenContent >= lenKey) {
            int insertScale = lenContent / lenKey;
            for (int i = 0; i < lenContent; i++) {
                if (i % insertScale == 0) {
                    int insertPosition = i / insertScale;
                    if (insertPosition < lenKey) {
                        tempContent.write(key[insertPosition]);
                    }
                }
                tempContent.write(content[i]);
            }
        } else {
            try {
                tempContent.write(key);
                tempContent.write(content);
            } catch (Exception e) {
            }
        }
        return tempContent.toByteArray();
    }

    /**
     * 把密码从原文中删除
     */
    private static byte[] delKey(byte[] content, byte[] key) {
        if (content == null || key == null) {
            return content;
        }

        int lenKey = key.length;
        int lenContent = content.length;
        if (lenContent - lenKey <= 0) {
            return content;
        }
        ByteArrayOutputStream tempContent = new ByteArrayOutputStream();
        if ((lenContent - lenKey) >= lenKey) {
            int delScale = lenContent / lenKey;
            for (int i = 0; i < lenContent; i++) {
                if (i % delScale == 0) {
                    int pos = i / delScale;
                    if (pos >= lenKey) {
                        tempContent.write(content[i]);
                    }
                } else {
                    tempContent.write(content[i]);
                }
            }
        } else {
            tempContent.write(content, lenKey, lenContent - lenKey);
        }
        return tempContent.toByteArray();
    }

    /**
     * “异或”处理
     *
     * @param data 原文
     * @param key 密码
     * @return 处理结果
     */
    private static byte[] XOR(byte[] data, byte[] key) {
        if (data == null || key == null) return data;
        int len = data.length;
        int keyLen = key.length;
        byte[] tempData = new byte[len];
        for (int i = 0; i < len; i++) {
            tempData[i] = (byte) (data[i] ^ key[i % keyLen]);
        }
        return tempData;
    }

    /**
     * 使用默认key进行加密
     *
     * @return huangcheng 2012-9-12
     */
    public static byte[] encode(byte[] content) {
        return encode(content, encodeKey);
    }

    /**
     * 默认加密
     */
    public static String encode(String content, String key) {
        try {
            return new String(encode(content.getBytes("UTF-8"), key.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        try {
            return new String(encode(content.getBytes(), key.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return content;
    }

    /**
     * 加密
     */
    public static byte[] encode(byte[] content, byte[] key) {
        if (content == null || key == null) {
            return content;
        }

        byte[] debugKey = "hundsun-1402-9062".getBytes();
        if (debugKey != null && compareByteArray(key, debugKey)) {
            return content;
        }

        try {
            // 原文中混入密码
            byte[] tempContent = insertKey(content, key);

            // 与密码做异或原始
            tempContent = XOR(tempContent, key);

            // 自定义Base64转换
            tempContent = base64Encode(tempContent);
            return tempContent;
        } catch (Exception e) {
            return content;
        }
    }

    /**
     * 加密
     */
    public static String encode(String content, byte[] key) {
        if (content == null || key == null) {
            return content;
        }

        try {
            byte[] tempContentB = content.getBytes("UTF-8");

            tempContentB = encode(tempContentB, key);
            return new String(tempContentB, "UTF-8");
        } catch (Exception e) {
            return content;
        }
    }

    /**
     * 加密
     */
    public static String encode(String content) {
        return encode(content, encodeKey);
    }

    /**
     * 解密
     */
    public static byte[] decode(byte[] content, byte[] key) {
        if (content == null || key == null) {
            return null;
        }

        byte[] debugKey = "hundsun-1402-9062".getBytes();
        if (debugKey != null && compareByteArray(key, debugKey)) {
            return content;
        }

        try {
            // 自定义Base64还原
            byte[] tempContent = base64Decode(content);

            // 与密码做异或原始
            tempContent = XOR(tempContent, key);

            // 原文中删除混入的密码
            tempContent = delKey(tempContent, key);

            return tempContent;
        } catch (Exception e) {
            return content;
        }
    }

    /**
     * 标准解密
     */
    public static byte[] GeneralDecode(byte[] content) {
        if (content == null) {
            return null;
        }

        try {
            // 自定义Base64还原
            byte[] tempContent = base64GeneralDecode(content);

            return tempContent;
        } catch (Exception e) {
            return content;
        }
    }

    /**
     * 解密
     *
     * @return huangcheng 2012-10-18
     */
    public static String decode(String content) {
        if (content == null) {
            return null;
        }

        try {
            byte[] tempContentB = content.getBytes("UTF-8");

            tempContentB = decode(tempContentB, encodeKey);

            return new String(tempContentB, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解密
     */
    public static String decode(String content, String key) {
        if (content == null || key == null) {
            return null;
        }

        try {
            byte[] tempContentB = content.getBytes("UTF-8");
            byte[] keyB = key.getBytes("UTF-8");

            tempContentB = decode(tempContentB, keyB);

            return new String(tempContentB, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

//    /**
//     * 解密，结果以byte[]形式返回
//     *
//     * @return huangcheng 2012-9-12
//     */
//    public static byte[] decodeResource(Context context, int resourceId, int keyResourceId) {
//        try {
//            // 加载配置文件
//            InputStream inputStream = context.getResources().openRawResource(resourceId);
//            byte[] dataBuf = new byte[inputStream.available()];
//            int dataBufCount = inputStream.read(dataBuf);
//            inputStream.close();
//
//            // 加载密钥
//            inputStream = context.getResources().openRawResource(keyResourceId);
//            byte[] keyBuf = new byte[inputStream.available()];
//            int keyBufCount = inputStream.read(keyBuf);
//            inputStream.close();
//
//            if (dataBufCount > 0 && keyBufCount > 0) {
//                byte[] result = decode(dataBuf, keyBuf);
//                return result;
//            }
//        } catch (IOException e) {
//            DebugLogger.e(TAG,e.getMessage()==null?"":e.getMessage());
//        }
//
//        return null;
//    }
//
//    /**
//     * 解密，使用默认的key,结果以byte[]形式返回
//     *
//     * @return huangcheng 2012-9-12
//     */
//    public static byte[] decodeResource(Context context, int resourceId) {
//        try {
//            // 加载配置文件
//            InputStream inputStream = context.getResources().openRawResource(resourceId);
//            byte[] dataBuf = new byte[inputStream.available()];
//            int count = inputStream.read(dataBuf);
//            inputStream.close();
//            if (count > 0) {
//                byte[] result = decode(dataBuf, encodeKey);
//                return result;
//            }
//        } catch (IOException e) {
//            DebugLogger.e(TAG,e.getMessage()==null?"":e.getMessage());
//        } catch (Exception e) {
//            DebugLogger.e(TAG,e.getMessage()==null?"":e.getMessage());
//        }
//
//        return null;
//    }
//
//    /**
//     * 解密，结果以InputSource形式返回
//     *
//     * @return huangcheng 2012-9-12
//     */
//    public static InputSource decodeResourceReturnInputSource(Context context, int resourceId,
//                                                              int keyResourceId) {
//        byte[] result = decodeResource(context, resourceId, keyResourceId);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result);
//        InputSource is = new InputSource(byteArrayInputStream);
//        return is;
//    }
//
//    /**
//     * 解密，使用默认的key,结果以InputSource形式返回
//     *
//     * @return huangcheng 2012-9-12
//     */
//    public static InputStream decodeResourceReturnInputSource(Context context, int resourceId) {
//        byte[] result = decodeResource(context, resourceId);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result);
//        // InputSource is = new InputSource(byteArrayInputStream);
//        return byteArrayInputStream;
//    }

    /**
     * 比较两个byte数组内容是否相等
     *
     * @return huangcheng 2012-9-27
     */
    public static boolean compareByteArray(byte[] arr1, byte[] arr2) {
        if (arr1 != null && arr2 != null && arr1.length == arr2.length) {
            if (arr1.length == 0) {// 如果长度为0认为是相等的
                return true;
            } else {
                for (int i = 0; i < arr2.length; i++) {
                    if (arr2[i] != arr1[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static char[] base64EncodeChars = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    /**
     * 编码
     */
    public static String encode_new(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param passwd 加密密码
     */
    public static String encrypt(String content, String passwd) {
        if (content == null) {
            return content;
        }
        if (content.length() > 15) {
            List<String> list = new ArrayList<String>();
            while (content.length() > 15) {
                String temp = content.substring(0, 15);
                list.add(encrypt(temp, passwd));
                content = content.substring(15);
            }
            if (content.length() > 0) {
                list.add(encrypt(content, passwd));
            }
            StringBuffer buffer = new StringBuffer();
            for (String temp : list) {
                buffer.append(temp);
            }
            return buffer.toString();
        }
        try {
            Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(passwd.getBytes("UTF-8"), "AES");
            aesECB.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = aesECB.doFinal(content.getBytes("UTF8"));
            return encode_new(result);
        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
        }
        return null;
    }


}

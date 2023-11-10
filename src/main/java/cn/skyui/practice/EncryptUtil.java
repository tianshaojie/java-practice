package cn.skyui.practice;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptUtil {

    public static final String SECRET_KEY = "d24nf39R8Q8o82BP";
    public static final String VECTOR = "9932166547903398";

    public static String encrypt(String encData, String secretKey, String vector) {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        try {
            byte[] raw = secretKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(encData.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String decData, String secretKey, String vector) {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        try {
            byte[] raw = secretKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.getDecoder().decode(decData);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("{\"mobile\":\"11111111111\"}", SECRET_KEY, VECTOR));
    }
}

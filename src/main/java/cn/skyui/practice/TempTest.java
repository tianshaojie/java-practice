package cn.skyui.practice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tiansj on 2017/10/1.
 */
public class TempTest {

    public static void main(String[] args) {

//        String userAgent = String.format("%s/%s (%s;Android %s)", null, null, null, null);
//        System.out.println(userAgent);
//
//        System.out.println(System.currentTimeMillis());
//
//        String url = "scheme=fzzqxf%3a%2f%2fbrowser%2f%3ftitle%3dfoundersc%26url%3dhttps%3a%2f%2fwww.foundersc.com";
//        try {
//            Map map = splitQuery(url);
//            System.out.println(map.get("scheme"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//        String time = "20180202";
//        String lastTime = "20180201";
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            if(time.compareTo(lastTime) < 0) {
//                System.out.println("20180202");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//        String str = "12345abcde";
//        for(int i = 0; i < str.length(); i++) {
//            System.out.println(String.valueOf(str.charAt(i)).toUpperCase());
//        }
//
//        String mobile = "13521213235";
//        int len = mobile.length();
//        System.out.println(mobile.substring(len-4, len));

//        try {
//            testException();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("testException");

        System.out.println(compareVersion("6.0.0.2", "6.0.0.1"));
    }

    public static int compareVersion(String oldVersion, String newVersion) {
        if (null == newVersion || "".equals(newVersion) || null == oldVersion || "".equals(
                oldVersion)) {
            return 0;
        }
        String[] v1 = oldVersion.split("\\.");
        String[] v2 = newVersion.split("\\.");
        if (v1.length < v2.length) { // 如果版本.长度不一样，后面的缺省为0，如果保证长度一样，这里可以不需要
            String[] t = new String[v2.length];
            System.arraycopy(v1, 0, t, 0, v1.length);
            for (int i = v1.length; i < t.length; i++) {
                t[i] = "0";
            }
            v1 = t;
        } else if (v1.length > v2.length) {
            String[] t = new String[v1.length];
            System.arraycopy(v2, 0, t, 0, v2.length);
            for (int i = v2.length; i < t.length; i++) {
                t[i] = "0";
            }
            v2 = t;
        }

        for (int i = 0; i < v1.length; i++) {
            int n1 = Integer.valueOf(v1[i]).intValue();
            int n2 = Integer.valueOf(v2[i]).intValue();
            if (n1 != n2) {
                return (n1 - n2); // 大于0则s1版本大，小于0则s2版本大
            }
        }
        return 0; // 相等
    }


    public static Map<String, String> splitQuery(String urlParam) throws UnsupportedEncodingException {
        final Map<String, String> query_pairs = new LinkedHashMap<>();
        final String[] pairs = urlParam.split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            if(idx > 0) {
                final String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                final String value = pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
                query_pairs.put(key, value);
            }
        }
        return query_pairs;
    }

    public static void testException() {
        testException1();
    }

    public static void testException1() {
        int a = 1/0;
    }
}

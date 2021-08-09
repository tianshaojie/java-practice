package cn.skyui.practice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//        System.out.println(compareVersion("6.0.0.2", "6.0.0.1"));



//            Socket s = new Socket();
//            SocketAddress socketAddress = new InetSocketAddress("app.cnht.com.cn", 80);
//            try {
//                s.connect(socketAddress, 3000);// 超时3秒
//                System.out.printf("connect success");
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    s.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

//        String log = "2 packets transmitted, 2 received, 100.0% packet loss";
//        String loss = null;
//        String regex = "(\\d+(\\.\\d+)?)% packet loss";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(log);
//        while (m.find()) {
//            loss = m.group(1);
//        }
//        try {
//            System.out.println("loss = " + new BigDecimal(loss).doubleValue());
//        } catch (Exception exception) {
//            System.out.println("loss = 0.00");
//        }

//        String log = "ping qq.com (183.3.226.35) 56(84) bytes of data.; 64 bytes from 183.3.226.35: icmp_seq=1 ttl=50 time=48.12 ms; 1 packets transmitted, 1 received, 0% packet loss, time 0ms; rtt min/avg/max/mdev = 48.787/48.787/48.787/0.000 ms";
//        String time = "";
////        String regex = "time=([(\\d+(\\.\\d+)?)\\s]+)(?= ms)";
//        String regex = "time=(\\d+(\\.\\d+)?)\\s?ms";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(log);
//        while (m.find()) {
//            time = m.group(1).trim();
//        }
//        try {
//            System.out.println("time = " + new BigDecimal(time).doubleValue());
//        } catch (Exception exception) {
//            System.out.println("loss = .00");
//        }

        String temp = null;
        boolean res = "a".equals(temp);
        System.out.printf(String.valueOf(res));


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

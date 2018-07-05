package com.yuzhi.fine;

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
        String url = "scheme=fzzqxf%3a%2f%2fbrowser%2f%3ftitle%3dfoundersc%26url%3dhttps%3a%2f%2fwww.foundersc.com";
        try {
            Map map = splitQuery(url);
            System.out.println(map.get("scheme"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String time = "20180202";
        String lastTime = "20180201";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(time.compareTo(lastTime) < 0) {
                System.out.println("20180202");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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


}

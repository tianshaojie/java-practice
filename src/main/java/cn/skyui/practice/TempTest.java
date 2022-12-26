package cn.skyui.practice;

import com.alibaba.fastjson.JSON;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

//        String temp = null;
//        boolean res = "a".equals(temp);
//        System.out.printf(String.valueOf(res));


//        Map<String, Boolean> serverIpv6Map = new HashMap<>();
//        serverIpv6Map.put("abc",true);
//        serverIpv6Map.put("abc",true);
//        serverIpv6Map.put("abc",false);
//        System.out.println(serverIpv6Map.toString());
//        System.out.println(serverIpv6Map.size() + ", " + serverIpv6Map.get("abc"));


//        BigDecimal res = BigDecimal.valueOf(6).subtract(BigDecimal.valueOf(3)).divide(BigDecimal.valueOf(3));
//        System.out.println("res=" + res.toString());

//        testBitDecimal();

//        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        System.out.printf("currentHour = " + currentHour);

//        System.out.println(sha1Hash("abc"));


        String str = "a|b|c|d|e";
        String[] arr = str.split("[|]", 2);
        System.out.println(JSON.toJSONString(arr));
    }


    private static String sha1Hash (String toHash) {
        String hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
            byte[] bytes = toHash.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            // This is ~55x faster than looping and String.formating()
            hash = bytesToHex( bytes );
        }
        catch( Throwable e ) {
        }
        return hash;
    }

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex( byte[] bytes ) {
        char[] hexChars = new char[ bytes.length * 2 ];
        for( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[ j ] & 0xFF;
            hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
            hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
        }
        return new String( hexChars );
    }

    private static void testBitDecimal() {
//        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
//        System.out.println(decimalFormat.format(new BigDecimal("234.8989")));
//        System.out.println(decimalFormat.format(new BigDecimal("234.89")));
//        System.out.println(decimalFormat.format(new BigDecimal("234.8")));
//        System.out.println(decimalFormat.format(new BigDecimal("234")));
//        System.out.println(decimalFormat.format(new BigDecimal(".8989")));
//        System.out.println(decimalFormat.format(new BigDecimal("0.156")));
//        System.out.println(decimalFormat.format(new BigDecimal("0.2351")));
//        System.out.println(decimalFormat.format(0.2351));
//
//        BigDecimal value = new BigDecimal("234.8989").setScale(1,BigDecimal.ROUND_HALF_UP);
//        // 不足两位小数补0
//        DecimalFormat decimalFormat1 = new DecimalFormat("0.00");
//        System.out.println(decimalFormat1.format(value));


//        try {
//            BigDecimal prevEffectivePrice = new BigDecimal("-");
//            BigDecimal times = new BigDecimal(1).divide(prevEffectivePrice, 2, BigDecimal.ROUND_HALF_EVEN);
//            if(times.compareTo(BigDecimal.valueOf(10)) > 0 || times.compareTo(BigDecimal.valueOf(0.1)) < 0) {
//                System.out.println("times="+times);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        try {
//            updateAssetStartTime = new SimpleDateFormat(ASSET_TIME_FORMAT).parse(UPDATE_ASSET_START_TIME);
//            updateAssetEndTime = new SimpleDateFormat(ASSET_TIME_FORMAT).parse(UPDATE_ASSET_END_TIME);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        long timestampServer = 1639038096194l;
//        long timestampLocal = 1639038096407l;
//
//        boolean isUpdateAssetTime = isUpdateAssetTime(timestampServer, timestampLocal);
//        System.out.println("isUpdateAssetTime=" + isUpdateAssetTime);


//        testRxjava();

        startTimer();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                    // 3秒后停止
//                    disposable.dispose();
//                    disposable = null;
//                    // 5秒后恢复
//                    Thread.sleep(5000);
//                    startTimer();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
    }

    private static void startTimer() {
        stopTimer();
        // 每隔200毫秒执行一次逻辑代码
        disposable = Observable.interval(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println(aLong++);
                    }
                });
    }

    /**
     * 停止定时执行
     */
    protected static void stopTimer() {
        if (null != disposable) {
            disposable.dispose();
            disposable = null;
        }
    }



    private static Observable observable;
    private static ObservableEmitter observableEmitter;
    private static Disposable disposable;

    private static void testRxjava() {
        observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                observableEmitter = emitter;
                // send events with simulated time wait
//                observableEmitter.onNext(1); // skip
//                Thread.sleep(400);
//                observableEmitter.onNext(2); // deliver
//                Thread.sleep(505);
//                observableEmitter.onNext(3); // skip
//                Thread.sleep(100);
//                observableEmitter.onNext(4); // deliver
//                Thread.sleep(605);
//                observableEmitter.onNext(5); // deliver
//                Thread.sleep(510);
//                emitter.onComplete();
            }
        });


        observable.debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        System.out.println(o.toString());
                    }
                });


        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < Integer.MAX_VALUE; i++) {
                    if(i % 2 == 0) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    observableEmitter.onNext(i);
                    } else {
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }).start();
    }


    private static final String ASSET_TIME_FORMAT = "HH:mm";
    private static final String UPDATE_ASSET_START_TIME = "09:00";
    private static final String UPDATE_ASSET_END_TIME = "15:00";
    private static Date updateAssetStartTime;
    private static Date updateAssetEndTime;


    /**
     * 上证云更新现价的时间范围：交易日9:00之前或16:00，
     */
    private static boolean isUpdateAssetTime(long timestampServer, long timestampLocal) {
        long timeDiff = 1639038096500l - timestampLocal;
        long currentTime = timestampServer + timeDiff;
        try {
            String nowTimeStr = new SimpleDateFormat(ASSET_TIME_FORMAT).format(new Date(currentTime));
            Date nowTime = new SimpleDateFormat(ASSET_TIME_FORMAT).parse(nowTimeStr);
            return isEffectiveDate(nowTime, updateAssetStartTime, updateAssetEndTime);
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     * String format = "HH:mm:ss";
     * Date nowTime = new SimpleDateFormat(format).parse("09:27:00");
     * Date startTime = new SimpleDateFormat(format).parse("09:27:00");
     * Date endTime = new SimpleDateFormat(format).parse("09:27:59");
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if(nowTime == null || startTime == null || endTime == null) {
            return false;
        }
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
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

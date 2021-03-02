package cn.skyui.practice.tcping1;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class PingClient {

    private DatagramChannel channel;
    private InetSocketAddress address;
    private InetAddress host;
    private double avgRTT ;
    private long minRTT ;
    private long maxRTT ;
    private int packetSend = 0;
    private int packetRec = 0;
    private int packetLost = 0;
    private long sum_time = 0;
    public PingClient(String host, int port) {
        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            showUsageAndExit("invalid host name !");
        }
//        if (port < 1024 || port > 65535) {
//            showUsageAndExit("invalid port number !");
//        }
        address = new InetSocketAddress(this.host, port);
    }
    public void close() throws IOException{
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
    }
    public void echoResult() {
        int lossRate = (int)(packetLost*100.0 / packetSend);
        System.out.println("----- Ping Statistics -----");
        System.out.println( packetSend + " packets transmitted, " + packetRec + " packets received, " + packetLost + " packets lost. ("+lossRate+"% lost)");
        System.out.println("RTT(ms) MIN = " + minRTT + ", AVG = " + avgRTT + ", MAX = " + maxRTT);
    }
    public void execute() throws IOException {
        System.out.println("----- Pinging "+address.getHostString()+" -----");
        channel = DatagramChannel.open();
        //设置为非阻塞模式
        channel.configureBlocking(false);
        ByteBuffer sendBuff;
        ByteBuffer recvBuff;
        short seq = 0;
        while (seq < 10) {//一共发十次请求
            long start_time = System.currentTimeMillis();
            byte[] packet =getPacket(seq,start_time);
            sendBuff = ByteBuffer.wrap(packet);
            recvBuff = ByteBuffer.allocate(50);
            //发送UDP数据报
            channel.send(sendBuff, address);
            packetSend++;
            //等待回复，直到超时
            long used_time=waitRecv(channel,recvBuff);
            byte[] receiced=recvBuff.array();
            //根据返回值判断是否超时
            if (used_time == -1) {
                System.out.println("PING request " + seq + " time out.");
                packetLost++;
                seq++;
            } else{
                //判断收到的回复的确是本程序需要的本次请求的Ping回复
                if (!isValidAnswer(receiced,seq)) {
                    System.out.println("Server Error, exiting ...");
                    System.exit(1);
                }
                // 计算总时间和三种 RTT
                sum_time += used_time;
                if (seq == 0) {
                    minRTT = used_time;
                    maxRTT = used_time;
                    avgRTT = used_time;
                } else {
                    if (used_time < minRTT) minRTT = used_time;
                    if (used_time > maxRTT) maxRTT = used_time;
                    //计算平均 RTT
                    avgRTT = round((double) sum_time / (double) packetRec);
                }
                System.out.println("PING request " + seq +" RTT(ms): "+used_time);
                packetRec++;
                seq++;
            }
        }
        echoResult();
    }
    private boolean isValidAnswer(byte[] receiced, short seq) {
        if (receiced[0] != 0 || receiced[1] != 0) {//确保ping的回复报文应该第一个字段和第二个字段都为0
            return false;
        }
        if (bytes2Num(receiced, 4, 2) != getPid() || bytes2Num(receiced, 6, 2) != seq) {
            //确保这个数据报对应的请求是本程序发出的且是本次请求的回复
            return false;
        }
        return true;
    }
    /**
     * 将byte数组的一部分byte转换成long
     * @param b
     * @param begin  开始字节的索引
     * @param len    转换的字节数
     * @return       除了long，其余整数类型都不负责符号位转换，如果是负数，由调用者负责对返回值强制转换得到
     */
    private long bytes2Num(byte[] b,int begin,int len) {
        int left=len;
        long sum = 0;
        for (int i = begin; i < begin + len; i++) {
            long n = ((int) b[i]) & 0xff;
            n <<= (--left) * 8;
            sum += n;
        }
        return sum;
    }
    /**
     * 发送的报文格式：类型(8) |  代码(0)  | 校验和
     *              标识符(pid)   |   序号
     *              时间戳前32bit
     *              时间戳后32bit
     *              剩余有效信息字节数 |
     *              "PingUDP"+"CRLF"+填充字节
     * Note: 采用big-ending顺序，即JVM与网络都使用的字节序
     * @param seq ICMP数据报序号
     * @param timestamp 当前时间戳
     * @return 组装好的ICMP数据报
     */
    private static byte[] getPacket(short seq, long timestamp) {
        byte[] firstLine = new byte[]{8, 0, 0, 0};
        byte[] seqBs = toBytes(seq, 2);// 序号字节数组
        byte[] tsBs = toBytes(timestamp, 8);// 时间戳字节数组
        byte[] pidBs = toBytes(getPid(), 2);// 标识符字节数组
        String tmp = "PingUDP" + System.lineSeparator();
        byte[] tmpbs = tmp.getBytes();
        byte[] validBytes = toBytes(tmpbs.length, 2);
        int toAdd;//需要填充的字节数
        byte[] other;//"PingUDP"+"CRLF"+填充字节
        if ((toAdd = tmpbs.length % 4) != 0) {//如果不是四的整数倍的字节
            other = new byte[tmpbs.length + toAdd];
            System.arraycopy(tmpbs, 0, other, 0, tmpbs.length);
        } else {
            other = tmpbs;
        }
        byte[] packet = new byte[18 + other.length];
        //将除了校验和的其他字段复制到packet中
        copyArray(packet, firstLine, pidBs, seqBs, tsBs, validBytes, other);
        //计算校验和
        long checkSum = getCheckSum(packet);
        //填充packet的checksum字段
        byte[] cs = toBytes(checkSum, 2);
        System.arraycopy(cs, 0, packet, 2, cs.length);
        return packet;
    }
    /**
     * 将多个byte[]的所有数据复制到target中
     * @param srcs 来源数组
     * @param target
     */
    private static void copyArray(byte[] target,byte[] ... srcs) {
        int hasCopied=0;
        for (byte[] src : srcs) {
            System.arraycopy(src, 0, target, hasCopied, src.length);
            hasCopied += src.length;
        }
    }
    /**
     * 由byte数组得到其校验和
     * @param buf 数据
     * @return  返回校验和，在long型返回值的低16位
     */
    private static long getCheckSum(byte[] buf) {
        int i = 0;
        int length = buf.length;
        long sum = 0;//checksum只占sum的低16位
        //由于sum为long型，下面的加法运算都不会导致符号位改变，等价于无符号加法
        while (length > 0) {
            sum += (buf[i++] & 0xff) << 8;//与checksum的高8位相加
            if ((--length) == 0) break;// 如果buf的byte个数不为偶数
            sum += (buf[i++] & 0xff); //与checksum的低8位相加
            --length;
        }
        //处理溢出，将sum的从右往左第二个16位与其第一个16位(最右的16位)相加并取反
        return (~((sum & 0xFFFF) + (sum >> 16))) & 0xFFFF;
    }
    /**
     * 取value的后几个字节放入byte[]中
     * @param value
     * @param len   指定value的后len个字节
     * @return
     */
    private static byte[] toBytes(long value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) (value >> 8 * i);
        }
        return b;
    }
    /**
     *
     * @param x
     * @return 将参数x保留小数点后两位
     */
    private static double round(double x) {
        return Math.round(x * 100.0)/100.0;
    }
    /**
     *
     * @return 当前JVM的进程id
     */
    private static short getPid() {
        // 获取代表当前JVM的字符串
        String name = ManagementFactory.getRuntimeMXBean().getName();
        // 获得pid
        String pid = name.split("@")[0];
        return Short.parseShort(pid);
    }
    /**
     *
     * @param channel   连接服务器套接字的channel
     * @param recvBuff  接收回复数据的ByteBuffer
     * @return          收到回复的时间。如果超时，返回-1;
     * @throws IOException
     */
    private long waitRecv(DatagramChannel channel, ByteBuffer recvBuff) throws IOException {
        SocketAddress server;
        final long timeout = 1000;
        long start_time = System.currentTimeMillis();
        long used_time;
        while ((used_time=System.currentTimeMillis() - start_time) < timeout) {//time out前一直循环，尝试接收回复
            server = channel.receive(recvBuff);
            if (server != null) {//如果收到信息
                return used_time;
            }
        }
        //超时后返回-1，代表在规定时间内没有收到回复
        return -1;
    }
    private static void showUsageAndExit(String errMsg) {
        if (errMsg != null && errMsg.length() != 0) {
            System.out.println(errMsg);
        }
        System.out.println("usage: java PingClient <host> <port>");
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {
//        if (args.length != 2) {
//            showUsageAndExit("");
//        }
        String host = "app.cnht.com.cn";
        int port = 443;
//        try {
//            port = Integer.parseInt(args[1]);
//        } catch (NumberFormatException nfe) {
//            showUsageAndExit("invalid port number !");
//        }
        PingClient pingClient = new PingClient(host, port);
        pingClient.execute();
        pingClient.close();
    }
}

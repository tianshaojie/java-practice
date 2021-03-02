package cn.skyui.practice.tcpping.pitcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.skyui.practice.tcpping.ByteUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pitcher extends Thread{

    private int port;
    private int rate;
    private int size;
    private String host;
    private List<Long> sentIds;

    // total nr of sent messages
    private long nSentMsg = 0;
    private Statistics statistics;

    private static Logger logger = LogManager.getLogger(Pitcher.class.getName());

    public Pitcher(String host, int p, int mps, int size) {
        this.port = p;
        this.rate = mps;
        this.size = size;
        this.host = host;
        this.statistics = Statistics.getInstance();
        this.sentIds = Collections.synchronizedList(new ArrayList<Long>());
    }

    public void run() {
        System.out.println(" Pitcher ");
        //send messages each 1/rate seconds
        new Timer().scheduleAtFixedRate(new SendMessageTask(host, port), 2000, 1000 / rate);
        //display text each second
        new Timer().scheduleAtFixedRate(new DisplayTask(), 1000, 1000);
    }

    private class SendMessageTask extends TimerTask {

        private Socket socket;
        private String address;
        private int port;

        public SendMessageTask(String inetAddress, int port) {
            this.address = inetAddress;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                socket = new Socket(InetAddress.getByName(address), port);
                DataOutputStream out = new DataOutputStream(
                        socket.getOutputStream());
                byte[] msg = null;
                synchronized (socket) {
                    // send message size
                    out.write(ByteUtils.intToByteArray(size));

                    // send message id
                    long id = getRandomLong();
                    byte[] bId = ByteUtils.longToByteArray(id);
                    out.write(bId);

                    // send timestamp
                    long timeStamp = System.currentTimeMillis();
                    byte[] bTimeStamp = ByteUtils.longToByteArray(timeStamp);
                    out.write(bTimeStamp);

                    // send some random string
                    byte[] rest = new byte[size - 2 * 8];
                    out.write(rest);
                    out.flush();

                    // add id to list of sent ids for tracking
                    sentIds.add(id);
                    // increase total number of sent messages
                    nSentMsg++;

                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    byte[] buff = new byte[4];
                    dis.read(buff);
                    int n = ByteUtils.byteArrayToInt(buff);
                    msg = new byte[n];
                    dis.read(msg);
                    socket.notify();
                }
                process(msg);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    public static long getRandomLong() { return (long) (new Random().nextDouble() * 1234567L);}

    private void process(byte[] msg) {
        final int LONG_SIZE = 8;
        int offset = 0;
        // read in id
        long id = ByteUtils.byteArrayToLong(Arrays.copyOfRange(msg, offset, offset + LONG_SIZE));
        offset += LONG_SIZE;

        // everything ok; remove id from the list of sentIds
        sentIds.remove(id);
        if (sentIds.size() > 0)
            System.out.println("Lost packets: " + sentIds.toString());

        // read in original timestamp (timestamp A)
        long timestampA = ByteUtils.byteArrayToLong(Arrays.copyOfRange(msg, offset, offset + LONG_SIZE));
        offset += LONG_SIZE;

        // read in Catcher's timestamp (timestamp B)
        long timestampB = ByteUtils.byteArrayToLong(Arrays.copyOfRange(msg, offset, offset + LONG_SIZE));
        offset += LONG_SIZE;

        // update statistics
        synchronized (statistics) {
            statistics.updateStatistics(timestampA, timestampB,
                    System.currentTimeMillis());
            statistics.notify();
        }
    }

    private class DisplayTask extends TimerTask {

        @Override
        public void run() {
            Calendar cal = Calendar.getInstance();
            cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            logger.info("\n********************** {} *****************************", sdf.format(cal.getTime()));

            synchronized (statistics) {
                logger.info("Total number of messages sent so far: {}", nSentMsg);
                logger.info("Total number of messages in previous second (mps): {}", rate);

                logger.info("Max RTT: {} ms, Avg RTT: {} ms, Avg A->B: {} ms, Avg B->A: {} ms",
                        (double)statistics.getTotalCycleTime(), (double)statistics.getCycleTime(),
                        ((double)statistics.getABTime() / rate), ((double)statistics.getBATime() / rate) );

                statistics.resetTimes();
                statistics.notify();
            }
        }
    }
}

package cn.skyui.practice.tcpping1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Catcher extends Thread{
    Socket socket;
    private String ipAddress;
    private int port;
    DataOutputStream out;
    DataInputStream in;

    private static Logger logger = LogManager.getLogger(Catcher.class.getName());

    public Catcher(String address, int p){
        ipAddress = address;
        port = p;
    }

    public void run() {
        System.out.println(" Catcher ");
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            ServerSocket serverSocket = new ServerSocket(port, 50, address);
            logger.info("Catcher started on {}:{}", address, port);
            while (true) {
                socket = serverSocket.accept();
                this.in = new DataInputStream(socket.getInputStream());
                byte[] buff = new byte[4];
                in.read(buff);
                int n = ByteUtils.byteArrayToInt(buff);
                byte[] msg = new byte[n];
                in.read(msg);

                byte[] output = process(msg);
                out = new DataOutputStream(socket.getOutputStream());
                out.write(buff);
                out.write(output);
                out.flush();
                socket.notify();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try { socket.close(); } catch (IOException ignorable){}
        }
    }

    private byte[] process(byte[]msg) {
        int n = msg.length;
        byte[] output = new byte[n];
        final int LONG_SIZE = 8;

        int offset = 0;
        //read in id
        byte[] id = Arrays.copyOfRange(msg, offset, offset+LONG_SIZE);
        offset += LONG_SIZE;

        //read in timestamp
        byte[] timestamp =  Arrays.copyOfRange(msg, offset, offset+LONG_SIZE);

        //create output message
        offset = 0;
        for (int i=0; i<LONG_SIZE; i++) {
            output[i+offset] = id[i];
        }
        offset+=LONG_SIZE;
        for (int i=0; i<LONG_SIZE; i++) {
            output[i+offset] = timestamp[i];
        }
        offset+=LONG_SIZE;

        long currentTimestamp = System.currentTimeMillis();
        byte[] currentTmsp = ByteUtils.longToByteArray(currentTimestamp);
        for (int i=0; i<currentTmsp.length; i++) {
            output[i+offset] = currentTmsp[i];
        }
        return output;
    }
}

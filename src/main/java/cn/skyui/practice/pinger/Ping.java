package cn.skyui.practice.pinger;

import java.io.IOException;
import java.net.Socket;

public class Ping {

    public static boolean pinging;

    public static void ping(String address, int port){
        while(pinging) {
            try {
                Socket socket = new Socket(address, port);
                System.out.println("Pinged " + address + " using port=" + port);
                socket.setSoTimeout(10);
                System.out.println();
            } catch (IOException e) {
                System.out.println("Connection Timed Out to " + address + ":" + port);
            }
        }

    }

}

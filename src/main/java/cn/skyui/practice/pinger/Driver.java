package cn.skyui.practice.pinger;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Driver class which opens connection and sends/receives message
 * @author superrmatt
 *
 */
public class Driver extends TimerTask{

    /**
     * the port to send message through
     */
    private static int _port = 443;

    /**
     * Milliscond rate of the run() method execution. 3600000 (the default) is one hour.
     */
    private static int _rate = 3000;

    /**
     * IP address of client & server
     */
    private static String _clientIP = "";
    private static String _serverIP = "baidu.com";

    /**
     * holds reference to the connector class
     */
    private static Connector _conn = new Connector();

    /**
     * main. instantiates a timer and schedules it to run every hour
     * Can be easily changed to run in whatever time rate you want. simply update the rate variable.
     * every hour the run() method will be executed.
     * @param args array of strings, no args necesary in this.
     */
    public static void main(String[] args){
        for(int i = 0; i < 2; i ++){ //this was set to an arbitrary value, at end of day I simply turned it off. wanted to use a number high enough where I wouldn't have any chance of having to restart it
            Timer timer = new Timer();
            timer.schedule(new Driver(), _rate);
        }
    }

    /**
     * executes via the Timer whenever it is scheduled to execute
     */
    @Override
    public void run() {
        int init = 0;

        try{
            InetAddress iAddress = InetAddress.getLocalHost();
            _clientIP = iAddress.getHostAddress();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }

        try{
            Socket socket = new Socket(_serverIP, _port);
            init = _conn.initialize(socket);
        }catch(SocketException e){
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

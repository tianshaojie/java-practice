package cn.skyui.practice.pinger;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * class which contains a method to establish a conenction to the server
 * @author superrmatt
 */
public class Connector {

    /**
     * holds the messages received by the server
     */
    private static ArrayList<String> _replies = new ArrayList<String>();

    /**
     * String message to send to server
     */
    private static String _message = "hello";

    /**
     * opens the connection to the server
     * @param socket the socket with which to send connection through
     * @return returns int 1 when completed, this helps allow tracking in the driver.
     */
    public static int initialize(Socket socket) throws IOException{

        //send message
        OutputStream out = socket.getOutputStream();
        OutputStreamWriter oswriter = new OutputStreamWriter(out);
        BufferedWriter buffWriter = new BufferedWriter(oswriter);

        buffWriter.write(_message);
        buffWriter.flush();
        System.out.println("Message sent");

        //get return message
        InputStream in = socket.getInputStream();
        InputStreamReader inReader = new InputStreamReader(in);
        BufferedReader buffReader = new BufferedReader(inReader);
        String message = buffReader.readLine();
        System.out.println(message);

        _replies.add(message);
        System.out.println();
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("previous replies");
        for(int i = 0; i < _replies.size(); i ++){
            System.out.println(_replies.get(i));
        }
        System.out.println("----------------------------------------------");

        return 1;

    }

}

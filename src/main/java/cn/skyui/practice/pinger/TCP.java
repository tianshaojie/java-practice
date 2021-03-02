package cn.skyui.practice.pinger;

import java.util.Scanner;

public class TCP {

    public static String address;
    public static int port;
    public static String portt;

    public static String getAddress(Scanner scanner) {
        System.out.print("tcp@shprqness.club $ ");
        return address = scanner.nextLine();
    }

    public static String getPort(Scanner scanner) {
        System.out.print("tcp@shprqness.club $ ");
        return portt = scanner.nextLine();
    }

    public static void main(String[] args){
//        System.out.println(Title.title);
        //ping <ip> <port>

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("What is the targets address?");
//        getAddress(scanner);
//
//        Scanner s = new Scanner(System.in);
//        System.out.println("What is the targets port?");
//        getPort(s);
//
//        try{
//            port = Integer.parseInt(portt);
//        }catch (Exception e){
//
//        }

        Ping.pinging = true;

        Ping.ping("baidu.com", 443);



    }

}

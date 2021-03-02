package cn.skyui.practice.tcping1;

public class TCPPing {

	public static void main(String[] args) {

		@SuppressWarnings("unused")
		String runningMode;
		// Setting default port value
		Integer socketPort = 9900;
		String socketBindAddress;
		// Setting default values of messagesPerSecond and messageSize
		Integer messagesPerSecond = 1;
		Integer messageSize = 300;
		String hostname;

		Pitcher pitcher = new Pitcher();
		Catcher catcher = new Catcher();
		// Parsing parameters from the command line
		try {



			try {
				socketPort = 443;
				messagesPerSecond = 64;
				messageSize = 64;
				hostname = "app.cnht.com.cn";
				pitcher.sendMessage(hostname, socketPort, messageSize, messagesPerSecond);
			} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
				help();
			}

//			socketBindAddress = "app.cnht.com.cn";
//			try {
//				socketPort = 443;
//			} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
//				help();
//				terminate();
//			}
//			catcher.receiveMessage(socketBindAddress, socketPort);

//			runningMode = args[0];
//			if (args[0].equals("-p")) {
//				try {
//					socketPort = Integer.parseInt(args[2]);
//					messagesPerSecond = Integer.parseInt(args[4]);
//					messageSize = Integer.parseInt(args[6]);
//					if (messageSize < 50 || messageSize > 3000) {
//						System.out.println(
//								"Message size out of range! It cannot be more than 3000 bytes and less than 50 bytes.");
//						return;
//					}
//					hostname = args[7];
//					pitcher.sendMessage(hostname, socketPort, messageSize, messagesPerSecond);
//				} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
//					help();
//				}
//			} else if (args[0].equals("-c")) {
//				socketBindAddress = args[2];
//				try {
//					socketPort = Integer.parseInt(args[4]);
//				} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
//					help();
//					terminate();
//				}
//				catcher.receiveMessage(socketBindAddress, socketPort);
//			} else if (!args[0].equals("-c") && !args[0].equals("-p")) {
//				help();
//				terminate();
//			}
		} catch (ArrayIndexOutOfBoundsException e) {
			help();
			terminate();
		}
	}

	public static void help() {
		System.out.println(
				"Wrong format. Use the following formats:\nPitcher mode: java tcpping.TCPPing -p -port <socket port number> -mps <messages per second> -size <message size> <hostname>\nCatcher mode: java tcpping.TCPPing -c -bind <socket bind address> -port <socket port number>");
	}
	
	public static void terminate(){
		System.exit(0);
	}
}

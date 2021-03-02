package cn.skyui.practice.tcping1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Marko �tuban
 *
 */
public class Pitcher {

	private Socket socket = null;
	private String message = new String();
	private Integer messageNumberSum = 0;
	private Integer messageID;
	private Integer messageSize;
	private Integer messagesPerSecond;
	private double timeAToB = 0;
	private double previousSecondTimeAToB;
	@SuppressWarnings("unused")
	private double timeBToA = 0;
	private double previousSecondTimeBToA;
	private Integer messagesSentInThePreviousSecond = 0;
	private double RTT;
	private double maxAToB = 0;
	private double maxBToA = 0;
	private double maxRTT = 0;

	private Timer printTimer = new Timer();
	private TimerTask printTask;

	private Timer sendTimer = new Timer();
	private TimerTask sendTask;

	private long period = 0;
	private long endTime, startTime;

	public Pitcher() {
	}

	public void sendMessage(String hostname, Integer socketPortNumber, Integer messageSize, Integer messagesPerSecond) {
		try {
			// Connecting to the hostname on a certain port
			socket = new Socket(this.getIPAddressByHostname(hostname), socketPortNumber);
			System.out
					.println("Connected to " + this.getIPAddressByHostname(hostname) + " on port " + socketPortNumber);

			period = 1000 / messagesPerSecond;
			this.messageSize = messageSize;
			this.messagesPerSecond = messagesPerSecond;

			// Printing statistics
			printStatsEverySecond();
			// Sending messages at a specified rate
			sendAtFixedRate();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("Host unknown!");
		} catch (IOException e) {
			System.out.println("Connection failed!");
		}
	}

	public String getIPAddressByHostname(String hostname) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return address.getHostAddress();
	}

	private void printStatsEverySecond() {
		this.printTask = new TimerTask() {
			public void run() {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

				new Columns()
						.addLine("Time", "Messages", "Messages(prev. sec)", "A->B", "B->A", "RTT", "Max A->B",
								"Max B->A", "Max RTT")
						.addLine(sdf.format(cal.getTime()), messageNumberSum.toString(),
								(messagesSentInThePreviousSecond > 0 ? Integer.toString(messagesSentInThePreviousSecond)
										: "0"),
								formatDecimalValue(previousSecondTimeAToB), formatDecimalValue(previousSecondTimeBToA),
								formatDecimalValue(RTT), formatDecimalValue(maxAToB), formatDecimalValue(maxBToA),
								formatDecimalValue(maxRTT))
						.print();

			}
		};
		printTimer.scheduleAtFixedRate(printTask, 0, 1000);
	}

	private void sendAtFixedRate() {
		this.sendTask = new TimerTask() {
			public void run() {
				try {
					previousSecondTimeAToB = timeAToB;
					messageID = messageNumberSum;

					// startTime and endTime are used to calculate the
					// time needed to send a message from pitcher to
					// catcher
					startTime = getTimeInNanos();
					PrintWriter outWriter = new PrintWriter(socket.getOutputStream(), true);
					// Generating a message that consists of (messageSize) bytes
					message = buildOutputMessage(messageSize);
					outWriter.println(message);
					outWriter.flush();
					endTime = getTimeInNanos();

					timeAToB = (endTime - startTime) / 1e6;

					// Receiving message from the server
					BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					inputReader.readLine();
					String secondMessageFromServer = inputReader.readLine();
					// Parsing the string to only contain digits
					String digits = returnDigits(secondMessageFromServer);

					previousSecondTimeBToA = Double.parseDouble(digits.substring(0, 9));
					timeBToA = Double.parseDouble(digits.substring(8, digits.length()));

					// Calculating maximum values for message travels
					if (previousSecondTimeAToB > maxAToB) {
						maxAToB = previousSecondTimeAToB;
					}

					if (previousSecondTimeBToA > maxBToA) {
						maxBToA = previousSecondTimeBToA;
					}

					RTT = previousSecondTimeAToB + previousSecondTimeBToA;

					if (RTT > maxRTT) {
						maxRTT = RTT;
					}

				} catch (SocketException e) {
					System.out.println("Connection terminated by catcher!");
					endPrintTimerTask();
					endSendTimeTask();
				} catch (IOException e) {
					System.out.println("Message " + messageID + " lost! Server didn't respond.");
				}
				messageNumberSum += 1;
				messagesSentInThePreviousSecond = messageNumberSum - messagesPerSecond;
			}
		};
		sendTimer.schedule(sendTask, 200, period);
	}

	private long getTimeInNanos() {
		return System.nanoTime();
	}

	public String returnDigits(String string) {
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (Character.isDigit(c) || c == '.') {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	private void endPrintTimerTask() {
		printTimer.cancel();
		printTimer.purge();
	}

	private void endSendTimeTask() {
		sendTimer.cancel();
		sendTimer.purge();
	}

	public void printTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(cal.getTime()));
	}

	public String formatDecimalValue(Double value) {
		String returnValue = String.format(Locale.ROOT, "%.6f", value);
		return returnValue;
	}

	public String buildOutputMessage(Integer messageSize) {
		StringBuilder outputString = new StringBuilder();
		for (int i = 0; i < messageSize; i++) {
			outputString.append('/');
		}

		return outputString.toString();
	}

}

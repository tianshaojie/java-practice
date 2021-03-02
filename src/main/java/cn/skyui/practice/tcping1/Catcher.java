package cn.skyui.practice.tcping1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Locale;

public class Catcher {

	private double timeBToA = 0;
	private double previousSecondTimeBToA;
	private Integer messageSize;

	public Catcher() {
	}

	public void receiveMessage(String bindSocketAddress, Integer socketPortNumber) {
		// Opening a server socket for the server(catcher)
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(socketPortNumber);
			System.out.println("Waiting for a connection on port " + socketPortNumber + "...");
			Socket clientSocket = serverSocket.accept();

			// Checking if the connection was established on the socket
			if (clientSocket.isConnected()) {
				System.out.println("Connection established on " + bindSocketAddress + ":" + socketPortNumber);
			}

			while (true) {
				try {
					previousSecondTimeBToA = timeBToA;

					// Receiving the message from pitcher
					BufferedReader inputReader = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					String receivedMessage = inputReader.readLine();

					messageSize = receivedMessage.length();

					String responseString = buildResponseString(previousSecondTimeBToA, timeBToA, messageSize);

					// Sending the first message to calculate the time needed to
					// send the message back to
					// pitcher
					long startTime = getTimeInNanos();
					PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream(), true);
					outWriter.println(responseString);
					long endTime = getTimeInNanos();
					timeBToA = (endTime - startTime) / 1e6;

					// Returning the message with the same number of bytes
					outWriter.println(buildResponseString(previousSecondTimeBToA, timeBToA, messageSize));

				} catch (SocketTimeoutException | SocketException e) {
					System.out.println("Connection terminated by pitcher!");
					terminate();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {

			try {
				if (serverSocket != null) {
					serverSocket.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}

	private long getTimeInNanos() {
		long millis = System.nanoTime();
		return millis;
	}

	// Formatting the string for better output in pitcher mode
	public String buildResponseString(double previousSecondTimeBToA, double timeBToA, Integer messageSize) {
		StringBuilder builder = new StringBuilder();

		String firstResponseValue = String.format(Locale.ROOT, "%.6f", previousSecondTimeBToA);
		String secondResponseValue = String.format(Locale.ROOT, "%.6f", timeBToA);

		builder.append(firstResponseValue);
		builder.append(secondResponseValue);

		int builderLength = (builder.toString()).length();

		for (int i = 0; i < (messageSize - builderLength); i++) {
			builder.append('/');
		}
		return builder.toString();
	}

	public void terminate() {
		System.exit(0);
	}
}
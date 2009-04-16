package org.luchini.bgserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.luchini.bgserver.util.Config;
import org.luchini.bgserver.util.LogUtil;

public class BGServer {

	private static Logger logger = LogUtil.getLogger(BGServer.class);
	
	private int port;
	
	public static void main(String[] args) {
		logger.info("BGServer start up sequence initiated.");
		new BGServer();	
	}
	
	public BGServer() {
		this.port = Integer.parseInt( 
				Config.getInstance().getEntry(Config.PORT));
		
		Protocol protocol = new Protocol();
		
		ServerSocket serverSocket = null;
		try {
			logger.info("Starting server on port " + this.port);
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			logger.error("Could not listen to port " + this.port);
			System.exit(1);
		}

		logger.info("Server started up succesfully");
		while (true) {
			Socket clientSocket = null;
		    try {
		        clientSocket = serverSocket.accept();
		    } catch (IOException e) {
		    	logger.warn("Accept failed.");
		        continue;
		    }
		    BGServerThread thread = new BGServerThread(clientSocket, protocol);
		    thread.start();
		}
	}
}

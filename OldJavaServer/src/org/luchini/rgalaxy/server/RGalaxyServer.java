package org.luchini.rgalaxy.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.luchini.bgserver.server.Protocol;
import org.luchini.rgalaxy.engine.RGalaxyEngine;
import org.luchini.rgalaxy.util.Config;
import org.luchini.rgalaxy.util.LogUtil;

public class RGalaxyServer {
/*
	private static Logger logger = LogUtil.getLogger(RGalaxyServer.class);
	private static int PORT = Integer.parseInt(
			Config.getInstance().getEntry(Config.PORT));
	
	public static void main(String[] args) {
		
		RGalaxyEngine engine = new RGalaxyEngine();
		Protocol protocol = new Protocol(engine);
		
		ServerSocket serverSocket = null;
		try {
			logger.info("Starting server on port " + PORT);
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			logger.error("Could not listen to port " + PORT);
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
		    RGalaxyServerThread thread = new RGalaxyServerThread(clientSocket, protocol);
		    //engine.addListener(thread);
		    thread.start();
		}

	}
*/
}

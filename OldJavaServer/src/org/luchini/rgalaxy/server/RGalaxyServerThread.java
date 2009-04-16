package org.luchini.rgalaxy.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.events.RoomListChangeEvent;
import org.luchini.bgserver.engine.events.GameStateEvent;
import org.luchini.bgserver.engine.events.RoomInfoChangeEvent;
import org.luchini.bgserver.engine.events.SeatStateChangeEvent;
import org.luchini.bgserver.engine.listeners.RoomInfoStateChangeListener;
import org.luchini.bgserver.engine.listeners.RoomListStateChangeListener;
import org.luchini.bgserver.engine.listeners.GameStateChangeListener;
import org.luchini.bgserver.engine.listeners.SeatStateChangeListener;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.responses.BasicResponse;
import org.luchini.rgalaxy.util.LogUtil;

public class RGalaxyServerThread {//extends Thread implements RoomListStateChangeListener, RoomInfoStateChangeListener, GameStateChangeListener, SeatStateChangeListener {
/*
	private Socket socket = null;
	private Protocol protocol = null;
	
	private PlayerInfo playerInfo = null;

	private static Logger logger = LogUtil.getLogger(RGalaxyServerThread.class);
	
	private BufferedReader br;
	private PrintWriter pw;
	
	public RGalaxyServerThread(Socket socket, Protocol protocol) {
		this.playerInfo = new PlayerInfo();
		this.socket = socket;
		this.protocol = protocol;
		logger.info("New client connected from " + socket.getInetAddress() +
				". Player nickname associated to it: " + this.playerInfo.getNickname());
	}
	
	public void run() {
	    try {
	        this.br = new BufferedReader(
	                             new InputStreamReader(socket.getInputStream()));
	        this.pw = new PrintWriter(
	                         new BufferedOutputStream(socket.getOutputStream(), 1024), false);

	        String inputLine, outputLine;
	
	        outputLine = this.protocol.sendId();
	        pw.println(outputLine);
	        pw.flush();
	
	        boolean end = false;
	        while ((inputLine = br.readLine()) != null) {
	        	outputLine = this.protocol.processInput(this, inputLine);
	        	if (outputLine != null) {
	        		if (outputLine.indexOf(BasicResponse.BYE) > -1)
	        			end = true;
	        		respond (outputLine);
	        	}
	        	if (end)
	        		break;
	        }
	        logger.info("Client " + socket.getInetAddress() + " (" + playerInfo.getNickname() + ") quitted");
	        	
	    } catch (SocketException e) {
	    	logger.warn(playerInfo.getNickname() + " has lost connection. " + e);
	    	this.protocol.connectionLost(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	    	if (pw != null)
	    		pw.close();
	    	if (br != null)
	    		try {
					br.close();
				} catch (Exception e) {}
	    	if (socket != null)
	    		try {
	    			socket.close();
	    		} catch (Exception e) {}
	    	pw = null;
	    	br = null;
	    	socket = null;
	    	
	    }

	}
	
	private synchronized void respond(String output) {
		pw.println(output);
		pw.flush();
	}
	
	public PlayerInfo getPlayerInfo() {
		return this.playerInfo;
	}

	@Override
	public void gameListChanged(RoomListChangeEvent event) {
		String output = this.protocol.processEvent(this, event);
		this.respond(output);
	}

	@Override
	public void gameInfoChanged(RoomInfoChangeEvent event) {
		String output = this.protocol.processEvent(this, event);
		this.respond(output);		
	}

	@Override
	public void gameStateChanged(GameStateEvent event) {
		String output = this.protocol.processEvent(this, event);
		this.respond(output);		
	}

	@Override
	public void seatStateChangeEvent(SeatStateChangeEvent event) {
		String output = this.protocol.processEvent(this, event);
		this.respond(output);		
	}
*/
}

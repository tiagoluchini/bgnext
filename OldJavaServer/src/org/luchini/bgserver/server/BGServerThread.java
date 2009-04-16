package org.luchini.bgserver.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.events.RoomListChangeEvent;
import org.luchini.bgserver.engine.events.GameStateEvent;
import org.luchini.bgserver.engine.events.RoomInfoChangeEvent;
import org.luchini.bgserver.engine.events.SeatStateChangeEvent;
import org.luchini.bgserver.engine.listeners.RoomListStateChangeListener;
import org.luchini.bgserver.engine.listeners.GameStateChangeListener;
import org.luchini.bgserver.engine.listeners.RoomInfoStateChangeListener;
import org.luchini.bgserver.engine.listeners.SeatStateChangeListener;
import org.luchini.bgserver.server.responses.BasicResponse;
import org.luchini.bgserver.util.LogUtil;
import org.luchini.bgserver.util.Config;

public class BGServerThread extends Thread implements 
		RoomInfoStateChangeListener, GameStateChangeListener, 
		RoomListStateChangeListener, SeatStateChangeListener {
	
	private Socket socket = null;
	private Protocol protocol = null;
	
	private PlayerInfo playerInfo = null;
	private String protocolVersion;

	private static Logger logger = LogUtil.getLogger(BGServerThread.class);
	
	private Timer timer;
	private RoomListChangeEvent lastRoomListEvent;
	
	private BufferedReader br;
	private PrintWriter pw;
	private boolean end;
	
	public BGServerThread(Socket socket, Protocol protocol) {
		this.playerInfo = new PlayerInfo();
		this.socket = socket;
		this.protocol = protocol;
		this.timer = new Timer();
		logger.info("New client connected from " + socket.getInetAddress() +
				". Player nickname associated to it: " + this.playerInfo.getNickname());
	}
	
	public String getProtocolVersion() {
		return this.protocolVersion;
	}
	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	
	public void run() {
	    try {
	        this.br = new BufferedReader(
	        		new InputStreamReader(socket.getInputStream()));
	        this.pw = new PrintWriter(
	        		new BufferedOutputStream(socket.getOutputStream(), 1024), false);

	        String inputLine, outputLine;
	
	        outputLine = this.protocol.createIDMsg();
	        pw.println(outputLine);
	        pw.flush();
	        
	        this.end = false;
	        while ((inputLine = br.readLine()) != null) {
	        	if (inputLine.getBytes()[0] == 0)
	        		inputLine = inputLine.substring(1);
	        	outputLine = this.protocol.processInput(this, inputLine);
	        	if (outputLine != null) {
	        		if (outputLine.indexOf(BasicResponse.BYE) > -1 || 
	        				outputLine.equals(Protocol.FORCE_QUIT_SIGNAL))
	        			end = true;
	        		this.respond(outputLine);
	        	}
	        	if (this.end)
	        		break;
	        }
	        if (this.end == false) {
	        	// has ended correctly
	        	this.protocol.connectionLost(this);
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
	
	public void closeConnection() {
		this.end = true;
	}
	
	private synchronized void respond(String output) {
		char c = 0;
		pw.println(output + c);
		pw.flush();
	}
	
	public PlayerInfo getPlayerInfo() {
		return this.playerInfo;
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
	public void roomListChanged(RoomListChangeEvent event) {
		if (this.lastRoomListEvent == null) {
			this.timer.schedule(new ListEventContender(this),
					Long.parseLong(Config.getInstance().getEntry(Config.LISTCONTENDER_TIMEOUT)));
		}
		this.lastRoomListEvent = event;
	}

	public void respondLastListChanged() {
		String output = this.protocol.processEvent(this, this.lastRoomListEvent);
		this.lastRoomListEvent = null;
		this.respond(output);
	}

	@Override
	public void seatStateChangeEvent(SeatStateChangeEvent event) {
		String output = this.protocol.processEvent(this, event);
		this.respond(output);
	}

}

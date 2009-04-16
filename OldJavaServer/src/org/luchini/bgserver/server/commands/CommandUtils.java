package org.luchini.bgserver.server.commands;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.SeatState;
import org.luchini.bgserver.engine.ServerEngine;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.util.LogUtil;

public class CommandUtils {

	private Map<String, Command> commands;
	
	private static Logger logger = LogUtil.getLogger(CommandUtils.class);
	
	public CommandUtils() {}

	public CommandUtils(String[] commandClasses) {
		this.loadCommands(commandClasses);
	}
	
	public void loadCommands(String[] commandClasses) {
		this.commands = new TreeMap<String, Command>();
		for (String commandClass : commandClasses) {
			Command command = null;
			try {
				command = (Command)Class.forName(commandClass).newInstance();
			} catch (Exception e) {
				logger.error("Not able to instantiate Command: " + commandClass + " - " + e);
			}
			if (command != null)
				this.commands.put(command.command().toUpperCase(), command);
		}
	}
	
	public Command findCommand(String command) {
		return this.commands.get(command.toUpperCase());
	}
	
	public static void hookGameListeners(ServerEngine engine, BGServerThread thread, String uniqueID) {
		engine.getRoomInfo(uniqueID).addStateChangeListener(thread);
		engine.findGameState(uniqueID).addGameStateChangeListener(thread);
	}
	
	public static void unhookGameListeners(ServerEngine engine, BGServerThread thread, String uniqueID) {
		GameState game = engine.findGameState(uniqueID);
		if (game != null) {
			game.getRoomInfo().removeStateChangeListener(thread);
			game.removeGameStateChangeListener(thread);
		}
	}
	
	public static void closeEverythingDown(ServerEngine engine, BGServerThread thread) {
		engine.removeGameListStateChangeListener(thread);
		List<RoomInfo> rooms = engine.listPlayersRooms(thread.getPlayerInfo());
		if (rooms != null) {
			for (RoomInfo room : rooms) {
				CommandUtils.unhookGameListeners(engine, thread, room.getUniqueID());
				CommandUtils.unhookSeatStateListener(engine, thread, room.getUniqueID());
			}
		}
	}
	
	public static void hookSeatStateListener(ServerEngine engine, BGServerThread thread, String uniqueID) {
		RoomInfo room = engine.getRoomInfo(uniqueID);
		if (room != null) {
			SeatState seat = room.findSeatByPlayerHash(thread.getPlayerInfo().hashCode());
			if (seat != null) {
				seat.setStateChangeListener(thread);
			}
		}
	}
	
	public static void unhookSeatStateListener(ServerEngine engine, BGServerThread thread, String uniqueID) {
		RoomInfo room = engine.getRoomInfo(uniqueID);
		if (room != null) {
			if (room.getSeatStates() != null) {
				for (SeatState seat : room.getSeatStates()) {
					if (seat.getStateChangeListener().equals(thread)) {
						seat.setStateChangeListener(null);
					}
				}
			}
		}
	}
	
	public static boolean validateParams(ParamKit paramKit, String[] stringParams) {
		boolean out = true;
		int c=0;
		if (paramKit != null) {
			for (Param param : paramKit.getParams()) {
				if (stringParams != null && stringParams.length > c) {
					out = param.setValue(stringParams[c]);
				} else {
					out = !param.isMandatory();
				}
				if (!out) 
					break;
				c++;
			}
		}
		return out;
	}
}

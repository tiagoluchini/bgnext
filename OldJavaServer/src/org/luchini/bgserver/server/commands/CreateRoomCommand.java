package org.luchini.bgserver.server.commands;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.ErrorResponse;
import org.luchini.bgserver.server.responses.RoomInfoResponse;

public class CreateRoomCommand implements ServerCommand {

	@Override
	public String command() {
		return "CREATEROOM";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, 
			BGServerThread thread, String[] params) {
		AbstractResponse out = new ErrorResponse(this.command(), 
				ErrorResponse.INVALID_GAMEENGINE_CODE, ErrorResponse.INVALID_GAMEENGINE_MSG);
		GameEngine engine = protocol.findGameEngine(params[0]);
		if (engine != null) {
			RoomInfo roomInfo = protocol.getServerEngine().createRoom(engine, thread.getPlayerInfo());
			if (roomInfo != null) {
				List<RoomInfo> tmpRooms = new ArrayList<RoomInfo>(1);
				tmpRooms.add(roomInfo);
				out = new RoomInfoResponse(this.command(), RoomInfoResponse.SPECIFIC_ROOM,  tmpRooms);
				CommandUtils.hookGameListeners(protocol.getServerEngine(), thread, roomInfo.getUniqueID());
				CommandUtils.hookSeatStateListener(protocol.getServerEngine(), thread, roomInfo.getUniqueID());
			}
		}
		return out;
	}

	@Override
	public ParamKit getParamKit() {
		return new ParamKit().add(new Param(true));
	}

}

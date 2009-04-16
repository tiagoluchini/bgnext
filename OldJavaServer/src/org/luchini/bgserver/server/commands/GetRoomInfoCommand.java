package org.luchini.bgserver.server.commands;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.ErrorResponse;
import org.luchini.bgserver.server.responses.RoomInfoResponse;

public class GetRoomInfoCommand implements ServerCommand {

	@Override
	public String command() {
		return "GETROOMINFO";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		AbstractResponse out = new ErrorResponse(this.command(), 
				ErrorResponse.INVALID_ROOM_CODE, ErrorResponse.INVALID_ROOM_MSG);
		RoomInfo room = protocol.getServerEngine().getRoomInfo(params[0]);
		if (room != null) {
			List<RoomInfo> rooms = new ArrayList<RoomInfo>();
			rooms.add(room);
			out = new RoomInfoResponse(this.command(), 
					RoomInfoResponse.SPECIFIC_ROOM, rooms);
		}
		return out;
	}

	@Override
	public ParamKit getParamKit() {
		return new ParamKit().add(new Param(true));
	}

}

package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.RoomInfoResponse;

public class ListRoomsCommand implements ServerCommand {
	
	@Override
	public String command() {
		return "LISTROOMS";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		if (params == null) {
			return (new RoomInfoResponse(this.command(), RoomInfoResponse.ALL, 
					protocol.getServerEngine().listRooms()));
		} else {
			return (new RoomInfoResponse(this.command(), RoomInfoResponse.SPECIFIC_GAME, 
					protocol.getServerEngine().listRooms(params[0])));
		}
	}

	@Override
	public ParamKit getParamKit() {
		return new ParamKit().add(new Param(false));
	}

}

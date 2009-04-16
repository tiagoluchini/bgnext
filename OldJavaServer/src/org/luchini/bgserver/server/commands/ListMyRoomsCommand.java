package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.RoomInfoResponse;

public class ListMyRoomsCommand implements ServerCommand {

	@Override
	public String command() {
		return "LISTMYROOMS";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		return (new RoomInfoResponse(this.command(), RoomInfoResponse.PERSONAL, 
				protocol.getServerEngine().listPlayersRooms(thread.getPlayerInfo())));
	}

	@Override
	public ParamKit getParamKit() {
		return null;
	}

}

package org.luchini.bgserver.server.commands;

import java.util.List;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.ErrorResponse;
import org.luchini.bgserver.server.responses.PlayerInfoResponse;

public class ListRoomPlayersCommand implements ServerCommand {

	@Override
	public String command() {
		return "LISTROOMPLAYERS";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		AbstractResponse out = new ErrorResponse(this.command(), 
				ErrorResponse.INVALID_ROOM_CODE, ErrorResponse.INVALID_ROOM_MSG);
		List<PlayerInfo> players = protocol.getServerEngine().listRoomPlayers(params[0]);
		if (players != null) {
			out = new PlayerInfoResponse(this.command(), 
					PlayerInfoResponse.ROOM_PLAYERS, players);
		}
		return out;	
	}

	@Override
	public ParamKit getParamKit() {
		return new ParamKit().add(new Param(true));
	}

}

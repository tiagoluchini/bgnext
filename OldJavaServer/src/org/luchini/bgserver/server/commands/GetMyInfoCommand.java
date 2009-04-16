package org.luchini.bgserver.server.commands;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.PlayerInfoResponse;

public class GetMyInfoCommand implements ServerCommand {

	@Override
	public String command() {
		return "GETMYINFO";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		List<PlayerInfo> players = new ArrayList<PlayerInfo>();
		players.add(thread.getPlayerInfo());
		PlayerInfoResponse out = new PlayerInfoResponse(this.command(),
				PlayerInfoResponse.PERSONAL, players);
		return out;
	}

	@Override
	public ParamKit getParamKit() {
		return null;
	}

}

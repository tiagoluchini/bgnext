package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.BasicResponse;

public class QuitCommand implements ServerCommand {

	@Override
	public String command() {
		return "QUIT";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		BasicResponse out = new BasicResponse(this.command(), BasicResponse.BYE);
		protocol.getServerEngine().quit(thread.getPlayerInfo());
		CommandUtils.closeEverythingDown(protocol.getServerEngine(), thread);
		return out;
	}

	@Override
	public ParamKit getParamKit() {
		return null;
	}

}

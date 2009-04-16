package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.BasicResponse;

public class SetMyNickCommand implements ServerCommand {

	@Override
	public String command() {
		return "SETMYNICK";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		thread.getPlayerInfo().setNickname(params[0]);
		return new BasicResponse(this.command(), BasicResponse.OK);
	}

	@Override
	public ParamKit getParamKit() {
		return new ParamKit().add(new Param(true));
	}

}

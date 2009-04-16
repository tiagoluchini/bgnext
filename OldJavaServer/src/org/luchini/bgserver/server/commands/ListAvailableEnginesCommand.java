package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.AvailableEnginesResponse;

public class ListAvailableEnginesCommand implements ServerCommand {

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		AvailableEnginesResponse resp = new AvailableEnginesResponse(
				this.command(), protocol.listGameEngines());
		return resp;
	}

	@Override
	public String command() {
		return "LISTAVAILABLEENGINES";
	}

	@Override
	public ParamKit getParamKit() {
		return null;
	}

}

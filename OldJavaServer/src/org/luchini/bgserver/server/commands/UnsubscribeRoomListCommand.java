package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.BasicResponse;

public class UnsubscribeRoomListCommand implements ServerCommand {

	@Override
	public String command() {
		return "UNSUBSCRIBEROOMLIST";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		protocol.getServerEngine().removeGameListStateChangeListener(thread);
		return new BasicResponse(this.command(), BasicResponse.OK);
	}

	@Override
	public ParamKit getParamKit() {
		// TODO Auto-generated method stub
		return null;
	}

}

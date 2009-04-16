package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.BasicResponse;
import org.luchini.bgserver.server.responses.ErrorResponse;

public class SeatGameCommand implements ServerCommand {

	@Override
	public String command() {
		return "SEATGAME";
	}

	@Override
	public AbstractResponse exec(Protocol protocol, BGServerThread thread,
			String[] params) {
		AbstractResponse out = new ErrorResponse(this.command(), 
				ErrorResponse.INVALID_ROOM_CODE, ErrorResponse.INVALID_ROOM_MSG);
		if (protocol.getServerEngine().seatGame(thread.getPlayerInfo(), params[0])) {
			out = new BasicResponse(this.command(), BasicResponse.OK);
			CommandUtils.hookSeatStateListener(protocol.getServerEngine(), thread, params[0]);
		}
		return out;	
	}

	@Override
	public ParamKit getParamKit() {
		return new ParamKit().add(new Param(true));
	}

}

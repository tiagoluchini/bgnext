package org.luchini.rgalaxy.server.commands;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.commands.GameCommand;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.BasicResponse;
import org.luchini.bgserver.server.responses.ErrorResponse;
import org.luchini.rgalaxy.engine.RGalaxyEngine;

public class ChooseRoleCommand implements GameCommand {

	@Override
	public String command() {
		return "CHOOSEROLE";
	}

	@Override
	public AbstractResponse exec(GameEngine engine, GameState gameState,
			BGServerThread thread, String[] params) {
		RGalaxyEngine myEngine = (RGalaxyEngine)engine;
		AbstractResponse out = new ErrorResponse(this.command(), ErrorResponseCode.CANT_START_GAME_CODE,
				ErrorResponseCode.CANT_START_GAME_MSG);
		if (myEngine.chooseRole(thread.getPlayerInfo(), gameState, params[0])) {
			out = new BasicResponse(this.command(), BasicResponse.OK);
		}
		return out;
	}

	@Override
	public ParamKit getParamKit() {
		return new ParamKit().add(new Param(true));
	}


}

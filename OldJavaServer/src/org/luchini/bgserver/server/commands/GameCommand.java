package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.responses.AbstractResponse;

public interface GameCommand extends Command {

	public AbstractResponse exec(GameEngine engine, GameState gameState, BGServerThread thread, String[] params);

}

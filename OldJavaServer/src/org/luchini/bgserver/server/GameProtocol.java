package org.luchini.bgserver.server;

import org.luchini.bgserver.engine.GameState;

public interface GameProtocol {

	public String processCommand(GameState gameState, BGServerThread thread, 
			String command, String[] params);
	public void connectionLost(BGServerThread thread);
	
}

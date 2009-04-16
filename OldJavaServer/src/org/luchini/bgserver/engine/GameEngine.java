package org.luchini.bgserver.engine;

import org.luchini.bgserver.server.GameProtocol;

public interface GameEngine {

	public String getUniqueName();
	public String getName();
	public String getIconURL();
	public GameProtocol getGameProtocol();
	public GameState createGameState(String uniqueID, PlayerInfo hostPlayer);
	
}

package org.luchini.coloretto.engine;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.server.GameProtocol;

public class ColorettoEngine implements GameEngine {

	private GameProtocol protocol;
	
	public ColorettoEngine() {
		this.protocol = new ColorettoProtocol();
	}
	
	@Override
	public GameState createGameState(String uniqueID, PlayerInfo hostPlayer) {
		return new ColorettoGameState(this, uniqueID, hostPlayer);
	}

	@Override
	public GameProtocol getGameProtocol() {
		return this.protocol;
	}

	@Override
	public String getIconURL() {
		//TODO temporary
		return "http://images.boardgamegeek.com/images/pic211348_mt.jpg";
	}

	@Override
	public String getName() {
		return "Coloretto";
	}

	@Override
	public String getUniqueName() {
		return "COLORETTO";
	}

}

package org.luchini.coloretto.engine;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.RoomInfo;

public class ColorettoGameState extends GameState {

	public ColorettoGameState(GameEngine gameEngine, String uniqueID,
			PlayerInfo hostPlayer) {
		super(gameEngine, uniqueID, hostPlayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected RoomInfo createRoomInfo(GameEngine gameEngine, String uniqueID,
			PlayerInfo seatedPlayer, GameState parent) {
		return new ColorettoRoomInfo(gameEngine, uniqueID, seatedPlayer, this);
	}

}

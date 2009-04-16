package org.luchini.coloretto.engine;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.SeatState;

public class ColorettoRoomInfo extends RoomInfo {

	public ColorettoRoomInfo(GameEngine gameEngine, String uniqueID,
			PlayerInfo hostPlayer, GameState parent) {
		super(gameEngine, uniqueID, hostPlayer, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SeatState createSeatState(RoomInfo parent, PlayerInfo seatedPlayer) {
		return new ColorettoSeatState(this, seatedPlayer);
	}

	@Override
	public Object getGameSpecificData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int initialAvailableSeats() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	protected int initialMinimumSeats() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	protected void mantainGameSpecificData(Object gameSpecificData) {
		// TODO Auto-generated method stub

	}

}

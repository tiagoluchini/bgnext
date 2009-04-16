package org.luchini.coloretto.engine;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.SeatState;

public class ColorettoSeatState extends SeatState {

	public ColorettoSeatState(RoomInfo parent, PlayerInfo seatedPlayer) {
		super(parent, seatedPlayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getGameSpecificData() {
		// TODO Auto-generated method stub
		return null;
	}

}

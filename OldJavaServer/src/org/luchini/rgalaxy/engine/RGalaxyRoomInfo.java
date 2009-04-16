package org.luchini.rgalaxy.engine;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.SeatState;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode
public class RGalaxyRoomInfo extends RoomInfo {

	private RGalaxyRoomInfoData gameSpecificData;
	
	public RGalaxyRoomInfo(GameEngine gameEngine, String uniqueID,
			PlayerInfo hostPlayer, GameState parent) {
		super(gameEngine, uniqueID, hostPlayer, parent);
		this.setGameSpecificData(new RGalaxyRoomInfoData(GameType.NORMAL));
	}

	@Override
	protected SeatState createSeatState(RoomInfo parent, PlayerInfo seatedPlayer) {
		return new RGalaxySeatState(parent, seatedPlayer);
	}

	@Override
	protected int initialAvailableSeats() {
		return 4;
	}

	@Override
	protected int initialMinimumSeats() {
		return 2;
	}

	@Override
	public Object getGameSpecificData() {
		return this.gameSpecificData;
	}

	@Override
	protected void mantainGameSpecificData(Object gameSpecificData) {
		RGalaxyRoomInfoData data = (RGalaxyRoomInfoData)gameSpecificData;
		this.gameSpecificData = data;
	}
	
}
package org.luchini.rgalaxy.engine;

import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(attributesOnly=true)
public class RGalaxyRoomInfoData {

	@TreeAttribute private GameType gameType;
	
	public RGalaxyRoomInfoData(GameType gameType) {
		this.gameType = gameType;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

}

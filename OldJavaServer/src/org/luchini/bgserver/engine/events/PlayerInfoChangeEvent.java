package org.luchini.bgserver.engine.events;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(fixedAttributes={"type"},
		fixedAttributesValues={"event"})
public class PlayerInfoChangeEvent implements Event {

	@TreeNode
	private PlayerInfoData playerInfoData;
	
	public PlayerInfoChangeEvent(PlayerInfo playerInfo) {
		this.playerInfoData = PlayerInfoData.createPlayerInfoData(playerInfo);
	}
	
	@Override
	public String getReference() {
		return "PLAYERINFOCHANGE";
	}
	
	public PlayerInfoData getPlayerInfoData() {
		return this.playerInfoData;
	}

}

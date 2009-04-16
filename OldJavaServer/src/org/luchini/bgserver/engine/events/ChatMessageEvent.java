package org.luchini.bgserver.engine.events;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

public class ChatMessageEvent extends AbstractGameStateEvent {

	@TreeAttribute(alias="roomID") private RoomInfoData roomInfoData;
	@TreeNode private String message;
	@TreeNode private PlayerInfoData playerInfoData;
	
	public ChatMessageEvent(RoomInfo roomInfo, PlayerInfo playerInfo, String message) {
		super(roomInfo);
		this.playerInfoData = PlayerInfoData.createPlayerInfoData(playerInfo);
		this.message = message;
		this.roomInfoData = RoomInfoData.createRoomInfoData(roomInfo);
	}

	@Override
	public String getReference() {
		return "CHATMESSAGE:" + this.getRoomInfoData().getUniqueID();
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public PlayerInfoData getPlayerInfoData() {
		return this.playerInfoData;
	}

	@Override
	public RoomInfoData getRoomInfoData() {
		return this.roomInfoData;
	}

}

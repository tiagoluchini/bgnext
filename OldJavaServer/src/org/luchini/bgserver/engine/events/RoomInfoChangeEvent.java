package org.luchini.bgserver.engine.events;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

public class RoomInfoChangeEvent extends AbstractGameStateEvent {

	@TreeAttribute private RoomInfoChangeReason reason;
	@TreeAttribute private String targetRef;
	@TreeNode private RoomInfoData roomInfoData;
	
	public RoomInfoChangeEvent(RoomInfo roomInfo, RoomInfoChangeReason reason, String targetRef) {
		super(roomInfo);
		this.roomInfoData = RoomInfoData.createRoomInfoData(roomInfo);
		this.reason = reason;
		this.targetRef = targetRef;
	}

	@Override
	public String getReference() {
		return "ROOMINFOCHANGED:" + this.roomInfoData.getUniqueID();
	}
	
	public RoomInfoChangeReason getReason() {
		return this.reason;
	}
	
	public String getTargetRef() {
		return this.targetRef;
	}

	@Override
	public RoomInfoData getRoomInfoData() {
		return this.roomInfoData;
	}

}

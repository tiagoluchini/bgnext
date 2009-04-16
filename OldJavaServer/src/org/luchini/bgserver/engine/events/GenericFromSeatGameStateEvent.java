package org.luchini.bgserver.engine.events;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(fixedAttributes={"type"},
		fixedAttributesValues={"event"},
		attributesOnly=true)
public class GenericFromSeatGameStateEvent extends AbstractGameStateEvent {

	@TreeAttribute(alias="roomID") private RoomInfoData roomInfoData;
	@TreeAttribute private String reason;
	@TreeNode private SeatStateData seatStateData;
	
	public GenericFromSeatGameStateEvent(RoomInfo roomInfo, SeatStateData seatStateData, String reason) {
		super(roomInfo);
		this.roomInfoData = RoomInfoData.createRoomInfoData(roomInfo);
		this.seatStateData = seatStateData;
		this.reason = reason;
	}

	@Override
	public String getReference() {
		return "GENERICGAMESTATE:" + this.roomInfoData.getUniqueID();
	}

	@Override
	public RoomInfoData getRoomInfoData() {
		return this.roomInfoData;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	public SeatStateData getSeatInfo() {
		return this.seatStateData;
	}

}

package org.luchini.rgalaxy.engine.events;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.events.AbstractGameStateEvent;
import org.luchini.bgserver.engine.events.RoomInfoData;
import org.luchini.bgserver.engine.events.SeatStateData;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(fixedAttributes={"type"},
		fixedAttributesValues={"event"},
		attributesOnly=true)
public class HiddenCardMovementEvent extends AbstractGameStateEvent {

	public static final String DRAW_DECK = "DRAW_DECK";
	public static final String DISCARD_DECK = "DISCARD_DECK";
	public static final String PLAYER_HAND = "PLAYER_HAND";
	
	@TreeAttribute(alias="roomID")private RoomInfoData roomInfoData;
	@TreeAttribute private String from;
	@TreeAttribute private String to;
	@TreeAttribute private int qty;
	@TreeNode private SeatStateData seatStateData;
	
	public HiddenCardMovementEvent(RoomInfo roomInfo, String from, String to, 
			int qty, SeatStateData seatStateData) {
		super(roomInfo);
		this.roomInfoData = RoomInfoData.createRoomInfoData(roomInfo);
		this.from = from;
		this.to = to;
		this.qty = qty;
		this.seatStateData = seatStateData;
	}

	@Override
	public String getReference() {
		return "HIDDENCARDMOVEMENT:" + this.roomInfoData.getUniqueID();
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
	
	public int getQty() {
		return this.qty;
	}
	
	public SeatStateData getSeatInfo() {
		return this.seatStateData;
	}

	@Override
	public RoomInfoData getRoomInfoData() {
		return this.roomInfoData;
	}
	
}

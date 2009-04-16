package org.luchini.rgalaxy.engine.events;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.events.AbstractSeatStateEvent;
import org.luchini.treeview.annotations.TreeAttribute;

public class ExplorePhaseInitEvent extends AbstractSeatStateEvent {

	@TreeAttribute(alias="roomID") private RoomInfo roomInfo;
	@TreeAttribute private int keepQty;
	
	public ExplorePhaseInitEvent(RoomInfo roomInfo, int keepQty) {
		super(roomInfo);
		this.roomInfo = roomInfo;
		this.keepQty = keepQty;
	}

	@Override
	public String getReference() {
		return "EXPLOREPHASEINIT:" + this.roomInfo.getUniqueID();
	}
	
	public int getKeepQty() {
		return this.keepQty;
	}

	@Override
	public RoomInfo getRoomInfo() {
		return this.roomInfo;
	}
	
}
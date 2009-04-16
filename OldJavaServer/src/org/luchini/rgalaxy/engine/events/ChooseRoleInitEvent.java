package org.luchini.rgalaxy.engine.events;

import java.util.List;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.events.AbstractGameStateEvent;
import org.luchini.bgserver.engine.events.RoomInfoData;
import org.luchini.rgalaxy.engine.Phase;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

public class ChooseRoleInitEvent extends AbstractGameStateEvent {

	@TreeAttribute(alias="roomID") private RoomInfoData roomInfoData;
	@TreeAttribute private int tableVPs;
	@TreeNode private List<Phase> availablePhases;
	
	public ChooseRoleInitEvent(RoomInfo roomInfo, int tableVPs, List<Phase> availablePhases) {
		super(roomInfo);
		this.roomInfoData = RoomInfoData.createRoomInfoData(roomInfo);
		this.tableVPs = tableVPs;
		this.availablePhases = availablePhases;
	}

	@Override
	public String getReference() {
		return "CHOOSEROLEINIT:" + this.roomInfoData.getUniqueID();
	}
	
	public int getTableVPs() {
		return this.tableVPs;
	}
	
	public List<Phase> getAvailablePhases() {
		return this.availablePhases;
	}

	@Override
	public RoomInfoData getRoomInfoData() {
		return this.roomInfoData;
	}

}
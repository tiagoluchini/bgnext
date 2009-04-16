package org.luchini.rgalaxy.engine.events;

import java.util.List;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.events.AbstractGameStateEvent;
import org.luchini.bgserver.engine.events.RoomInfoData;
import org.luchini.rgalaxy.engine.SeatChosenRole;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

public class ExecutionInitEvent extends AbstractGameStateEvent {

	@TreeAttribute(alias="roomID") private RoomInfoData roomInfoData;
	@TreeNode private List<SeatChosenRole> chosenRoles;
	
	public ExecutionInitEvent(RoomInfo roomInfo, List<SeatChosenRole> chosenRoles) {
		super(roomInfo);
		this.roomInfoData = RoomInfoData.createRoomInfoData(roomInfo);
		this.chosenRoles = chosenRoles;
	}

	@Override
	public String getReference() {
		return "EXECUTIONINIT:" + this.roomInfoData.getUniqueID();
	}
	
	public List<SeatChosenRole> getChosenRoles() {
		return this.chosenRoles;
	}

	@Override
	public RoomInfoData getRoomInfoData() {
		return this.roomInfoData;
	}

}

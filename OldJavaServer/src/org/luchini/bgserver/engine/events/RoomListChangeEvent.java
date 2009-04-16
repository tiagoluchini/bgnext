package org.luchini.bgserver.engine.events;

import java.util.List;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(fixedAttributes={"type"},
		fixedAttributesValues={"event"})
public class RoomListChangeEvent implements Event {

	@TreeNode
	private List<RoomInfoData> rooms;
	
	public RoomListChangeEvent(List<RoomInfo> rooms) {
		this.rooms = RoomInfoData.convertList(rooms);
	}
	
	public List<RoomInfoData> getRoomListData() {
		return this.rooms;
	}
	
	@Override
	public String getReference() {
		return "GAMELISTCHANGE";
	}

}

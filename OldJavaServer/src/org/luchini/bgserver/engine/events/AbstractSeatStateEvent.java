package org.luchini.bgserver.engine.events;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(fixedAttributes={"type"},
		fixedAttributesValues={"event"})
public abstract class AbstractSeatStateEvent implements SeatStateChangeEvent {

	public AbstractSeatStateEvent(RoomInfo roomInfo) {}
	
}

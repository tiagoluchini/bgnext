package org.luchini.bgserver.engine.events;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(fixedAttributes={"type"},
		fixedAttributesValues={"event"})
public abstract class AbstractGameStateEvent implements GameStateEvent {
	
	public AbstractGameStateEvent(RoomInfo roomInfo) {}
	
}
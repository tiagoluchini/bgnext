package org.luchini.bgserver.engine.listeners;

import org.luchini.bgserver.engine.events.RoomListChangeEvent;

public interface RoomListStateChangeListener {

	public void roomListChanged(RoomListChangeEvent event);
	
}

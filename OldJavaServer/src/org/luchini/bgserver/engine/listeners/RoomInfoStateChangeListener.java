package org.luchini.bgserver.engine.listeners;

import org.luchini.bgserver.engine.events.RoomInfoChangeEvent;

public interface RoomInfoStateChangeListener {

	public void gameInfoChanged(RoomInfoChangeEvent event);
	
}

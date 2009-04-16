package org.luchini.bgserver.engine.listeners;

import org.luchini.bgserver.engine.events.PlayerInfoChangeEvent;

public interface PlayerInfoStateChangeListener {

	public void playerInfoChanged(PlayerInfoChangeEvent event);
	
}

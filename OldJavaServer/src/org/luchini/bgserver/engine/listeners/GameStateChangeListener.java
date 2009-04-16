package org.luchini.bgserver.engine.listeners;

import org.luchini.bgserver.engine.events.GameStateEvent;

public interface GameStateChangeListener {

	public void gameStateChanged(GameStateEvent event);
	
}

package org.luchini.bgserver.engine.listeners;

import org.luchini.bgserver.engine.events.SeatStateChangeEvent;

public interface SeatStateChangeListener {

	public void seatStateChangeEvent(SeatStateChangeEvent event);
	
}

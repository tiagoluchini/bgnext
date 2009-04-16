package org.luchini.bgserver.engine.events;

import org.luchini.bgserver.engine.RoomInfo;

public interface SeatStateChangeEvent extends Event {

	public RoomInfo getRoomInfo();

}

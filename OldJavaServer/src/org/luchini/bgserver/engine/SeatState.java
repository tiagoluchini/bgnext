package org.luchini.bgserver.engine;

import org.luchini.bgserver.engine.events.SeatStateChangeEvent;
import org.luchini.bgserver.engine.listeners.SeatStateChangeListener;

public abstract class SeatState {

	protected PlayerInfo seatedPlayer;
	
	protected RoomInfo parent;
	protected SeatStateChangeListener seatStateChangeListener;
	
	abstract public Object getGameSpecificData();
	
	public SeatState(RoomInfo parent, PlayerInfo seatedPlayer) {
		this.parent = parent;
		this.seatedPlayer = seatedPlayer;
	}
	
	public PlayerInfo getSeatedPlayer() {
		return seatedPlayer;
	}
	public void setSeatedPlayer(PlayerInfo playerInfo) {
		this.seatedPlayer = playerInfo;
	}
	
	
	public RoomInfo getParent() {
		return this.parent;
	}
	
	
	public SeatStateChangeListener getStateChangeListener() {
		return seatStateChangeListener;
	}
	public void setStateChangeListener(SeatStateChangeListener seatStateChangeListener) {
		this.seatStateChangeListener = seatStateChangeListener;
	}
	
	protected void informListener(SeatStateChangeEvent event) {
		if (this.seatStateChangeListener != null)
			this.seatStateChangeListener.seatStateChangeEvent(event);
	}

}

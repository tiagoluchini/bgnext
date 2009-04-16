package org.luchini.bgserver.engine;

import java.util.HashSet;
import java.util.Set;

import org.luchini.bgserver.engine.events.ChatMessageEvent;
import org.luchini.bgserver.engine.events.GameStateEvent;
import org.luchini.bgserver.engine.listeners.GameStateChangeListener;

public abstract class GameState {

	protected RoomInfo roomInfo;
	protected String lastChatLine;
	protected Set<GameStateChangeListener> gameStateChangeListeners;
	
	abstract protected RoomInfo createRoomInfo(GameEngine gameEngine, String uniqueID, 
			PlayerInfo seatedPlayer, GameState parent);
	
	public GameState(GameEngine gameEngine, String uniqueID, PlayerInfo hostPlayer) {
		super();
		this.roomInfo = this.createRoomInfo(gameEngine, uniqueID, hostPlayer, this);
	}
	

	public RoomInfo getRoomInfo() {
		return roomInfo;
	}


	public Set<GameStateChangeListener> getGameStateChangeListeners() {
		return gameStateChangeListeners;
	}


	public String getLastChatLine() {
		return lastChatLine;
	}
	public void setLastChatLine(PlayerInfo sender, String lastChatLine) {
		this.lastChatLine = lastChatLine;
		this.informGameStateChangeListeners(new ChatMessageEvent(this.roomInfo, sender, lastChatLine));
	}
	
	
	public void addGameStateChangeListener(GameStateChangeListener listener) {
		if (this.gameStateChangeListeners == null)
			this.gameStateChangeListeners = new HashSet<GameStateChangeListener>();
		this.gameStateChangeListeners.add(listener);
	}

	public void removeGameStateChangeListener(GameStateChangeListener listener) {
		if (this.gameStateChangeListeners != null)
			this.gameStateChangeListeners.remove(listener);
	}
	
	protected void informGameStateChangeListeners(GameStateEvent event) {
		if (this.gameStateChangeListeners != null) {
			for (GameStateChangeListener listener : this.gameStateChangeListeners) {
				listener.gameStateChanged(event);
			}
		}
	}

	
}

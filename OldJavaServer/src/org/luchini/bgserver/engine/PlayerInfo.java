package org.luchini.bgserver.engine;

import java.util.HashSet;
import java.util.Set;

import org.luchini.bgserver.engine.events.PlayerInfoChangeEvent;
import org.luchini.bgserver.engine.listeners.PlayerInfoStateChangeListener;
import org.luchini.bgserver.util.Config;

public class PlayerInfo {

	private String nickname;
	//private long recordID; (for persistence purposes)
	
	private Set<PlayerInfoStateChangeListener> stateChangeListeners;
	

	public PlayerInfo() {
		this.nickname = "Guest " + Config.getInstance().nextSequential("guest");
	}
	public PlayerInfo(String nickname) {
		this.nickname = nickname;
	}

	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
		this.informStateChangeListeners();
	}
	
	
	
	public void addStateChangeListener(PlayerInfoStateChangeListener listener) {
		if (this.stateChangeListeners == null)
			this.stateChangeListeners = new HashSet<PlayerInfoStateChangeListener>();
		this.stateChangeListeners.add(listener);
	}
	
	public void removeStateChangeListener(PlayerInfoStateChangeListener listener) {
		if (this.stateChangeListeners != null)
			this.stateChangeListeners.remove(listener);
	}

	private void informStateChangeListeners() {
		if (this.stateChangeListeners != null) {
			for (PlayerInfoStateChangeListener listener : this.stateChangeListeners) {
				listener.playerInfoChanged(new PlayerInfoChangeEvent(this));
			}
		}
	}
	
}

package org.luchini.rgalaxy.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.StatusType;
import org.luchini.bgserver.engine.events.RoomInfoChangeEvent;
import org.luchini.bgserver.engine.events.RoomInfoChangeReason;
import org.luchini.bgserver.engine.events.PlayerInfoChangeEvent;
import org.luchini.bgserver.engine.listeners.RoomInfoStateChangeListener;
import org.luchini.bgserver.engine.listeners.PlayerInfoStateChangeListener;
import org.luchini.rgalaxy.util.Config;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode
public class CopyOfGameInfo {//implements PlayerInfoStateChangeListener {
/*
	@TreeAttribute
	private String uniqueID;
	@TreeAttribute
	private String nickname;
	@TreeAttribute
	private GameType gameType;
	@TreeAttribute
	private int totalSeats = 4;
	@TreeAttribute
	private int seatsAvailable = 4;
	@TreeNode
	private List<PlayerInfo> players;
	@TreeAttribute
	private StatusType statusType;
	
	private Set<RoomInfoStateChangeListener> stateChangeListeners;
		
	public CopyOfGameInfo(String uniqueID, PlayerInfo hostPlayer) {
		this.init(uniqueID, hostPlayer);
	}
	
	public CopyOfGameInfo(String uniqueID, PlayerInfo hostPlayer, String nickname) {
		this.init(uniqueID, hostPlayer);
		this.nickname = nickname;
	}
	
	public CopyOfGameInfo(String uniqueID, PlayerInfo hostPlayer, GameType gameType) {
		this.init(uniqueID, hostPlayer);
		this.gameType = gameType;
	}
	
	public CopyOfGameInfo(String uniqueID, PlayerInfo hostPlayer, GameType gameType, String nickname) {
		this.init(uniqueID, hostPlayer);
		this.gameType = gameType;
		this.nickname = nickname;
	}
	
	private void init(String uniqueID, PlayerInfo hostPlayer) {
		this.nickname = "Game " + Config.getInstance().nextSequential("game");
		this.gameType = GameType.NORMAL;
		this.statusType = StatusType.WAITING_PLAYERS;
		this.players = new ArrayList<PlayerInfo>();
		this.players.add(hostPlayer);
		hostPlayer.addStateChangeListener(this);
		this.uniqueID = uniqueID;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
		this.informStateChange(RoomInfoChangeReason.NICKNAME_CHANGED, nickname);
	}
	public GameType getGameType() {
		return gameType;
	}
	public void setGameType(GameType gameType) {
		this.gameType = gameType;
		this.informStateChange(RoomInfoChangeReason.GAMETYPE_CHANGED, gameType.toString());
	}
	public List<PlayerInfo> getPlayers() {
		return players;
	}
	public void addPlayer(PlayerInfo player) {
		this.players.add(player);
		player.addStateChangeListener(this);
		this.informStateChange(RoomInfoChangeReason.NEW_PLAYER_ENTERED, player.getNickname());
	}
	public void removePlayer(PlayerInfo player) {
		this.players.remove(player);
		player.removeStateChangeListener(this);
		this.informStateChange(RoomInfoChangeReason.PLAYER_QUITED, player.getNickname());
	}
	public StatusType getStatusType() {
		return statusType;
	}
	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
		this.informStateChange(RoomInfoChangeReason.STATUS_CHANGED, statusType.toString());
	}
	public String getUniqueID() {
		return this.uniqueID;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public int getSeatsAvailable() {
		return seatsAvailable;
	}
	public void takeSeat() {
		this.seatsAvailable--;
		this.informStateChange(RoomInfoChangeReason.SEAT_TAKEN, 
				this.totalSeats - this.seatsAvailable + "/" + this.totalSeats);
	}
	public void freeSeat() {
		this.seatsAvailable++;
		this.informStateChange(RoomInfoChangeReason.SEAT_LEFT, 
				this.totalSeats - this.seatsAvailable + "/" + this.totalSeats);
	}

	public void addStateChangeListener(RoomInfoStateChangeListener listener) {
		if (this.stateChangeListeners == null)
			this.stateChangeListeners = new HashSet<RoomInfoStateChangeListener>();
		this.stateChangeListeners.add(listener);
	}
	
	public void removeStateChangeListener(RoomInfoStateChangeListener listener) {
		if (this.stateChangeListeners != null)
			this.stateChangeListeners.remove(listener);
	}
	
	private void informStateChange(RoomInfoChangeReason reason, String targetRef) {
		if (this.stateChangeListeners != null) {
			for (RoomInfoStateChangeListener listener : this.stateChangeListeners) {
				listener.gameInfoChanged(new RoomInfoChangeEvent(this, reason, targetRef));
			}
		}
	}

	@Override
	public void playerInfoChanged(PlayerInfoChangeEvent event) {
		this.informStateChange(RoomInfoChangeReason.PLAYERINFO_CHANGED, event.getPlayerInfo().getNickname());
	}

	@Override
	public String toString() {
		return this.uniqueID;
	}	
*/
}

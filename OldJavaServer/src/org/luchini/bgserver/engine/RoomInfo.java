package org.luchini.bgserver.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.luchini.bgserver.engine.events.GameEngineData;
import org.luchini.bgserver.engine.events.RoomInfoChangeEvent;
import org.luchini.bgserver.engine.events.RoomInfoChangeReason;
import org.luchini.bgserver.engine.events.PlayerInfoChangeEvent;
import org.luchini.bgserver.engine.listeners.PlayerInfoStateChangeListener;
import org.luchini.bgserver.engine.listeners.RoomInfoStateChangeListener;
import org.luchini.bgserver.util.Config;

public abstract class RoomInfo implements PlayerInfoStateChangeListener {

	abstract protected int initialAvailableSeats();
	abstract protected int initialMinimumSeats();
	
	abstract protected SeatState createSeatState(RoomInfo parent, PlayerInfo seatedPlayer);
	abstract public Object getGameSpecificData();
	abstract protected void mantainGameSpecificData(Object gameSpecificData);
	
	protected String uniqueID;
	protected String nickname;
	protected int totalSeats;
	protected int seatsAvailable;
	protected int minimumSeatedToStart;
	protected StatusType statusType;
	
	protected GameEngineData gameEngineData; 
	protected List<PlayerInfo> players;
	protected List<SeatState> seatStates;
	
	protected GameEngine gameEngine;
	protected GameState parent;
	protected Set<RoomInfoStateChangeListener> stateChangeListeners;

	
	public RoomInfo(GameEngine gameEngine, String uniqueID, PlayerInfo hostPlayer, GameState parent) {
		this.parent = parent;
		this.nickname = "Game " + Config.getInstance().nextSequential("game");
		this.statusType = StatusType.WAITING_PLAYERS;
		this.players = new ArrayList<PlayerInfo>();
		this.players.add(hostPlayer);
		hostPlayer.addStateChangeListener(this);
		this.uniqueID = uniqueID;
		this.totalSeats = this.initialAvailableSeats();
		this.seatsAvailable = this.initialAvailableSeats();
		this.minimumSeatedToStart = this.initialMinimumSeats();
		this.gameEngineData = GameEngineData.createGameEngineData(gameEngine);
		this.gameEngine = gameEngine;
	}	

	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
		this.informStateChange(RoomInfoChangeReason.NICKNAME_CHANGED, nickname);
	}


	public GameState getParent() {
		return this.parent;
	}
	
	
	public GameEngineData getGameEngineInfo() {
		return this.gameEngineData;
	}
	
	
	public GameEngine getGameEngine() {
		return this.gameEngine;
	}
	
	
	public void setGameSpecificData(Object gameSpecificData) {
		this.mantainGameSpecificData(gameSpecificData);
		this.informStateChange(RoomInfoChangeReason.GAME_SPECIFC_DATA_CHANGED, gameSpecificData.toString());
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
	
	
	public List<SeatState> getSeatStates() {
		return this.seatStates;
	}
	public synchronized void seatPlayer(PlayerInfo player) {
		if (this.seatStates == null)
			this.seatStates = new ArrayList<SeatState>();
		SeatState seat = this.createSeatState(this, player);
		this.seatStates.add(seat);
		this.seatsAvailable--;
		this.informStateChange(RoomInfoChangeReason.PLAYER_SEATED, player.getNickname());
	}
	public synchronized void unseatPlayer(PlayerInfo player) {
		if (this.seatStates != null) {
			SeatState seatState = this.findSeatByPlayerHash(player.hashCode());
			if (seatState != null) {
				seatState.setSeatedPlayer(null);
				this.seatsAvailable++;
				this.informStateChange(RoomInfoChangeReason.PLAYER_LEFT, player.getNickname());
			}
		}
	}
	public synchronized void reseatPlayer(PlayerInfo player, int seatHash) {
		if (this.seatStates != null) {
			SeatState seatState = this.findSeatBySeatHash(seatHash);
			if (seatState != null) {
				seatState.setSeatedPlayer(player);
				this.seatsAvailable--;
				this.informStateChange(RoomInfoChangeReason.PLAYER_RESEATED, player.getNickname());
			}
		}
	}
	public synchronized void removeEmptySeats() {
		List<SeatState> toRetain = new ArrayList<SeatState>(); 
		for (SeatState seat : this.seatStates) {
			if (seat.getSeatedPlayer() != null)
				toRetain.add(seat);
		}
		this.seatStates.retainAll(toRetain);
	}

	public boolean canStart(PlayerInfo playerInfo) {
		boolean out = false;
		if (this.statusType.equals(StatusType.WAITING_PLAYERS) &&
				this.players.get(0).equals(playerInfo)) {
			if (this.seatStates != null) {
				out = (this.listOccupiedSeatStates().size() > this.minimumSeatedToStart);
			}
		}
		return out;
	}
	
	public SeatState findSeatByPlayerHash(int playerHash) {
		SeatState out = null;
		if (this.seatStates != null) {
			for (SeatState seatState : this.seatStates) {
				if (seatState.getSeatedPlayer() != null &&
						seatState.getSeatedPlayer().hashCode() == playerHash) {
					out = seatState;
					break;
				}
			}
		}
		return out;
	}
	
	public SeatState findSeatBySeatHash(int seatHash) {
		SeatState out = null;
		if (this.seatStates != null) {
			for (SeatState seatState : this.seatStates) {
				if (seatState.hashCode() == seatHash) {
					out = seatState;
					break;
				}
			}
		}
		return out;		
	}
	
	public List<SeatState> listAvailableSeatStates() {
		List<SeatState> out = null;
		if (this.seatStates != null) {
			out = new ArrayList<SeatState>();
			for (SeatState seatState : this.seatStates) {
				if (seatState.getSeatedPlayer() == null) {
					out.add(seatState);
				}
			}
		}
		return out;		
	}
	
	public List<SeatState> listOccupiedSeatStates() {
		List<SeatState> out = null;
		if (this.seatStates != null) {
			out = new ArrayList<SeatState>();
			for (SeatState seatState : this.seatStates) {
				if (seatState.getSeatedPlayer() != null) {
					out.add(seatState);
				}
			}
		}
		return out;		
	}
	
	public PlayerInfo findPlayerInfoByPlayerHash(int playerHash) {
		PlayerInfo out = null;
		if (this.players != null) {
			for (PlayerInfo playerInfo : this.players) {
				if (playerInfo.hashCode() == playerHash) {
					out = playerInfo;
					break;
				}
			}
		}
		return out;
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
	
	
	public int getMinimumSeatedToStart() {
		return minimumSeatedToStart;
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
	
	protected void informStateChange(RoomInfoChangeReason reason, String targetRef) {
		if (this.stateChangeListeners != null) {
			for (RoomInfoStateChangeListener listener : this.stateChangeListeners) {
				listener.gameInfoChanged(new RoomInfoChangeEvent(this, reason, targetRef));
			}
		}
	}

	@Override
	public void playerInfoChanged(PlayerInfoChangeEvent event) {
		this.informStateChange(RoomInfoChangeReason.PLAYERINFO_CHANGED, event.getPlayerInfoData().getNickname());
	}

	@Override
	public String toString() {
		return this.uniqueID;
	}	

	
}

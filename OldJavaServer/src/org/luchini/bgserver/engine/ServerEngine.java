package org.luchini.bgserver.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.events.RoomListChangeEvent;
import org.luchini.bgserver.engine.events.RoomInfoChangeEvent;
import org.luchini.bgserver.engine.listeners.RoomListStateChangeListener;
import org.luchini.bgserver.engine.listeners.RoomInfoStateChangeListener;
import org.luchini.bgserver.util.Config;
import org.luchini.bgserver.util.LogUtil;

public class ServerEngine implements RoomInfoStateChangeListener {

	private Map<String, GameState> games;
	private Set<RoomListStateChangeListener> roomListStateChangeListeners;
	private Map<Integer, String> filteredListeners;
	
	private static Logger logger = LogUtil.getLogger(ServerEngine.class);
	
	public ServerEngine() {
		logger.debug("Starting up BGServer Engine");
		this.games = new LinkedHashMap<String, GameState>();
	}
			
	public RoomInfo createRoom(GameEngine gameEngine, PlayerInfo hostPlayer) {
		GameState game = gameEngine.createGameState(Config.uniqueID(), hostPlayer);
		this.games.put(game.getRoomInfo().getUniqueID(), game);
		game.getRoomInfo().addStateChangeListener(this);
		this.informGameListStateListeners(game.getRoomInfo().getGameEngineInfo().getUniqueName());
		return game.getRoomInfo();
	}
	
	public List<RoomInfo> listRooms() {
		List<RoomInfo> out = null;
		if (this.games != null) {
			out = new ArrayList<RoomInfo>();
			Collection<GameState> states = this.games.values();
			for(GameState gameState : states) {
				out.add(gameState.getRoomInfo());
			}
		}
		return out;
	}
	
	public List<RoomInfo> listRooms(String engineUniqueName) {
		List<RoomInfo> out = null;
		if (this.games != null) {
			out = new ArrayList<RoomInfo>();
			Collection<GameState> states = this.games.values();
			for(GameState gameState : states) {
				String tmpUnique = gameState.getRoomInfo().getGameEngineInfo().getUniqueName(); 
				if (tmpUnique.equals(engineUniqueName)) {
					out.add(gameState.getRoomInfo());
				}
			}
		}
		return out;		
	}
	
	public List<RoomInfo> listPlayersRooms(PlayerInfo playerInfo) {
		List<RoomInfo> out = null;
		if (this.games != null) {
			out = new ArrayList<RoomInfo>();
			Collection<GameState> states = this.games.values();
			for(GameState gameState : states) {
				if (gameState.getRoomInfo().getPlayers().contains(playerInfo)) {
					out.add(gameState.getRoomInfo());
				}
			}
		}
		return out;
	}
	
	public List<PlayerInfo> listRoomPlayers(String uniqueID) {
		List<PlayerInfo> out = null;
		GameState game = this.games.get(uniqueID);
		if (game != null) {
			out = new ArrayList<PlayerInfo>();
			for (PlayerInfo playerInfo : game.getRoomInfo().getPlayers()) {
				out.add(playerInfo);
			}
		}
		return out;
	}
	
	synchronized public boolean quitRoom(PlayerInfo playerInfo, String uniqueID) {
		boolean out = false;
		GameState game = this.games.get(uniqueID);
		logger.debug("Trying to quit game");
		if (game != null) {
			logger.debug("Game " + game.getRoomInfo().getNickname() + " found");
			List<PlayerInfo> players = game.getRoomInfo().getPlayers();
			int size = players.size();
			if (players.contains(playerInfo)) {
				logger.debug("Player " + playerInfo.getNickname() + " is in the game room");
				SeatState seatState = game.getRoomInfo().findSeatByPlayerHash(playerInfo.hashCode());
				if (seatState != null) {
					logger.debug("Player " + playerInfo.getNickname() + " must leave seat too");
					this.leaveGame(playerInfo, uniqueID);
				}
				game.getRoomInfo().removePlayer(playerInfo);
				if (size == 1) {
					String engineUniqueName = game.getRoomInfo().getGameEngineInfo().getUniqueName();
					logger.debug("Player was the last one standing so, destroy the game session");
					game.getRoomInfo().removeStateChangeListener(this);
					this.games.remove(uniqueID);
					// needs to inform subscribers that it was destroyed
					this.informGameListStateListeners(engineUniqueName);
				}
				out = true;
			}
		}
		return out;
	}
	
	synchronized public boolean enterRoom(PlayerInfo playerInfo, String uniqueID) {
		boolean out = false;
		GameState game = this.games.get(uniqueID);
		if (game != null) {
			if (!game.getRoomInfo().getPlayers().contains(playerInfo)) {
				game.getRoomInfo().addPlayer(playerInfo);
				out = true;
			}
		}
		return out;
	}
	
	public synchronized boolean seatGame(PlayerInfo playerInfo, String uniqueID) {
		boolean out = false;
		GameState game = this.games.get(uniqueID);
		if (game != null) {
			List<PlayerInfo> players = game.getRoomInfo().getPlayers();
			if (players.contains(playerInfo)) {
				boolean alreadySeated = (game.getRoomInfo().findSeatByPlayerHash(playerInfo.hashCode())!=null);
				if (!alreadySeated) {
					StatusType statusType = game.getRoomInfo().getStatusType();
					if (statusType.equals(StatusType.WAITING_PLAYERS)) {
						if (game.getRoomInfo().getSeatsAvailable() > 0) {
							game.getRoomInfo().seatPlayer(playerInfo);
						}
						out = true;
					}
				}
			}
		}
		return out;
	}
	
	public synchronized boolean leaveGame(PlayerInfo playerInfo, String uniqueID) {
		boolean out = false;
		GameState game = this.games.get(uniqueID);
		if (game != null) {
			List<PlayerInfo> players = game.getRoomInfo().getPlayers();
			if (players.contains(playerInfo)) {
				boolean alreadySeated = (game.getRoomInfo().findSeatByPlayerHash(playerInfo.hashCode())!=null);
				if (alreadySeated) {
					game.getRoomInfo().unseatPlayer(playerInfo);
					if (game.getRoomInfo().getStatusType().equals(StatusType.UNDER_WAY)) {
						game.getRoomInfo().setStatusType(StatusType.REPLACEMENT_NEEDED);
					}
					if (game.getRoomInfo().getStatusType().equals(StatusType.WAITING_PLAYERS)) {
						game.getRoomInfo().removeEmptySeats();
					}
					out = true;
				}
			}
		}
		return out;
	}	
	
	public boolean setRoomNick(PlayerInfo playerInfo, String uniqueID, String newNick) {
		boolean out = false;
		GameState state = this.games.get(uniqueID);
		if (state != null && 
				state.getRoomInfo().getPlayers().get(0).equals(playerInfo)) {
			state.getRoomInfo().setNickname(newNick);
			out = true;
		}
		return out;
	}
	
	public void quit(PlayerInfo playerInfo) {
		List<RoomInfo> myRooms = this.listPlayersRooms(playerInfo);
		logger.debug("Quit requested for player " + playerInfo.getNickname());
		if (myRooms != null) {
			for (RoomInfo room : myRooms) {
				logger.debug("Quitting player from game " + room.getNickname());
				this.quitRoom(playerInfo, room.getUniqueID());
			}
		}
	}
	
	public void connectionLost(PlayerInfo playerInfo) {
		this.quit(playerInfo);
	}
	
	public RoomInfo getRoomInfo(String uniqueID) {
		RoomInfo out = null;
		GameState game = this.games.get(uniqueID);
		if (game != null)
			out = game.getRoomInfo();
		return out;
	}
	
	public GameState findGameState(String uniqueID) {
		return this.games.get(uniqueID);
	}
	
	/* ---------------- */
	
	public void addGameListStateChangeListener(RoomListStateChangeListener listener) {
		if (this. roomListStateChangeListeners == null)
			this.roomListStateChangeListeners = new HashSet<RoomListStateChangeListener>();
		this.roomListStateChangeListeners.add(listener);
		this.informSpecificListener(listener, null);
	}
	
	public void addGameListStateChangeListener(RoomListStateChangeListener listener, 
			String gameEngineUnique) {
		this.addGameListStateChangeListener(listener);
		if (this.filteredListeners == null)
			this.filteredListeners = new TreeMap<Integer, String>();
		this.filteredListeners.put(new Integer(listener.hashCode()), gameEngineUnique);
		this.informSpecificListener(listener, gameEngineUnique);
	}

	public void removeGameListStateChangeListener(RoomListStateChangeListener listener) {
		if (this.roomListStateChangeListeners != null)
			this.roomListStateChangeListeners.remove(listener);
		if (this.filteredListeners != null)
			this.filteredListeners.remove(new Integer(listener.hashCode()));
	}
	
	private void informGameListStateListeners(String gameEngineUnique) {
		if (this.roomListStateChangeListeners != null) {
			for (RoomListStateChangeListener listener : this.roomListStateChangeListeners) {
				this.informSpecificListener(listener, gameEngineUnique);
			}
		}
	}
	
	private void informSpecificListener(RoomListStateChangeListener listener, String gameEngineUnique) {
		String filtered = null;
		if (gameEngineUnique != null && this.filteredListeners != null)
			filtered = this.filteredListeners.get(new Integer(listener.hashCode()));
		if (filtered != null) {
			if (filtered.equals(gameEngineUnique)) {
				listener.roomListChanged(new RoomListChangeEvent(
						this.listRooms(gameEngineUnique)));
			}
		} else {
			listener.roomListChanged(new RoomListChangeEvent(this.listRooms()));
		}

	}

	@Override
	public void gameInfoChanged(RoomInfoChangeEvent event) {
		this.informGameListStateListeners(event.getRoomInfoData().getGameEngineData().getUniqueName());
	}
	
	/* ---------------- */

	public boolean say(PlayerInfo playerInfo, String uniqueID, String message) {
		boolean out = false;
		GameState game = this.games.get(uniqueID);
		if (game != null) {
			if(game.getRoomInfo().getPlayers().contains(playerInfo)) {
				game.setLastChatLine(playerInfo, message);
				out = true;
			}
		}
		return out;
	}
	
	/* ---------------- */
	
}
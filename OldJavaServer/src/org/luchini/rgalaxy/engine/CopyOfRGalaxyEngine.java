package org.luchini.rgalaxy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.StatusType;
import org.luchini.bgserver.engine.events.AbstractSeatStateEvent;
import org.luchini.bgserver.engine.events.RoomListChangeEvent;
import org.luchini.bgserver.engine.events.RoomInfoChangeEvent;
import org.luchini.bgserver.engine.listeners.RoomInfoStateChangeListener;
import org.luchini.bgserver.engine.listeners.RoomListStateChangeListener;
import org.luchini.bgserver.engine.listeners.GameStateChangeListener;
import org.luchini.bgserver.engine.listeners.SeatStateChangeListener;
import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.deck.DeckUtil;
import org.luchini.rgalaxy.deck.ExploreBehaviour;
import org.luchini.rgalaxy.deck.enums.SetType;
import org.luchini.rgalaxy.engine.events.ExplorePhaseInitEvent;
import org.luchini.rgalaxy.util.Config;
import org.luchini.rgalaxy.util.LogUtil;

public class CopyOfRGalaxyEngine {//implements RoomInfoStateChangeListener {
/*
	private Map<String, RGalaxyGameState> games;
	private Set<RoomListStateChangeListener> gameListStateChangeListeners;
	
	private static Logger logger = LogUtil.getLogger(CopyOfRGalaxyEngine.class);
	
	public CopyOfRGalaxyEngine() {
		logger.debug("Starting up RGalaxyEngine");
		this.games = new TreeMap<String, RGalaxyGameState>();
	}
	
	public RGalaxyRoomInfo createGame(PlayerInfo playerInfo, String nickname, GameType gametype) {
		RGalaxyRoomInfo info = new RGalaxyRoomInfo(Config.uniqueID(), playerInfo, nickname);
		this.createBaseGame(info, gametype);
		return info;
	}
	
	public RGalaxyRoomInfo createGame(PlayerInfo playerInfo, String nickname) {
		RGalaxyRoomInfo info = new RGalaxyRoomInfo(Config.uniqueID(), playerInfo, nickname);
		this.createBaseGame(info, GameType.NORMAL);
		return info;
	}
	
	public RGalaxyRoomInfo createGame(PlayerInfo playerInfo) {
		RGalaxyRoomInfo info = new RGalaxyRoomInfo(Config.uniqueID(), playerInfo);
		this.createBaseGame(info, GameType.NORMAL);
		return info;
	}
	
	private void createBaseGame(RGalaxyRoomInfo gameInfo, GameType gametype) {
		List<Card> deck = DeckUtil.createNewShuffledDeck(SetType.BASE);
		this.games.put(gameInfo.getUniqueID(), new RGalaxyGameState(gameInfo, deck));
		this.addGameInfoStateChangeListener(gameInfo.getUniqueID(), this);
		this.informGameListStateListeners();
	}
	
	public List<RGalaxyRoomInfo> listGames() {
		List<RGalaxyRoomInfo> out = null;
		if (this.games.size() != 0) {
			out = new ArrayList<RGalaxyRoomInfo>();
			Collection<RGalaxyGameState> states = this.games.values();
			for(RGalaxyGameState gameState : states) {
				out.add(gameState.getGameInfo());
			}
		}
		return out;
	}
	
	public List<RGalaxyRoomInfo> listPlayersGame(PlayerInfo playerInfo) {
		List<RGalaxyRoomInfo> out = null;
		if (this.games.size() != 0) {
			out = new ArrayList<RGalaxyRoomInfo>();
			Collection<RGalaxyGameState> states = this.games.values();
			for(RGalaxyGameState gameState : states) {
				if (gameState.getGameInfo().getPlayers().contains(playerInfo)) {
					out.add(gameState.getGameInfo());
				}
			}
		}
		return out;
	}
	
	public List<PlayerInfo> listPlayers(String uniqueID) {
		List<PlayerInfo> out = null;
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null) {
			out = new ArrayList<PlayerInfo>();
			for (PlayerInfo playerInfo : game.getGameInfo().getPlayers()) {
				out.add(playerInfo);
			}
		}
		return out;
	}
	
	synchronized public boolean quitGame(PlayerInfo playerInfo, String uniqueID) {
		boolean out = false;
		RGalaxyGameState game = this.games.get(uniqueID);
		logger.debug("Trying to quit game");
		if (game != null) {
			logger.debug("Game " + game.getGameInfo().getNickname() + " found");
			List<PlayerInfo> players = game.getGameInfo().getPlayers();
			int size = players.size();
			if (players.contains(playerInfo)) {
				logger.debug("Player " + playerInfo.getNickname() + " is in the game room");
				RGalaxySeatState playerState = game.findPlayerStateByPlayerHash(playerInfo.hashCode());
				if (playerState != null) {
					logger.debug("Player " + playerInfo.getNickname() + " must leave seat too");
					this.leaveGame(playerInfo, uniqueID);
				}
				game.getGameInfo().removePlayer(playerInfo);
				if (size == 1) {
					logger.debug("Player was the last standing so, destroy the game session");
					this.games.remove(uniqueID);
					// needs to inform subscribers that it was destroyed
					this.informGameListStateListeners(); 
				}
				out = true;
			}
		}
		return out;
	}
	
	synchronized public boolean enterGame(PlayerInfo playerInfo, String uniqueID) {
		boolean out = false;
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null) {
			if (!game.getGameInfo().getPlayers().contains(playerInfo)) {
				game.getGameInfo().addPlayer(playerInfo);
				out = true;
			}
		}
		return out;
	}
	
	public synchronized boolean joinGame(PlayerInfo playerInfo, String uniqueID) {
		boolean out = false;
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null) {
			List<PlayerInfo> players = game.getGameInfo().getPlayers();
			if (players.contains(playerInfo)) {
				boolean alreadySeated = (game.findPlayerStateByPlayerHash(playerInfo.hashCode())!=null);
				if (!alreadySeated) {
					StatusType statusType = game.getGameInfo().getStatusType();					
					if (statusType.equals(StatusType.WAITING_PLAYERS)) {
						if (game.getGameInfo().getSeatsAvailable() > 0) {
							game.seatPlayer(playerInfo);
							game.getGameInfo().takeSeat();
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
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null) {
			List<PlayerInfo> players = game.getGameInfo().getPlayers();
			if (players.contains(playerInfo)) {
				boolean alreadySeated = (game.findPlayerStateByPlayerHash(playerInfo.hashCode())!=null);
				if (alreadySeated) {
					game.unseatPlayer(playerInfo);
					game.getGameInfo().freeSeat();
					if (game.getGameInfo().getStatusType().equals(StatusType.UNDER_WAY)) {
						game.getGameInfo().setStatusType(StatusType.REPLACEMENT_NEEDED);
					}
					out = true;
				}
			}
		}
		return out;
	}
	
	public boolean startGame(PlayerInfo playerInfo, String uniqueID) {
		boolean out = false;
		RGalaxyGameState game = this.games.get(uniqueID);
		if ((game != null) && 
				(game.getGameInfo().getPlayers().get(0).equals(playerInfo))) {
			if (game.getPlayerStates() != null) {
				List<RGalaxySeatState> occupiedSeats = game.listOccupiedPlayerStates();
				if (occupiedSeats.size() >= 2) {
					// VPs
					game.setTableVPs(game.getPlayerStates().size() * 12);
					// State
					game.getGameInfo().setStatusType(StatusType.UNDER_WAY);
					// Starting open cards
					int qtySeats = occupiedSeats.size();
					List<Card> startingCards = 
						DeckUtil.extractStartingCardsAndReshuffle(
								qtySeats, game.getDrawDeck());
					int c=0;
					for (RGalaxySeatState playerState : game.getPlayerStates()) {
						List<Card> tmpCards = new ArrayList<Card>(1);
						tmpCards.add(startingCards.get(c));
						game.addToOpen(tmpCards, playerState.getPlayerHashCode());
						c++;
					}
					// Starting hands
					for (RGalaxySeatState playerState : game.getPlayerStates()) {
						game.moveFromDrawDeckToHand(6, playerState.hashCode());
					}
					// Choose role
					game.setGlobalPhaseType(GlobalPhaseType.CHOOSE_ROLE);
					out = true;
				}
			}
		}
		return out;
	}
	
	public boolean setGameNick(PlayerInfo playerInfo, String uniqueID, String newNick) {
		boolean out = false;
		RGalaxyGameState state = this.games.get(uniqueID);
		if (state != null && state.getGameInfo().getPlayers().get(0).equals(playerInfo)) {
			state.getGameInfo().setNickname(newNick);
			out = true;
		}
		return out;
	}
	
	public void quit(PlayerInfo playerInfo) {
		List<RGalaxyRoomInfo> myGames = this.listPlayersGame(playerInfo);
		logger.debug("Quit requested for player " + playerInfo.getNickname());
		if (myGames != null) {
			for (RGalaxyRoomInfo game : myGames) {
				logger.debug("Quitting player from game " + game.getNickname());
				this.quitGame(playerInfo, game.getUniqueID());
			}
		}
	}
	
	public void connectionLost(PlayerInfo playerInfo) {
		this.quit(playerInfo);
	}
	
	public RGalaxyRoomInfo getGameInfo(String uniqueID) {
		RGalaxyRoomInfo out = null;
		RGalaxyGameState state = this.games.get(uniqueID);
		if (state != null)
			out = state.getGameInfo();
		return out;
	}
	

	
	
	
	
	public void addGameListStateChangeListener(RoomListStateChangeListener listener) {
		if (this.gameListStateChangeListeners == null)
			this.gameListStateChangeListeners = new HashSet<RoomListStateChangeListener>();
		this.gameListStateChangeListeners.add(listener);
	}
	
	public void removeGameListStateChangeListener(RoomListStateChangeListener listener) {
		if (this.gameListStateChangeListeners != null)
			this.gameListStateChangeListeners.remove(listener);
	}
	
	private void informGameListStateListeners() {
		if (this.gameListStateChangeListeners != null) {
			for (RoomListStateChangeListener listener : this.gameListStateChangeListeners) {
				listener.gameListChanged(new RoomListChangeEvent(this.listGames()));
			}
		}
	}

	@Override
	public void gameInfoChanged(RoomInfoChangeEvent event) {
		this.informGameListStateListeners();
	}
	




	
	public void addGameInfoStateChangeListener(String uniqueID, RoomInfoStateChangeListener listener) {
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null)
			game.getGameInfo().addStateChangeListener(listener);
	}
	
	public void removeGameInfoStateChangeListener(String uniqueID, RoomInfoStateChangeListener listener) {
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null)
			game.getGameInfo().removeStateChangeListener(listener);
	}
	
	public void addGameStateChangeListener(String uniqueID, GameStateChangeListener listener) {
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null)
			game.addGameStateChangeListener(listener);
	}
	
	public void removeGameStateChangeListener(String uniqueID, GameStateChangeListener listener) {
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null)
			game.removeGameStateChangeListener(listener);
	}
	





	
	public void addPlayerStateListener(String uniqueID, 
			int playerHashCode, SeatStateChangeListener listener) {
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null) {
			RGalaxySeatState playerState = game.findPlayerStateByPlayerHash(playerHashCode);
			if (playerState != null) {
				playerState.setPlayerChannelListener(listener);
			}
		}
	}
	
	public void removePlayerStateListener(String uniqueID, 
			int playerHashCode) {
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null) {
			RGalaxySeatState playerState = game.findPlayerStateByPlayerHash(playerHashCode);
			if (playerState != null) {
				playerState.setPlayerChannelListener(null);
			}
		}
	}
	






	public boolean say(PlayerInfo playerInfo, String uniqueID, String message) {
		boolean out = false;
		RGalaxyGameState state = this.games.get(uniqueID);
		if (state != null) {
			if(state.getGameInfo().getPlayers().contains(playerInfo)) {
				state.setLastChatLine(playerInfo.getNickname(), message);
				out = true;
			}
		}
		return out;
	}
	

	
	
	
	
	
	public boolean chooseRole(PlayerInfo playerInfo, String uniqueID, String roleID) {
		boolean out = false;
		RGalaxyGameState game = this.games.get(uniqueID);
		if (game != null) {
			if (game.getGlobalPhaseType().equals(GlobalPhaseType.CHOOSE_ROLE)) {
				RGalaxySeatState playerState = game.findPlayerStateByPlayerHash(playerInfo.hashCode);
				if (playerState.getChosenPhase() == null) {
					PhaseType phase = this.findPhaseTypeByID(game, roleID);
					if (phase != null) {
						game.chooseRole(playerState.hashCode(), phase);
						out = true;
						if (game.qtyRolesChosen() == game.listOccupiedPlayerStates().size() &&
								game.getGameInfo().getStatusType().equals(StatusType.UNDER_WAY)) {
							game.setGlobalPhaseType(GlobalPhaseType.ROLES_EXECUTION);
							for (RGalaxySeatState seat : game.getPlayerStates()) {
								this.initRole(game, seat.hashCode(), 0);
							}
						}
					}
				}
			}
		}
		return out;
	}
	
	private PhaseType findPhaseTypeByID(RGalaxyGameState game, String phaseID) {
		PhaseType out = null;
		if (game.getAvailablePhases() != null) {
			for (Phase phase : game.getAvailablePhases()) {
				if (phase.getID().equals(phaseID)) {
					out = phase.getPhaseType();
					break;
				}
			}
		}
		return out;
	}
	

	
	
	
	
	
	private void initRole(RGalaxyGameState game, int seatHash, int index) {
		List<Phase> phases = game.listChosenRoles();
		PhaseType phaseType = phases.get(index).getPhaseType();
		AbstractSeatStateEvent event = null;
		boolean privilege = (game.findPlayerStateBySeatHash(seatHash).getChosenPhase().equals(phaseType));
		if (phaseType.equals(PhaseType.EXPLORE_NORMAL)) {
			event = composeExploreNormalEvent(game, seatHash, privilege);
		}
		game.findPlayerStateBySeatHash(seatHash).setCurrentPhase(phaseType, event);
	}
	
	private AbstractSeatStateEvent composeExploreNormalEvent(RGalaxyGameState game, int seatHash, boolean privilege) {
		int keep = 1;
		int draw = 1;
		if (privilege) {
			keep++;
			draw++;
		}
		List<CardCombo> cardCombos = game.findPlayerStateBySeatHash(seatHash).getOpenCombos();
		for (CardCombo cardCombo : cardCombos) {
			if (cardCombo.getOpenCard().getExploreBehaviour() != null) {
				ExploreBehaviour behaviour = cardCombo.getOpenCard().getExploreBehaviour();
				draw += behaviour.drawExtra();
				keep += behaviour.keepExtra();
			}
		}
		game.moveFromDrawDeckToHand(draw, seatHash);
		return new ExplorePhaseInitEvent(game.getGameInfo(),keep);
	}
*/
}

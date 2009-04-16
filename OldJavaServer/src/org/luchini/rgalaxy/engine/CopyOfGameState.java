package org.luchini.rgalaxy.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.events.AbstractSeatStateEvent;
import org.luchini.bgserver.engine.events.ChatMessageEvent;
import org.luchini.bgserver.engine.events.GameStateEvent;
import org.luchini.bgserver.engine.events.SeatStateData;
import org.luchini.bgserver.engine.listeners.GameStateChangeListener;
import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.deck.DeckUtil;
import org.luchini.rgalaxy.engine.events.ExecutionInitEvent;
import org.luchini.rgalaxy.engine.events.HiddenCardMovementEvent;
import org.luchini.rgalaxy.engine.events.ChooseRoleInitEvent;

public class CopyOfGameState {
/*
	private RGalaxyRoomInfo gameInfo;
	
	private List<Card> drawDeck;
	private List<Card> discardDeck;

	private List<RGalaxySeatState> playerStates;
	private GlobalPhaseType globalPhaseType;
	
	private int tableVPs;
	private String lastChatLine;

	private List<Phase> availablePhases;
	
	private Set<GameStateChangeListener> gameStateChangeListeners;
	
	public CopyOfGameState(RGalaxyRoomInfo gameInfo, List<Card> drawDeck) {
		super();
		this.gameInfo = gameInfo;
		this.drawDeck = drawDeck;
		this.discardDeck = new ArrayList<Card>();
		this.initPhases();
	}
	
	private void initPhases() {
		this.availablePhases = new ArrayList<Phase>();
		this.availablePhases.add(new Phase(PhaseType.EXPLORE_NORMAL));
		this.availablePhases.add(new Phase(PhaseType.EXPLORE_AGRESSIVE));
		this.availablePhases.add(new Phase(PhaseType.DEVELOP));
		this.availablePhases.add(new Phase(PhaseType.SETTLE));
		this.availablePhases.add(new Phase(PhaseType.CONSUME_TRADER));
		this.availablePhases.add(new Phase(PhaseType.CONSUME_POINTS));
		this.availablePhases.add(new Phase(PhaseType.PRODUCE));
	}
	
	public List<Phase> getAvailablePhases() {
		return this.availablePhases;
	}

	public List<Card> getDrawDeck() {
		return drawDeck;
	}

	public List<Card> getDiscardDeck() {
		return discardDeck;
	}

	public synchronized void reshuffleDiscardDeck() {
		DeckUtil.shuffleDeck(this.discardDeck);
		this.drawDeck.addAll(this.discardDeck);
		this.discardDeck.clear();
		this.informGameStateChangeListeners(new ReshuffleEvent(this.gameInfo));
	}
	
	public synchronized void addToOpen(List<Card> cards, int toPlayerHashCode) {
		RGalaxySeatState playerState = this.findPlayerStateByPlayerHash(toPlayerHashCode);
		if (playerState != null) {
			playerState.addToOpenCombos(cards);
			// TODO call listeners (player X has open THESE cards)
		}
	}
	
	public synchronized void moveFromDrawDeckToHand(int qty, int toSeatHashCode) {
		RGalaxySeatState playerState = this.findPlayerStateBySeatHash(toSeatHashCode);
		if (playerState != null) {
			if (this.drawDeck.size() < qty)
				this.reshuffleDiscardDeck();
			List<Card> tmpCards = new ArrayList<Card>(qty);
			for (int i=0; i<qty; i++) {
				tmpCards.add(this.drawDeck.get(i));
			}
			playerState.addToHand(tmpCards);
			this.drawDeck.removeAll(tmpCards);
			SeatInfo seatInfo = new SeatInfo(playerState, this.findPlayerInfoBySeatHash(toSeatHashCode));
			this.informGameStateChangeListeners(
					new HiddenCardMovementEvent(this.gameInfo, HiddenCardMovementEvent.DRAW_DECK, 
							HiddenCardMovementEvent.PLAYER_HAND, qty, seatInfo));
		}
	}
	
	public synchronized void moveFromHandToDiscardDeck(int fromSeatHash, int[] cardHashes) {
		RGalaxySeatState seat = this.findPlayerStateBySeatHash(fromSeatHash);
		if (seat != null) {
			List<Card> tmpCards = new ArrayList<Card>(cardHashes.length);
			for (int i=0; i<cardHashes.length; i++) {
				Card card = this.findCardInHand(fromSeatHash, cardHashes[i]);
				tmpCards.add(card);
				this.discardDeck.add(card);
			}
			seat.removeFromHand(tmpCards);
			SeatInfo seatInfo = new SeatInfo(seat, this.findPlayerInfoBySeatHash(fromSeatHash));
			this.informGameStateChangeListeners(
					new HiddenCardMovementEvent(this.gameInfo, HiddenCardMovementEvent.PLAYER_HAND,
							HiddenCardMovementEvent.DISCARD_DECK, cardHashes.length, seatInfo));
		}
	}
	
	public synchronized void moveFromOpenToDiscardDeck(int fromSeatHash, int[] cardHashes) {
		//TODO implement
	}
	
	public synchronized void moveFromHandToOpen(int fromSeatHash, int[] cardHashCodes) {
		//TODO implement
	}
	
	public Card findCardInHand(int seatHash, int cardHash) {
		Card out = null;
		RGalaxySeatState seat = this.findPlayerStateBySeatHash(seatHash);
		if (seat != null) {
			if (seat.getHand() != null) {
				for (Card card : seat.getHand()) {
					if (card.hashCode() == cardHash) {
						out = card;
						break;
					}
				}
			}
		}
		return out;
	}
	
	public RGalaxySeatState findPlayerStateByPlayerHash(int playerHashCode) {
		RGalaxySeatState out = null;
		if (this.playerStates != null) {
			for (RGalaxySeatState playerState : this.playerStates) {
				if (playerState.getPlayerHashCode() == playerHashCode) {
					out = playerState;
					break;
				}
			}
		}
		return out;
	}
	
	public RGalaxySeatState findPlayerStateBySeatHash(int seatHashCode) {
		RGalaxySeatState out = null;
		if (this.playerStates != null) {
			for (RGalaxySeatState playerState : this.playerStates) {
				if (playerState.hashCode() == seatHashCode) {
					out = playerState;
					break;
				}
			}
		}
		return out;		
	}
	
	public PlayerInfo findPlayerInfoByPlayerHash(int playerHash) {
		PlayerInfo out = null;
		if (this.gameInfo.getPlayers() != null) {
			for (PlayerInfo playerInfo : this.gameInfo.getPlayers()) {
				if (playerInfo.hashCode() == playerHash) {
					out = playerInfo;
					break;
				}
			}
		}
		return out;
	}
	
	public PlayerInfo findPlayerInfoBySeatHash(int seatHash) {
		PlayerInfo out = null;
		if (this.playerStates != null) {
			for (RGalaxySeatState playerState : this.playerStates) {
				if (playerState.hashCode() == seatHash) {
					out = this.findPlayerInfoByPlayerHash(playerState.getPlayerHashCode());
					break;
				}
			}
		}
		return out;
	}
	
	public List<RGalaxySeatState> listAvailablePlayerStates() {
		List<RGalaxySeatState> out = null;
		if (this.playerStates != null) {
			out = new ArrayList<RGalaxySeatState>();
			for (RGalaxySeatState playerState : this.playerStates) {
				if (playerState.getPlayerHashCode() == 0) {
					out.add(playerState);
				}
			}
		}
		return out;		
	}
	
	public List<RGalaxySeatState> listOccupiedPlayerStates() {
		List<RGalaxySeatState> out = null;
		if (this.playerStates != null) {
			out = new ArrayList<RGalaxySeatState>();
			for (RGalaxySeatState playerState : this.playerStates) {
				if (playerState.getPlayerHashCode() != 0) {
					out.add(playerState);
				}
			}
		}
		return out;		
	}
	
	public GlobalPhaseType getGlobalPhaseType() {
		return globalPhaseType;
	}
	public void setGlobalPhaseType(GlobalPhaseType globalPhaseType) {
		this.globalPhaseType = globalPhaseType;
		if (globalPhaseType.equals(GlobalPhaseType.CHOOSE_ROLE)) {
			this.informGameStateChangeListeners(
					new ChooseRoleInitEvent(this.gameInfo, 
							this.tableVPs, this.availablePhases));
		} else {
			this.informGameStateChangeListeners(
					new ExecutionInitEvent(this.gameInfo,
							this.listSeatChosenRoles()));
		}
	}

	public void setCurrentRole(int seatHash, PhaseType phaseType, AbstractSeatStateEvent event) {
		RGalaxySeatState playerState = this.findPlayerStateBySeatHash(seatHash);
		if (playerState != null) {
			playerState.setCurrentPhase(phaseType, event);
		}
	}
	
	private List<SeatChosenRole> listSeatChosenRoles() {
		List<SeatChosenRole> out = null;
		if (this.playerStates != null) {
			out = new ArrayList<SeatChosenRole>(this.playerStates.size());
			for (RGalaxySeatState playerState : this.playerStates) {
				SeatInfo seatInfo = new SeatInfo(playerState, 
						this.findPlayerInfoBySeatHash(playerState.hashCode()));
				Phase phaseChosen = new Phase(playerState.getChosenPhase());
				SeatChosenRole seatChosen = new SeatChosenRole(seatInfo, phaseChosen);
				out.add(seatChosen);
			}
		}
		return out;
	}
	
	public List<Phase> listChosenRoles() {
		List<Phase> out = null;
		if (this.playerStates != null) {
			out = new ArrayList<Phase>(this.playerStates.size());
			for (Phase phase : this.availablePhases) {
				for (RGalaxySeatState playerState : this.playerStates) {
					if (playerState.getChosenPhase().equals(phase.getPhaseType())) {
						out.add(phase);
					}
				}
			}
		}
		return out;
	}
	

	public int getTableVPs() {
		return tableVPs;
	}
	public void setTableVPs(int tableVPs) {
		this.tableVPs = tableVPs;
	}

	

	public RGalaxyRoomInfo getGameInfo() {
		return gameInfo;
	}




	public List<RGalaxySeatState> getPlayerStates() {
		return playerStates;
	}
	public synchronized void seatPlayer(PlayerInfo playerInfo) {
		if (this.playerStates == null)
			this.playerStates = new ArrayList<RGalaxySeatState>();
		RGalaxySeatState playerState = new RGalaxySeatState(this, playerInfo.hashCode());
		this.playerStates.add(playerState);
		this.informGameStateChangeListeners(
				new SeatedPlayersChangeEvent(this.gameInfo, 
						SeatedPlayersReason.PLAYER_SEATED, 
						playerInfo.getNickname(), 
						this.createSeatInfoList()));
	}
	public synchronized void unseatPlayer(PlayerInfo playerInfo) {
		if (this.playerStates != null) {
			RGalaxySeatState playerState = this.findPlayerStateByPlayerHash(playerInfo.hashCode());
			if (playerState != null) {
				playerState.setPlayerHashCode(0);
				this.informGameStateChangeListeners(
						new SeatedPlayersChangeEvent(this.gameInfo, 
								SeatedPlayersReason.PLAYER_LEFT, 
								playerInfo.getNickname(), 
								this.createSeatInfoList()));
			}
		}
	}
	public synchronized void reseatPlayer(PlayerInfo playerInfo, int seatHash) {
		if (this.playerStates != null) {
			RGalaxySeatState playerState = this.findPlayerStateBySeatHash(seatHash);
			if (playerState != null) {
				playerState.setPlayerHashCode(playerInfo.hashCode());
				this.informGameStateChangeListeners(
						new SeatedPlayersChangeEvent(this.gameInfo, 
								SeatedPlayersReason.PLAYER_RESEATED, 
								playerInfo.getNickname(), 
								this.createSeatInfoList()));
			}
		}
	}
	
	public void chooseRole(int seatHash, PhaseType phase) {
		RGalaxySeatState seat = this.findPlayerStateBySeatHash(seatHash);
		seat.setChosenPhase(phase);
		SeatInfo seatInfo = new SeatInfo(seat, this.findPlayerInfoBySeatHash(seatHash));
		this.informGameStateChangeListeners(new ChooseRoleCompleteEvent(this.gameInfo, seatInfo));
	}
	public int qtyRolesChosen() {
		int out = 0;
		for (RGalaxySeatState playerState : this.playerStates) {
			if (playerState.getChosenPhase() != null)
				out++;
		}
		return out;
	}
	public void clearChoseRoles() {
		for (RGalaxySeatState playerState : this.playerStates) {
			playerState.setChosenPhase(null);
		}
	}


	private List<SeatInfo> createSeatInfoList() {
		List<SeatInfo> out = null;
		if (this.playerStates != null) {
			out = new ArrayList<SeatInfo>(this.playerStates.size());
			for (RGalaxySeatState playerState : this.playerStates) {
				out.add(new SeatInfo(playerState, 
						this.findPlayerInfoBySeatHash(playerState.hashCode())));
			}
		}
		return out;
	}

	public Set<GameStateChangeListener> getGameStateChangeListeners() {
		return gameStateChangeListeners;
	}


	public String getLastChatLine() {
		return lastChatLine;
	}
	public void setLastChatLine(String senderName, String lastChatLine) {
		this.lastChatLine = lastChatLine;
		this.informGameStateChangeListeners(new ChatMessageEvent(this.gameInfo, senderName, lastChatLine));
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
	
	private void informGameStateChangeListeners(GameStateEvent event) {
		if (this.gameStateChangeListeners != null) {
			for (GameStateChangeListener listener : this.gameStateChangeListeners) {
				listener.gameStateChanged(event);
			}
		}
	}
*/
}

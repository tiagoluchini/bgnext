package org.luchini.rgalaxy.engine;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.SeatState;
import org.luchini.bgserver.engine.events.GenericFromSeatGameStateEvent;
import org.luchini.bgserver.engine.events.GenericGameStateEvent;
import org.luchini.bgserver.engine.events.SeatStateData;
import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.deck.DeckUtil;
import org.luchini.rgalaxy.engine.events.ChooseRoleInitEvent;
import org.luchini.rgalaxy.engine.events.ExecutionInitEvent;
import org.luchini.rgalaxy.engine.events.GenericEventReason;
import org.luchini.rgalaxy.engine.events.GenericFromSeatEventReason;
import org.luchini.rgalaxy.engine.events.HiddenCardMovementEvent;

public class RGalaxyGameState extends GameState {

	private List<Card> drawDeck;
	private List<Card> discardDeck;
	private GlobalPhaseType globalPhaseType;
	private int tableVPs;

	private List<Phase> availablePhases;

	
	public RGalaxyGameState(GameEngine gameEngine, String uniqueID,
			PlayerInfo hostPlayer, List<Card> drawDeck) {
		super(gameEngine, uniqueID, hostPlayer);
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
		this.informGameStateChangeListeners(new GenericGameStateEvent(this.roomInfo, 
				GenericEventReason.RESHUFFLE_DISCARD_DECK.toString()));
	}
	
	public synchronized void addToOpen(List<Card> cards, int toSeatHashCode) {
		RGalaxySeatState seat = (RGalaxySeatState)this.getRoomInfo().findSeatBySeatHash(toSeatHashCode);
		if (seat != null) {
			seat.addToOpenCombos(cards);
			// TODO call listeners (player X has open THESE cards)
		}
	}
	
	public synchronized void moveFromDrawDeckToHand(int qty, int toSeatHashCode) {
		RGalaxySeatState seat = (RGalaxySeatState)this.getRoomInfo().findSeatBySeatHash(toSeatHashCode);
		if (seat != null) {
			if (this.drawDeck.size() < qty)
				this.reshuffleDiscardDeck();
			List<Card> tmpCards = new ArrayList<Card>(qty);
			for (int i=0; i<qty; i++) {
				tmpCards.add(this.drawDeck.get(i));
			}
			seat.addToHand(tmpCards);
			this.drawDeck.removeAll(tmpCards);
			SeatStateData seatStateData = SeatStateData.createSeatStateData(seat);
			this.informGameStateChangeListeners(
					new HiddenCardMovementEvent(this.roomInfo, HiddenCardMovementEvent.DRAW_DECK, 
							HiddenCardMovementEvent.PLAYER_HAND, qty, seatStateData));
		}
	}
	
	public synchronized void moveFromHandToDiscardDeck(int fromSeatHash, int[] cardHashes) {
		RGalaxySeatState seat = (RGalaxySeatState)this.getRoomInfo().findSeatBySeatHash(fromSeatHash);
		if (seat != null) {
			List<Card> tmpCards = new ArrayList<Card>(cardHashes.length);
			for (int i=0; i<cardHashes.length; i++) {
				Card card = this.findCardInHand(seat, cardHashes[i]);
				tmpCards.add(card);
				this.discardDeck.add(card);
			}
			seat.removeFromHand(tmpCards);
			SeatStateData seatStateData = SeatStateData.createSeatStateData(seat);
			this.informGameStateChangeListeners(
					new HiddenCardMovementEvent(this.roomInfo, HiddenCardMovementEvent.PLAYER_HAND,
							HiddenCardMovementEvent.DISCARD_DECK, cardHashes.length, seatStateData));
		}
	}
	
	public synchronized void moveFromOpenToDiscardDeck(int fromSeatHash, int[] cardHashes) {
		//TODO implement
	}
	
	public synchronized void moveFromHandToOpen(int fromSeatHash, int[] cardHashCodes) {
		//TODO implement
	}

	public Card findCardInHand(RGalaxySeatState seat, int cardHash) {
		Card out = null;
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
	
	public GlobalPhaseType getGlobalPhaseType() {
		return globalPhaseType;
	}
	public void setGlobalPhaseType(GlobalPhaseType globalPhaseType) {
		this.globalPhaseType = globalPhaseType;
		if (globalPhaseType.equals(GlobalPhaseType.CHOOSE_ROLE)) {
			this.informGameStateChangeListeners(
					new ChooseRoleInitEvent(this.roomInfo, 
							this.tableVPs, this.availablePhases));
		} else {
			this.informGameStateChangeListeners(
					new ExecutionInitEvent(this.roomInfo,
							this.listSeatChosenRoles()));
		}
	}

	private List<SeatChosenRole> listSeatChosenRoles() {
		List<SeatChosenRole> out = null;
		for (Phase searchPhase : this.availablePhases) {
			for (SeatState seatState : this.roomInfo.getSeatStates()) {
				RGalaxySeatState mySeat = (RGalaxySeatState)seatState;
				if (mySeat.getChosenPhase().equals(searchPhase.getPhaseType())) {
					SeatStateData seatStateData = SeatStateData.createSeatStateData(mySeat);
					Phase phaseChosen = new Phase(mySeat.getChosenPhase());
					SeatChosenRole seatChosen = new SeatChosenRole(seatStateData, phaseChosen);
					out.add(seatChosen);
					break;
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

	
	public void chooseRole(int seatHash, PhaseType phase) {
		RGalaxySeatState seat = (RGalaxySeatState)this.roomInfo.findSeatBySeatHash(seatHash);
		seat.setChosenPhase(phase);
		SeatStateData seatStateData = SeatStateData.createSeatStateData(seat);
		this.informGameStateChangeListeners(new GenericFromSeatGameStateEvent(
				this.roomInfo, seatStateData, GenericFromSeatEventReason.ROLE_CHOSEN.toString()));
	}
	public int qtyRolesChosen() {
		int out = 0;
		for (SeatState seatState : this.roomInfo.getSeatStates()) {
			RGalaxySeatState mySeat = (RGalaxySeatState)seatState; 
			if (mySeat.getChosenPhase() != null)
				out++;
		}
		return out;
	}
	public void clearChoseRoles() {
		for (SeatState seatState : this.roomInfo.getSeatStates()) {
			RGalaxySeatState mySeat = (RGalaxySeatState)seatState;
			mySeat.setChosenPhase(null);
		}
	}

	
	@Override
	protected RoomInfo createRoomInfo(GameEngine gameEngine, String uniqueID,
			PlayerInfo seatedPlayer, GameState parent) {
		return new RGalaxyRoomInfo(gameEngine, uniqueID, seatedPlayer, parent);
	}


}

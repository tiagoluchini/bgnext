package org.luchini.rgalaxy.engine;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.SeatState;
import org.luchini.bgserver.engine.events.AbstractSeatStateEvent;
import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.engine.events.HandCardsEvent;

public class RGalaxySeatState extends SeatState {

	private List<Card> hand;
	private List<CardCombo> openCombos;
	private PhaseType chosenPhase;
	private PhaseType currentPhase;
	private int earnedVPs;
	
	
	public RGalaxySeatState(RoomInfo parent, PlayerInfo seatedPlayer) {
		super(parent, seatedPlayer);
	}

	
	public List<Card> getHand() {
		return hand;
	}
	public void addToHand(List<Card> cards) {
		if (this.hand == null)
			this.hand = new ArrayList<Card>();
		this.hand.addAll(cards);
		this.informListener(new HandCardsEvent(this.parent, this.hand));
	}
	public void removeFromHand(List<Card> cards) {
		if (this.hand != null) {
			this.hand.removeAll(cards);
			// there is no calling to listeners as this should be public (gameState)
		}
	}

	
	public int getEarnedVPs() {
		return this.earnedVPs;
	}
	public void setEarnedVPs(int VPs) {
		this.earnedVPs = VPs;
		// there is no calling to listeners as this should be public (gameState)
	}

	
	public List<CardCombo> getOpenCombos() {
		return this.openCombos;
	}
	public void addToOpenCombos(List<Card> cards) {
		if (this.openCombos == null)
			this.openCombos = new ArrayList<CardCombo>();
		for (Card card : cards) {
			this.openCombos.add(new CardCombo(card));
		}
		// there is no calling to listeners as this should be public (gameState)
	}

	
	public PhaseType getChosenPhase() {
		return this.chosenPhase;
	}
	public void setChosenPhase(PhaseType phaseType) {
		this.chosenPhase = phaseType;
		// no one needs to be informed about this (there is a game one at the gameState)
	}

	
	public PhaseType getCurrentPhase() {
		return this.currentPhase;
	}
	public void setCurrentPhase(PhaseType phaseType, AbstractSeatStateEvent event) {
		this.currentPhase = phaseType;
		this.informListener(event);
	}


	@Override
	public Object getGameSpecificData() {
		return new RGalaxySeatStateData(this);
	}	

}

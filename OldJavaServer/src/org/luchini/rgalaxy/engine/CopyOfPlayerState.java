package org.luchini.rgalaxy.engine;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.events.AbstractSeatStateEvent;
import org.luchini.bgserver.engine.events.SeatStateChangeEvent;
import org.luchini.bgserver.engine.listeners.SeatStateChangeListener;
import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.engine.events.HandCardsEvent;

public class CopyOfPlayerState {
/*
	private int playerHashCode;
	private List<Card> hand;
	private List<CardCombo> openCombos;
	private PhaseType chosenPhase;
	private PhaseType currentPhase;
	private SeatStateChangeListener seatStateChangeListener;
	private int earnedVPs;
	
	private RGalaxyGameState parent;
	
	public CopyOfPlayerState(RGalaxyGameState parent, int playerHashCode) {
		this.parent = parent;
		this.playerHashCode = playerHashCode;
	}
	
	public int getPlayerHashCode() {
		return playerHashCode;
	}
	public void setPlayerHashCode(int playerHashCode) {
		this.playerHashCode = playerHashCode;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	public void addToHand(List<Card> cards) {
		if (this.hand == null)
			this.hand = new ArrayList<Card>();
		this.hand.addAll(cards);
		this.informListener(new HandCardsEvent(this.parent.getGameInfo(), this.hand));
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
	
	public SeatStateChangeListener getPlayerChannelListener() {
		return seatStateChangeListener;
	}
	public void setPlayerChannelListener(SeatStateChangeListener seatStateChangeListener) {
		this.seatStateChangeListener = seatStateChangeListener;
	}
	
	private void informListener(SeatStateChangeEvent event) {
		if (this.seatStateChangeListener != null)
			this.seatStateChangeListener.seatStateChangeEvent(event);
	}
*/
}

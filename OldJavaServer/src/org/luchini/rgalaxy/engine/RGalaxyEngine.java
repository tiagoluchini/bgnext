package org.luchini.rgalaxy.engine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.SeatState;
import org.luchini.bgserver.engine.StatusType;
import org.luchini.bgserver.server.GameProtocol;
import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.deck.DeckUtil;
import org.luchini.rgalaxy.deck.enums.SetType;
import org.luchini.rgalaxy.server.RGalaxyProtocol;
import org.luchini.rgalaxy.util.LogUtil;

public class RGalaxyEngine implements GameEngine {

	private static Logger logger = LogUtil.getLogger(RGalaxyEngine.class);
	
	private GameProtocol protocol;
	
	public RGalaxyEngine() {
		logger.debug("Starting up RGalaxyEngine");
		this.protocol = new RGalaxyProtocol(this);
	}

	@Override
	public GameState createGameState(String uniqueID, PlayerInfo hostPlayer) {
		List<Card> deck = DeckUtil.createNewShuffledDeck(SetType.BASE);
		return new RGalaxyGameState(this, uniqueID, hostPlayer, deck);
	}

	@Override
	public GameProtocol getGameProtocol() {
		return this.protocol;
	}

	@Override
	public String getIconURL() {
		//TODO temporary 
		return "http://images.boardgamegeek.com/images/pic271269_mt.jpg";
	}

	@Override
	public String getName() {
		return "Race for the Galaxy";
	}

	@Override
	public String getUniqueName() {
		return "RGALAXY";
	}
	
	public boolean startGame(PlayerInfo playerInfo, GameState game) {
		boolean out = false;
		RGalaxyGameState myGame = (RGalaxyGameState)game;
		if (myGame.getRoomInfo().canStart(playerInfo)) {
			int qtySeats = myGame.getRoomInfo().getSeatStates().size();
			// VPs
			myGame.setTableVPs(qtySeats * 12);
			// State
			myGame.getRoomInfo().setStatusType(StatusType.UNDER_WAY);
			// Starting open cards
			List<Card> startingCards = 
				DeckUtil.extractStartingCardsAndReshuffle(
						qtySeats, myGame.getDrawDeck());
			int c=0;
			for (SeatState seatState : game.getRoomInfo().getSeatStates()) {
				List<Card> tmpCards = new ArrayList<Card>(1);
				tmpCards.add(startingCards.get(c));
				myGame.addToOpen(tmpCards, seatState.hashCode());
				c++;
			}
			// Starting hands
			for (SeatState seatState : game.getRoomInfo().getSeatStates()) {
				myGame.moveFromDrawDeckToHand(6, seatState.hashCode());
			}
			// Choose role
			myGame.setGlobalPhaseType(GlobalPhaseType.CHOOSE_ROLE);
			out = true;
		}
		return out;
	}
	
	
	public boolean chooseRole(PlayerInfo playerInfo, GameState game, String roleID) {
		boolean out = false;
		RGalaxyGameState myGame = (RGalaxyGameState)game;
		if (game != null) {
			if (myGame.getGlobalPhaseType().equals(GlobalPhaseType.CHOOSE_ROLE)) {
				RGalaxySeatState seatState = (RGalaxySeatState)game.getRoomInfo().findSeatByPlayerHash(
						playerInfo.hashCode());
				if (seatState.getChosenPhase() == null) {
					PhaseType phase = this.findPhaseTypeByID(myGame, roleID);
					if (phase != null) {
						myGame.chooseRole(seatState.hashCode(), phase);
						out = true;
						// should move to Execution phase if all have chosen
						if (myGame.qtyRolesChosen() == myGame.getRoomInfo().listOccupiedSeatStates().size() &&
								myGame.getRoomInfo().getStatusType().equals(StatusType.UNDER_WAY)) {
							myGame.setGlobalPhaseType(GlobalPhaseType.ROLES_EXECUTION);
							/*for (RGalaxySeatState seat : game.getPlayerStates()) {
								this.initRole(game, seat.hashCode(), 0);
							}*/
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
	
	/*
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

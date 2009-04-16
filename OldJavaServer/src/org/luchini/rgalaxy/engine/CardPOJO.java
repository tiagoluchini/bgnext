package org.luchini.rgalaxy.engine;

import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.deck.enums.CardType;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;
import org.luchini.rgalaxy.deck.enums.SetType;
import org.luchini.rgalaxy.deck.enums.SpecialType;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode
public class CardPOJO {

	@TreeAttribute protected int hashCode;
	@TreeAttribute protected String name;
	@TreeAttribute protected int cost;
	@TreeAttribute protected int VP;
	@TreeAttribute protected int strength;
	@TreeAttribute protected CardType cardType;
	@TreeAttribute protected ProductionType productionType;
	@TreeAttribute protected MilitaryType militaryType;
	@TreeAttribute protected GoodType storableGoodType;
	@TreeAttribute protected SpecialType specialType;
	@TreeAttribute protected boolean startingWorld;
	@TreeAttribute protected SetType setType;
	
	@TreeNode protected PhaseBehaviourPOJO ExploreBehaviour;
	@TreeNode protected PhaseBehaviourPOJO DevelopBehaviour;
	@TreeNode protected PhaseBehaviourPOJO SettleBehaviour;
	@TreeNode protected PhaseBehaviourPOJO TradeBehaviour;
	@TreeNode protected PhaseBehaviourPOJO ConsumeBehaviour;
	@TreeNode protected PhaseBehaviourPOJO ProduceBehaviour;
	
	@TreeNode protected PhaseBehaviourPOJO MultiplierBehaviour;

	public CardPOJO(Card card) {
		this.hashCode = card.hashCode();
		this.name = card.getName();
		this.cost = card.getCost();
		this.VP = card.getVP();
		this.strength = card.getStrength();
		this.cardType = card.getCardType();
		this.productionType = card.getProductionType();
		this.militaryType = card.getMilitaryType();
		this.storableGoodType = card.getStorableGoodType();
		this.specialType = card.getSpecialType();
		this.startingWorld = card.isStartingWorld();
		this.setType = card.getSetType();
		
		this.ExploreBehaviour = new PhaseBehaviourPOJO(PhaseType.EXPLORE, card.getExploreBehaviour());
		this.DevelopBehaviour = new PhaseBehaviourPOJO(PhaseType.DEVELOP, card.getDevelopBehaviour());
		this.SettleBehaviour = new PhaseBehaviourPOJO(PhaseType.SETTLE, card.getSettleBehaviour());
		this.TradeBehaviour = new PhaseBehaviourPOJO(PhaseType.TRADE, card.getTradeBehaviour());
		this.ConsumeBehaviour = new PhaseBehaviourPOJO(PhaseType.CONSUME, card.getConsumeBehaviour());
		this.ProduceBehaviour = new PhaseBehaviourPOJO(PhaseType.PRODUCE, card.getProduceBehaviour());
		
		this.MultiplierBehaviour = new PhaseBehaviourPOJO(PhaseType.MULTIPLIER, card.getMultiplierBehaviour());
	}
	
}

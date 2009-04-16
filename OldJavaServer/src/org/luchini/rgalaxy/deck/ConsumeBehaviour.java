package org.luchini.rgalaxy.deck;

import org.luchini.rgalaxy.deck.enums.GoodType;

public interface ConsumeBehaviour extends PhaseBehaviour {

	public int numberOfUsages();
	
	public int generalVP();
	public int generalDrawCard();
	
	public int specificVP();
	public GoodType specificVPGood();
	public int specificDrawCard();
	public GoodType specificDrawCardGood();
	
	public boolean discardCardsInstead();
	
	public int fixedNumberOfGoodsRequired();
	public int fixedNumberOfUniqueGoodsRequired();
	
	public boolean useAllLeftGoods();
	
	public boolean tradePrice();
	public boolean tradePricePlusTradePowers();
	
	public boolean drawIfLucky();
	
	public int drawCard();
	
}

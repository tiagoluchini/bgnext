package org.luchini.rgalaxy.deck;

import org.luchini.rgalaxy.deck.enums.GoodType;

public interface TradeBehaviour extends PhaseBehaviour {

	public int generalDrawExtra();
	public int specificDrawExtra();
	public GoodType specificDrawExtraGood();
	public int drawExtraFromThisWorld();
	
}

package org.luchini.rgalaxy.deck;

import org.luchini.rgalaxy.deck.enums.GoodType;

public interface SettleBehaviour extends PhaseBehaviour {

	public int reduceCost();
	public int militaryStrength();
	
	public boolean mustDiscardToEffect();
	
	public int specificReduceCost();
	public GoodType specificReduceCostGood();
	
	public int specificMilitaryStrength();
	public GoodType specificMilitaryStrenghtGood();
	
	public int specificMilitaryStrengthAgainstRebels();
	
	public int drawAfter();
	
	public boolean costToZero();
	public boolean costToZeroNonAlien();
	
	public boolean payForMilitary();
	
}

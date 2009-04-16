package org.luchini.rgalaxy.deck.behaviours.settle;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.enums.GoodType;

public class AlienCostMinusTwoAlienMilitaryPlusTwo implements SettleBehaviour {

	@Override
	public boolean costToZero() {
		return false;
	}

	@Override
	public int drawAfter() {
		return 0;
	}

	@Override
	public int militaryStrength() {
		return 0;
	}

	@Override
	public boolean mustDiscardToEffect() {
		return false;
	}

	@Override
	public boolean payForMilitary() {
		return false;
	}

	@Override
	public int reduceCost() {
		return 0;
	}

	@Override
	public GoodType specificMilitaryStrenghtGood() {
		return GoodType.ALIEN;
	}

	@Override
	public int specificMilitaryStrength() {
		return 2;
	}

	@Override
	public int specificMilitaryStrengthAgainstRebels() {
		return 0;
	}

	@Override
	public int specificReduceCost() {
		return 2;
	}

	@Override
	public GoodType specificReduceCostGood() {
		return GoodType.ALIEN;
	}

	@Override
	public byte[] getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReference() {
		return "Alien Cost -2, Alien Military +2";
	}

	@Override
	public boolean costToZeroNonAlien() {
		return false;
	}

}

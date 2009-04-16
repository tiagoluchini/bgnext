package org.luchini.rgalaxy.deck.behaviours.settle;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.enums.GoodType;

public class DiscardForMilitaryThree implements SettleBehaviour {

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
		return 3;
	}

	@Override
	public boolean mustDiscardToEffect() {
		return true;
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
		return null;
	}

	@Override
	public int specificMilitaryStrength() {
		return 0;
	}

	@Override
	public int specificMilitaryStrengthAgainstRebels() {
		return 0;
	}

	@Override
	public int specificReduceCost() {
		return 0;
	}

	@Override
	public GoodType specificReduceCostGood() {
		return null;
	}

	@Override
	public byte[] getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReference() {
		return "Discard for Military +3";
	}

	@Override
	public boolean costToZeroNonAlien() {
		return false;
	}

}

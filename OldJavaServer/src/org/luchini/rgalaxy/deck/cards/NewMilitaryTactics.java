package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.behaviours.settle.DiscardForMilitaryThree;

public class NewMilitaryTactics extends AbstractDevelopment {

	@Override
	public int getCost() {
		return 1;
	}

	@Override
	public byte[] getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "New Military Tactics";
	}

	@Override
	public SettleBehaviour getSettleBehaviour() {
		return new DiscardForMilitaryThree();
	}

	@Override
	public int getVP() {
		return 1;
	}

}

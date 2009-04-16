package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.behaviours.settle.MilitaryTwoMore;

public class SpaceMarines extends AbstractDevelopment {

	@Override
	public int getCost() {
		return 2;
	}

	@Override
	public byte[] getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Space Marines";
	}

	@Override
	public SettleBehaviour getSettleBehaviour() {
		return new MilitaryTwoMore();
	}

	@Override
	public int getVP() {
		return 1;
	}
	

}

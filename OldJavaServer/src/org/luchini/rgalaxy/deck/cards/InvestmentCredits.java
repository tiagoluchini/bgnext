package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.DevelopBehaviour;
import org.luchini.rgalaxy.deck.behaviours.develop.CostMinusOne;

public class InvestmentCredits extends AbstractDevelopment {

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
		return "Investment Credits";
	}

	@Override
	public DevelopBehaviour getDevelopBehaviour() {
		return new CostMinusOne();
	}

	@Override
	public int getVP() {
		return 1;
	}

}

package org.luchini.rgalaxy.deck.behaviours.develop;

import org.luchini.rgalaxy.deck.DevelopBehaviour;

public class CostMinusTwo implements DevelopBehaviour {

	@Override
	public int drawAfter() {
		return 0;
	}

	@Override
	public int drawBefore() {
		return 0;
	}

	@Override
	public int reduceCost() {
		return 2;
	}

	@Override
	public byte[] getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReference() {
		return "Cost -2";
	}

}

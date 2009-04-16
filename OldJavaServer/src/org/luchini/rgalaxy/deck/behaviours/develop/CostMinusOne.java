package org.luchini.rgalaxy.deck.behaviours.develop;

import org.luchini.rgalaxy.deck.DevelopBehaviour;

public class CostMinusOne implements DevelopBehaviour {

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
		return 1;
	}

	@Override
	public byte[] getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReference() {
		return "Cost -1";
	}

}

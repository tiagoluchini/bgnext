package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.behaviours.settle.CostMinusTwo;

public class ReplicantRobots extends AbstractDevelopment {

	@Override
	public int getCost() {
		return 4;
	}

	@Override
	public byte[] getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Replicant Robots";
	}

	@Override
	public SettleBehaviour getSettleBehaviour() {
		return new CostMinusTwo();
	}

	@Override
	public int getVP() {
		return 2;
	}
	
}

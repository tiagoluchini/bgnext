package org.luchini.rgalaxy.deck;

public interface DevelopBehaviour extends PhaseBehaviour {

	public int drawBefore();
	public int drawAfter();
	public int reduceCost();

}

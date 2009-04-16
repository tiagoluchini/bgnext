package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.DevelopBehaviour;
import org.luchini.rgalaxy.deck.behaviours.develop.DrawOneBefore;

public class InterstellarBank extends AbstractDevelopment {

	@Override
	public int getCost() {
		return 2;
	}

	@Override
	public DevelopBehaviour getDevelopBehaviour() {
		return new DrawOneBefore();
	}

	@Override
	public int getVP() {
		return 1;
	}

	@Override
	public byte[] getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Interstellar Bank";
	}

}

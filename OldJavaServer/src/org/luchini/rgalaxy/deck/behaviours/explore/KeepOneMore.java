package org.luchini.rgalaxy.deck.behaviours.explore;

import org.luchini.rgalaxy.deck.ExploreBehaviour;

public class KeepOneMore implements ExploreBehaviour {

	@Override
	public int drawExtra() {
		return 0;
	}

	@Override
	public int keepExtra() {
		return 1;
	}

	@Override
	public byte[] getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReference() {
		return "Keep +1";
	}

}

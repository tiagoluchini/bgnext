package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;

public class PreSentientRace extends AbstractWorld {

	@Override
	public byte[] getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilitaryType getMilitaryType() {
		return MilitaryType.NEUTRAL;
	}

	@Override
	public String getName() {
		return "Pre-Sentient Race";
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.WINDFALL;
	}

	@Override
	public GoodType getStorableGoodType() {
		return GoodType.GENES;
	}

	@Override
	public int getCost() {
		return 2;
	}

	@Override
	public int getVP() {
		return 1;
	}

}

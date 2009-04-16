package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;
import org.luchini.rgalaxy.deck.enums.SpecialType;

public class ReptilianUpliftRace extends AbstractWorld {

	@Override
	public byte[] getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilitaryType getMilitaryType() {
		return MilitaryType.MILITARY;
	}

	@Override
	public String getName() {
		return "Reptilian Uplift Race";
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
	public SpecialType getSpecialType() {
		return SpecialType.UPLIFT;
	}

	@Override
	public int getStrength() {
		return 2;
	}

	@Override
	public int getVP() {
		return 2;
	}

}

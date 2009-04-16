package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;
import org.luchini.rgalaxy.deck.enums.SpecialType;

public class RebelBase extends AbstractWorld {

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
		return "Rebel Base";
	}

	@Override
	public ProductionType getProductionType() {
		return null;
	}

	@Override
	public GoodType getStorableGoodType() {
		return null;
	}

	@Override
	public SpecialType getSpecialType() {
		return SpecialType.REBEL;
	}

	@Override
	public int getStrength() {
		return 6;
	}

	@Override
	public int getVP() {
		return 6;
	}

}

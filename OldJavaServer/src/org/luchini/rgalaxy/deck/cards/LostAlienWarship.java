package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.behaviours.settle.MilitaryTwoMore;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;
import org.luchini.rgalaxy.deck.enums.SpecialType;

public class LostAlienWarship extends AbstractWorld {

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
		return "Lost Alien Warship";
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.WINDFALL;
	}

	@Override
	public GoodType getStorableGoodType() {
		return GoodType.ALIEN;
	}

	@Override
	public SettleBehaviour getSettleBehaviour() {
		return new MilitaryTwoMore();
	}

	@Override
	public SpecialType getSpecialType() {
		return SpecialType.ALIEN;
	}

	@Override
	public int getStrength() {
		return 5;
	}

	@Override
	public int getVP() {
		return 3;
	}

}

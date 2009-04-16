package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.behaviours.settle.MilitaryOneMore;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;
import org.luchini.rgalaxy.deck.enums.SpecialType;

public class RebelWarriorRace extends AbstractWorld {

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
		return "Rebel Warrior Race";
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
	public SettleBehaviour getSettleBehaviour() {
		return new MilitaryOneMore();
	}

	@Override
	public SpecialType getSpecialType() {
		return SpecialType.REBEL;
	}

	@Override
	public int getStrength() {
		return 3;
	}

	@Override
	public int getVP() {
		return 2;
	}
	
	

}

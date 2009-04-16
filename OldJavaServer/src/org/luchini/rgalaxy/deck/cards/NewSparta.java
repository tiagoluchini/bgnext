package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.behaviours.settle.MilitaryTwoMore;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;

public class NewSparta extends AbstractWorld {

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
		return "New Sparta";
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
	public SettleBehaviour getSettleBehaviour() {
		return new MilitaryTwoMore();
	}

	@Override
	public int getStrength() {
		return 2;
	}

	@Override
	public int getVP() {
		return 1;
	}

	@Override
	public boolean isStartingWorld() {
		return true;
	}
	
	

}

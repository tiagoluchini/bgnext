package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.behaviours.settle.MilitaryOneMore;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;

public class FormerPenalColony extends AbstractWorld {

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
		return "Former Penal Colony";
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.WINDFALL;
	}

	@Override
	public GoodType getStorableGoodType() {
		return GoodType.NOVELTY;
	}

	@Override
	public SettleBehaviour getSettleBehaviour() {
		return new MilitaryOneMore();
	}

	@Override
	public int getStrength() {
		return 2;
	}

	@Override
	public int getVP() {
		return 1;
	}
	
	

}

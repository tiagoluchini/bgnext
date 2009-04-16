package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.behaviours.settle.MilitaryOneMore;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;

public class RefugeeWorld extends AbstractWorld {

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
		return "Refugee World";
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
	public int getCost() {
		return 0;
	}

	@Override
	public SettleBehaviour getSettleBehaviour() {
		return new MilitaryOneMore();
	}

	@Override
	public int getVP() {
		return 1;
	}
	
}

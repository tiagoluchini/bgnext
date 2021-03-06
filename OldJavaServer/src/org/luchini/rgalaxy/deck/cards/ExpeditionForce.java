package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.deck.ConsumeBehaviour;
import org.luchini.rgalaxy.deck.DevelopBehaviour;
import org.luchini.rgalaxy.deck.ExploreBehaviour;
import org.luchini.rgalaxy.deck.MultiplierBehaviour;
import org.luchini.rgalaxy.deck.ProduceBehaviour;
import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.TradeBehaviour;
import org.luchini.rgalaxy.deck.behaviours.explore.DrawOneMore;
import org.luchini.rgalaxy.deck.behaviours.settle.MilitaryOneMore;
import org.luchini.rgalaxy.deck.enums.CardType;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;
import org.luchini.rgalaxy.deck.enums.SetType;
import org.luchini.rgalaxy.deck.enums.SpecialType;

public class ExpeditionForce implements Card {

	@Override
	public CardType getCardType() {
		return CardType.DEVELOPMENT;
	}

	@Override
	public ConsumeBehaviour getConsumeBehaviour() {
		return null;
	}

	@Override
	public int getCost() {
		return 1;
	}

	@Override
	public DevelopBehaviour getDevelopBehaviour() {
		return null;
	}

	@Override
	public ExploreBehaviour getExploreBehaviour() {
		return new DrawOneMore();
	}

	@Override
	public byte[] getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilitaryType getMilitaryType() {
		return null;
	}

	@Override
	public MultiplierBehaviour getMultiplierBehaviour() {
		return null;
	}

	@Override
	public String getName() {
		return "Expedition Force";
	}

	@Override
	public ProduceBehaviour getProduceBehaviour() {
		return null;
	}

	@Override
	public ProductionType getProductionType() {
		return null;
	}

	@Override
	public SettleBehaviour getSettleBehaviour() {
		return new MilitaryOneMore();
	}

	@Override
	public GoodType getStorableGoodType() {
		return null;
	}

	@Override
	public int getStrength() {
		return 0;
	}

	@Override
	public TradeBehaviour getTradeBehaviour() {
		return null;
	}

	@Override
	public int getVP() {
		return 1;
	}

	@Override
	public boolean isStartingWorld() {
		return false;
	}

	@Override
	public SetType getSetType() {
		return SetType.BASE;
	}

	@Override
	public SpecialType getSpecialType() {
		return null;
	}
}

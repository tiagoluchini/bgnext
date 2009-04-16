package org.luchini.rgalaxy.deck.cards;

import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.deck.ConsumeBehaviour;
import org.luchini.rgalaxy.deck.DevelopBehaviour;
import org.luchini.rgalaxy.deck.ExploreBehaviour;
import org.luchini.rgalaxy.deck.MultiplierBehaviour;
import org.luchini.rgalaxy.deck.ProduceBehaviour;
import org.luchini.rgalaxy.deck.SettleBehaviour;
import org.luchini.rgalaxy.deck.TradeBehaviour;
import org.luchini.rgalaxy.deck.enums.CardType;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;
import org.luchini.rgalaxy.deck.enums.SetType;
import org.luchini.rgalaxy.deck.enums.SpecialType;

public class DesertedAlienOutpost implements Card {

	@Override
	public CardType getCardType() {
		return CardType.WORLD;
	}

	@Override
	public ConsumeBehaviour getConsumeBehaviour() {
		return null;
	}

	@Override
	public int getCost() {
		return 4;
	}

	@Override
	public DevelopBehaviour getDevelopBehaviour() {
		return null;
	}

	@Override
	public ExploreBehaviour getExploreBehaviour() {
		return null;
	}

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
	public MultiplierBehaviour getMultiplierBehaviour() {
		return null;
	}

	@Override
	public String getName() {
		return "Deserted Alien Outpost";
	}

	@Override
	public ProduceBehaviour getProduceBehaviour() {
		return null;
	}

	@Override
	public ProductionType getProductionType() {
		return ProductionType.WINDFALL;
	}

	@Override
	public SetType getSetType() {
		return SetType.BASE;
	}

	@Override
	public SettleBehaviour getSettleBehaviour() {
		return null;
	}

	@Override
	public GoodType getStorableGoodType() {
		return GoodType.ALIEN;
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
		return 3;
	}

	@Override
	public boolean isStartingWorld() {
		return false;
	}

	@Override
	public SpecialType getSpecialType() {
		return SpecialType.ALIEN;
	}
}

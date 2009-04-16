package org.luchini.rgalaxy.deck;

import org.luchini.rgalaxy.deck.enums.CardType;
import org.luchini.rgalaxy.deck.enums.GoodType;
import org.luchini.rgalaxy.deck.enums.MilitaryType;
import org.luchini.rgalaxy.deck.enums.ProductionType;
import org.luchini.rgalaxy.deck.enums.SetType;
import org.luchini.rgalaxy.deck.enums.SpecialType;

public interface Card {

	public String getName();
	public byte[] getImage();
	public int getCost();
	public int getVP();
	public int getStrength();
	public CardType getCardType();
	public ProductionType getProductionType();
	public MilitaryType getMilitaryType();
	public GoodType getStorableGoodType();
	public SpecialType getSpecialType();
	public boolean isStartingWorld();
	
	public ExploreBehaviour getExploreBehaviour();
	public DevelopBehaviour getDevelopBehaviour();
	public SettleBehaviour getSettleBehaviour();
	public TradeBehaviour getTradeBehaviour();
	public ConsumeBehaviour getConsumeBehaviour();
	public ProduceBehaviour getProduceBehaviour();
	
	public MultiplierBehaviour getMultiplierBehaviour();
	
	public SetType getSetType();
}

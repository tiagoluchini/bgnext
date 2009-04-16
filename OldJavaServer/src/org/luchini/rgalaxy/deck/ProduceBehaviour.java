package org.luchini.rgalaxy.deck;

import org.luchini.rgalaxy.deck.enums.GoodType;

public interface ProduceBehaviour extends PhaseBehaviour {

	public int specificProduce();
	public GoodType specificProduceGood();
	
	public int drawCard();
	
	public int drawForKind();
	public GoodType drawForKindGood();
	
	public int drawForEachGenesis();
	
	public int generalWindfallProduce();
	public int specificWindfallProduce();
	public GoodType specificWindfallProduceGood();
	
	public int drawForMostOfKind();
	public GoodType drawForMostOfKindGood();
	
}

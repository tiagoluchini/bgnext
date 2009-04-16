package org.luchini.rgalaxy.engine;

import org.luchini.rgalaxy.deck.Card;

public class CardCombo {

	private Card openCard;
	private Card productCard;
	
	public CardCombo(Card openCard) {
		this.openCard = openCard;
	}
	
	public Card getOpenCard() {
		return openCard;
	}
	public void setOpenCard(Card openCard) {
		this.openCard = openCard;
	}
	public Card getProductCard() {
		return productCard;
	}
	public void setProductCard(Card productCard) {
		this.productCard = productCard;
	}

	
}
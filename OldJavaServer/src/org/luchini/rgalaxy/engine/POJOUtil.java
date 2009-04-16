package org.luchini.rgalaxy.engine;

import java.util.ArrayList;
import java.util.List;

import org.luchini.rgalaxy.deck.Card;

public class POJOUtil {

	private POJOUtil() { /* util class */ }
	
	public static List<CardPOJO> convertCardsToPOJO(List<Card> cards) {
		List<CardPOJO> out = null;
		if (cards != null) {
			out = new ArrayList<CardPOJO>(cards.size());
			for (int i=0; i<cards.size(); i++) {
				out.add(new CardPOJO(cards.get(i)));
			}
		}
		return out;
	}
	
}

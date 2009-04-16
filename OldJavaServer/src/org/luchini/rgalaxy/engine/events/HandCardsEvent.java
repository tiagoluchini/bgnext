package org.luchini.rgalaxy.engine.events;

import java.util.List;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.events.AbstractSeatStateEvent;
import org.luchini.rgalaxy.deck.Card;
import org.luchini.rgalaxy.engine.CardPOJO;
import org.luchini.rgalaxy.engine.POJOUtil;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

public class HandCardsEvent extends AbstractSeatStateEvent {

	@TreeAttribute(alias="roomID") private RoomInfo roomInfo;
	
	@TreeNode private List<CardPOJO> cards;
	
	public HandCardsEvent(RoomInfo roomInfo, List<Card> cards) {
		super(roomInfo);
		this.roomInfo = roomInfo;
		this.cards = POJOUtil.convertCardsToPOJO(cards);
	}

	@Override
	public RoomInfo getRoomInfo() {
		return this.roomInfo;
	}

	public List<CardPOJO> getCards() {
		return this.cards;
	}
	
	@Override
	public String getReference() {
		return "HANDCARDS:" + this.getRoomInfo().getUniqueID();
	}

}

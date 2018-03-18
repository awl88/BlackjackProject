package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.cards.common.Card;

public class Hand {
	private List<Card> hand = new ArrayList<>();

	public void addCard(Card card) {
		hand.add(card);

	}

	public List<Card> getCardsInHand() {
		return hand;
	}

	public void clearHand() {
		hand.clear();

	}

	public int getValueOfHand() {
		int value = 0;
		boolean ace = false;
		for (Card card : hand) {
			value = value + card.getValue();
			if (card.getValue() == 11) {
				ace = true;
			}
		}
		if (ace && (value > 21)) {
			value -= 10;
		}
		return value;
	}
}

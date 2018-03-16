package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.cards.common.Card;

public class Hand {
	private List<Card> hand = new ArrayList<>();

	public void addCard(Card card) {
		hand.add(card);
		
	}

	public Hand getCardsInHand() {
		System.out.println(hand);
		return this;
	}

	public int getValueOfHand() {

		return -1;
	}
}

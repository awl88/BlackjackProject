package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> deckOfCards = new ArrayList<>(52);
	
	public Deck() {
		createDeck();
	}

	public List<Card> createDeck() {
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				Card c = new Card(s, r);
				deckOfCards.add(c);
			}

		}
		return deckOfCards;
	}

	public int checkDeckSize() {
		return deckOfCards.size();
	}

	public Card dealCard() {
		return deckOfCards.remove(0);
	}

	public void shuffle() {
		Collections.shuffle(deckOfCards);
	}

}

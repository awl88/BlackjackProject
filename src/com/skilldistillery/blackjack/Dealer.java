package com.skilldistillery.blackjack;

import com.skilldistillery.cards.common.Card;
import com.skilldistillery.cards.common.Deck;

public class Dealer extends Player {
	private Deck deck = new Deck();
	
	public Dealer(String name, Hand hand) {
		super(name, hand);
	}
	
	public void shuffleCards() {
		deck.shuffle();
	}
	
	public int checkDeckSize() { 
		return deck.checkDeckSize();
	}
	
	public void readdCards() {
		deck.createDeck();
	}
	
	public Card dealCards() {
		Card card = deck.dealCard();
		return card;
		
		
	}
	
}

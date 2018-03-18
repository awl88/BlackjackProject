package com.skilldistillery.blackjack;

import java.util.List;

import com.skilldistillery.cards.common.Card;

public abstract class Player {
	private String name;
	private Hand hand;
	private int wallet = 2500;

	public void addCard(Card card) {
		hand.addCard(card);
	}

	public Player(String name, Hand hand) {
		super();
		this.name = name;
		this.hand = hand;
	}

	public int getCardValue() {
		return hand.getValueOfHand();
	}

	public void clearHand() {
		hand.clearHand();
	}

	public int getWallet() {
		return wallet;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getHand() {
		return hand.getCardsInHand();
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

}

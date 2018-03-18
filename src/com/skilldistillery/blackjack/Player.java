package com.skilldistillery.blackjack;

import java.util.List;

import com.skilldistillery.cards.common.Card;

public abstract class Player {
	private String name;
	private Hand hand;
	private double wallet = 500;

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
	
	public void addMoney(double winnings) {
		wallet += winnings;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
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

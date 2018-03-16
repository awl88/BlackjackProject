package com.skilldistillery.blackjack;

public abstract class Player {
	private String name;
	private Hand hand;
	
	
	public Player(String name, Hand hand) {
		super();
		this.name = name;
		this.hand = hand;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Hand getHand() {
		return hand;
	}


	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	
	
	
}

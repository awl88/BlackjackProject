package com.skilldistillery.blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.skilldistillery.cards.common.Card;
import com.skilldistillery.cards.common.Deck;

public class Table {
	private Deck deck = new Deck();

	public static void main(String[] args) {
		Table deal = new Table();
		Scanner kb = new Scanner(System.in);
		deal.start(kb);
		kb.close();
	}

	public void start(Scanner kb) {
		System.out.print("Welcome to the Blackjack table. What is your name? ");
		String name = kb.next();
		Hand p1Hand = new Hand();
		Player p1 = new Gambler(name, p1Hand);
		
		
//		deck.shuffle();
//		System.out.print("How many cards would you like? ");
//		int dealAmount = 0;
//		try {
//			dealAmount = kb.nextInt();
//		} catch (InputMismatchException e) {
//			System.out.println("No, we need a number.");
//		}
//
//		if (dealAmount > 52) {
//			System.out.println("We only have " + deck.checkDeckSize() + " cards in the deck...");
//			return;
//		}
//		for (int i = 0; i < dealAmount; i++) {
//			Card card = deck.dealCard();
//			System.out.println(card.toString());
//		}
//		System.out.println("There are " + deck.checkDeckSize() + " cards left in the deck.");
	}

}

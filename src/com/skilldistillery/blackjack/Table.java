package com.skilldistillery.blackjack;

import java.util.Scanner;

import com.skilldistillery.cards.common.Card;

public class Table {

	public static void main(String[] args) {
		Table deal = new Table();
		Scanner kb = new Scanner(System.in);
		deal.start(kb);
		kb.close();
	}

	public void start(Scanner kb) {
		// Start game & create Player
		System.out.print("Welcome to the Blackjack table. What is your name? ");
		String name = kb.next();
		Hand p1Hand = new Hand();
		Player p1 = new Gambler(name, p1Hand);

		// Create Dealer
		Hand d1Hand = new Hand();
		Dealer d1 = new Dealer("Dealer", d1Hand);

		// Dealer shuffles deck
		d1.shuffleCards();

		// Dealer deals cards
		int x = 0;
		for (int i = 0; i < 2; i++) {
			Card gamblerCard = d1.dealCards();
			p1.addCard(gamblerCard);
			System.out.print("You were dealt a : " + gamblerCard + "  \t");
			Card dealerCard = d1.dealCards();
			if (x == 0) {
				System.out.println("The dealer deals himself a card facedown.");
				x++;
			} else {
				System.out.println("The dealer deals himself a " + dealerCard);
			}

		}
		while (true) {
			System.out.println("The cards in your hand right now are " + p1.getHand());
			System.out.println("Your hand's value is currently " + p1.getCardValue());
			System.out.println("==================");
			System.out.println("Would you like to:");
			System.out.println("1. Hit");
			System.out.println("2. Stay");
			int hitOrStay = kb.nextInt();
			if (hitOrStay == 1) {
				Card nextCard = d1.dealCards();
				p1.addCard(nextCard);
			} else {
				break;
			}
		}

		// Decide winners

		// deck.shuffle();
		// System.out.print("How many cards would you like? ");
		// int dealAmount = 0;
		// try {
		// dealAmount = kb.nextInt();
		// } catch (InputMismatchException e) {
		// System.out.println("No, we need a number.");
		// }
		//
		// if (dealAmount > 52) {
		// System.out.println("We only have " + deck.checkDeckSize() + " cards in the
		// deck...");
		// return;
		// }
		// for (int i = 0; i < dealAmount; i++) {
		// Card card = deck.dealCard();
		// System.out.println(card.toString());
		// }
		// System.out.println("There are " + deck.checkDeckSize() + " cards left in the
		// deck.");
	}

}

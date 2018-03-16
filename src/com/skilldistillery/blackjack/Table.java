package com.skilldistillery.blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.skilldistillery.cards.common.Card;

public class Table {
	static Scanner kb = new Scanner(System.in);
	// Create player
	Hand p1Hand = new Hand();
	Player p1 = new Gambler("", p1Hand);

	// Create Dealer
	Hand d1Hand = new Hand();
	Dealer d1 = new Dealer("Dealer", d1Hand);

	public static void main(String[] args) {
		Table deal = new Table();
		deal.start(kb);
		kb.close();
	}

	public void start(Scanner kb) {
		// Start game
		System.out.print("Welcome to the Blackjack table. What is your name? ");
		String name = kb.next();

		p1.setName(name);

		// Dealer shuffles deck
		d1.shuffleCards();

		// Call game method
		while (true) {
			game();

			System.out.print("Would you like to play another round? (Y/N) ");
			String playAgain = "N";
			try {
				playAgain = kb.next();
			} catch (InputMismatchException e) {
				System.out.println("Please input Y or N");
			}
			if (playAgain.equalsIgnoreCase("Y")) {
				game();
			} else if (playAgain.equalsIgnoreCase("N")) {
				System.out.println("Thank you for playing, have a great day.");
			} else {
				System.out.println("Please input Y or N");
			}
		}

	}

	public void game() {
		// Dealer deals cards
		int x = 0;
		for (int i = 0; i < 2; i++) {
			Card gamblerCard = d1.dealCards();
			p1.addCard(gamblerCard);
			System.out.print("You were dealt a : " + gamblerCard + "  \t");
			Card dealerCard = d1.dealCards();
			d1.addCard(dealerCard);

			if (x == 0) {
				System.out.println("The dealer deals himself a card facedown.");
				x++;
			} else {
				System.out.println("The dealer deals himself a " + dealerCard);
			}

		}
		// Hit or stay?
		boolean keepGoing = true;
		System.out.println();
		System.out.println("==It is now your turn==");
		System.out.println();
		while (keepGoing) {
			while (true) {
				System.out.println("The cards in your hand right now are " + p1.getHand());
				System.out.println("Your hand's value is currently " + p1.getCardValue());
				System.out.println("==================");
				if (p1.getCardValue() > 21) {
					System.out.println("You bust!");
					youLose();
					keepGoing = false;
					break;
				}
				System.out.println("Would you like to:");
				System.out.println("1. Hit");
				System.out.println("2. Stay");
				int hitOrStay = kb.nextInt();
				if (hitOrStay == 1) {
					Card hitMe = d1.dealCards();
					p1.addCard(hitMe);
				} else {
					break;
				}
			}
			System.out.println();
			System.out.println("==It is now the Dealer's turn==");
			System.out.println();
			System.out.println("The dealer shows his hand, he currently has " + d1.getHand());
			System.out.println("The value of the dealer's hand is currently " + d1.getCardValue());
			// Dealer checks his hand, if < 17, dealer hits
			while (d1.getCardValue() < 17) {
				Card hitMe = d1.dealCards();
				System.out.println("The dealer drew a " + hitMe);
				d1.addCard(hitMe);
				System.out.println("The dealer's hand is currently " + d1.getHand());
				System.out.println("The value of the dealer's hand is currently " + d1.getCardValue());
			}
			if (d1.getCardValue() > 21) {
				System.out.println("Dealer busts!");
				youWin();
				keepGoing = false;
			}
			// Decide winners
			if (p1.getCardValue() == d1.getCardValue()) {
				System.out.println("It's a tie!");
			} else if (p1.getCardValue() > d1.getCardValue()) {
				youWin();
			} else if (p1.getCardValue() < d1.getCardValue()) {
				youLose();
			}
		}
	}

	public void youWin() {
		System.out.println();
		System.out.println("===================");
		System.out.println("You win this round!");
		System.out.println("===================");
		System.out.println();
	}

	public void youLose() {
		System.out.println();
		System.out.println("===================");
		System.out.println("You lose this round. Better luck next time!");
		System.out.println("===================");
		System.out.println();
	}

}

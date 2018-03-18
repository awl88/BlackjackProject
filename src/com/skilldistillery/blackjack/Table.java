package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.common.Card;

public class Table {
	static Scanner kb = new Scanner(System.in);
	private double bet;
	// Create ArrayList to hold players
	List<Player> players = new ArrayList<>();

	// Create separate ArrayList to also hold additional players
	List<Gambler> others = new ArrayList<>();

	// Create ArrayList to hold scores
	List<Integer> scores = new ArrayList<>();

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
		// Add PC && Dealer to ArrayList
		players.add(p1);

		// Start game
		System.out.println("You walk into the casino with $500. Your goal is to win as much as you can. Good luck!");
		System.out.print("Welcome to the Blackjack table. What is your name? ");
		String name = kb.next();

		// Ask to add other players
		System.out.print("How many other players would you like to play against? (up to 4) ");
		int otherAmount = kb.nextInt();

		// If otherAmount > 0, create other players, give name, and add to ArrayLists
		String[] otherPlayerNames = new String[otherAmount];
		for (int i = 0; i < otherAmount; i++) {
			System.out.print("Please give a name to player " + i + ": ");
			String otherName = kb.next();
			otherPlayerNames[i] = otherName;
		}
		Hand[] otherPlayerHands = new Hand[otherAmount];
		for (int i = 0; i < otherAmount; i++) {
			Hand otherHand = new Hand();
			otherPlayerHands[i] = otherHand;
		}

		for (int i = 0; i < otherPlayerNames.length; i++) {
			Gambler temp = new Gambler(otherPlayerNames[i], otherPlayerHands[i]);
			players.add(temp);
			others.add(temp);
		}

		// Add Dealer to ArrayList
		players.add(d1);

		p1.setName(name);

		System.out.println("Nice to meet you " + p1.getName() + ".");
		System.out.println();

		// Dealer shuffles deck
		d1.shuffleCards();
		System.out.println("The dealer is shuffling the deck...");
		System.out.println("   Shuffling...");
		System.out.println("\tShuffling...");
		System.out.println("The dealer will now deal cards:");
		System.out.println();

		// Call game method
		gamePlay();
	}

	public void game() {
		while (true) {

			System.out.print("Would you like to play another round? (Y/N) ");
			String playAgain = "N";
			try {
				playAgain = kb.next();
			} catch (InputMismatchException e) {
				System.out.println("Please input Y or N");
			}
			if (playAgain.equalsIgnoreCase("Y")) {
				if (d1.checkDeckSize() < players.size() * 3) {
					System.out.println("It appears there are not enough cards to play a round.");
					System.out.println("Please wait while the Dealer re-shuffles the cards into the deck.");
					System.out.println("   Shuffling...");
					System.out.println("\tShuffling...");
					System.out.println("We are ready to go.");
					d1.readdCards();
				}
				gamePlay();
			} else if (playAgain.equalsIgnoreCase("N")) {
				System.out.println("Thank you for playing, have a great day.");
				System.exit(0);
			} else {
				System.out.println("Please input Y or N");
			}
		}
	}

	public void gamePlay() {
		// Place bets
		System.out.print("Your wallet currently has " + p1.getWallet() + ". Please enter your bet: ");
		bet = kb.nextDouble();

		// Dealer deals cards
		System.out.println("The dealer will now deal cards:");
		int faceUp = 0;
		for (int i = 0; i < 2; i++) {
			Card gamblerCard = d1.dealCards();
			p1.addCard(gamblerCard);
			System.out.print("You were dealt a : " + gamblerCard + "  \t");
			Card dealerCard = d1.dealCards();
			d1.addCard(dealerCard);
			for (int j = 0; j < others.size(); j++) {
				Gambler other = others.get(i);
				Card otherCard = d1.dealCards();
				System.out.print(other.getName() + " was dealt a " + otherCard + "\t");
				other.addCard(otherCard);
			}

			if (faceUp == 0) {
				System.out.println("The dealer deals himself a card facedown.");
				faceUp++;
			} else {
				System.out.println("The dealer deals himself a " + dealerCard);
			}

		}
		playerTurn();
		scores.add(p1.getCardValue());
		otherPlayersTurns();
		dealerTurn();
		scores.add(d1.getCardValue());
		getWinner();

	}

	public void playerTurn() {
		boolean keepGoing = true;
		System.out.println();
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665");
		System.out.println("\u2660 It is now your turn \u2666");
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665");
		System.out.println();
		// Hit or stay?
		while (keepGoing) {
			System.out.println();
			System.out.println("The cards in your hand right now are " + p1.getHand());
			System.out.println("Your hand's value is currently " + p1.getCardValue());

			System.out.println("Would you like to:");
			System.out.println("\t1. Hit");
			System.out.println("\t2. Stay");
			while (true) {
				int hitOrStay = 0;
				try {
					hitOrStay = kb.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Please input 1 to hit, or 2 to stay");
				}
				if (hitOrStay == 1) {
					Card hitMe = d1.dealCards();
					System.out.println("You were dealt a " + hitMe);
					p1.addCard(hitMe);
					break;
				} else if (hitOrStay == 2) {
					keepGoing = false;
					break;
				} else {
					continue;
				}
			}
			
			// Check to see if player bust
			if (p1.getCardValue() > 21) {
				System.out.println();
				System.out.println("\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660");
				System.out.println("\u2660 You bust! \u2666");
				System.out.println("\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660");
				System.out.println();
				keepGoing = false;
				break;
			}
		}
	}

	public void otherPlayersTurns() {
		// Each other player takes a turn
		for (int i = 0; i < (others.size()); i++) {
			Player otherPlayer = others.get(i);
			System.out.println("It is now " + otherPlayer.getName() + "'s turn.");
			System.out.println();
			System.out.println(otherPlayer.getName() + " currently has " + otherPlayer.getHand());
			System.out.println(
					"The value of" + otherPlayer.getName() + "'s hand is currently " + otherPlayer.getCardValue());
			System.out.println();
			
			// Randomly decide if they will hit or stay
			int doTheyBet = (int) (Math.random() * 3) + 14;
			while (otherPlayer.getCardValue() < doTheyBet) {
				Card hitMe = d1.dealCards();
				System.out.println(otherPlayer.getName() + " decides to hit...");
				System.out.println(otherPlayer.getName() + " was dealt a " + hitMe);
				otherPlayer.addCard(hitMe);
				
				// Check to see if they bust
				if (otherPlayer.getCardValue() > 21) {
					System.out.println();
					System.out.println(otherPlayer.getName() + " bust!");
					System.out.println();
					break;
				}
				System.out.println(otherPlayer.getName() + " hand is currently " + otherPlayer.getHand());
				System.out.println(
						"The value of " + otherPlayer.getName() + "'s hand is currently " + otherPlayer.getCardValue());
				System.out.println();
			}
			scores.add(otherPlayer.getCardValue());
		}
	}

	public void dealerTurn() {
		System.out.println();
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666");
		System.out.println("\u2660 It is now the Dealer's turn \u2666");
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666");
		System.out.println();
		System.out.println("The dealer shows his hand, he currently has " + d1.getHand());
		System.out.println("The value of the dealer's hand is currently " + d1.getCardValue());
		System.out.println();

		// Dealer checks his hand, if < 17, dealer hits
		while (d1.getCardValue() < 17) {
			Card hitMe = d1.dealCards();
			System.out.println("The dealer drew a " + hitMe);
			d1.addCard(hitMe);

			// Did dealer bust?
			if (d1.getCardValue() > 21) {
				System.out.println();
				System.out.println(
						"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666");
				System.out.println("\u2663 Dealer bust! \u2665");
				System.out.println(
						"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666");
				System.out.println();
			}
			System.out.println("The dealer's hand is currently " + d1.getHand());
			System.out.println("The value of the dealer's hand is currently " + d1.getCardValue());
			System.out.println();

		}
	}

	// Decide winners
	public void getWinner() {

		Player winner = new Gambler("", p1Hand);
		int winningNumber = 0;
		for (int i = 0; i < scores.size(); i++) {
			int score = scores.get(i);
			if ((score > winningNumber) && (score <= 21)) {
				winningNumber = scores.get(i);
				if (i == 0) {
					winner = p1;
				} else if (i == scores.size() - 1) {
					winner = d1;
				} else {
					winner = players.get(i);
				}
			} else if ((score == winningNumber)) {

			}
		}
		if (winningNumber == 0) {
			System.out.println("You won!");
		} else if (winningNumber == 1) {
			System.out.println("Dealer wins!");
		} else {
			System.out.println(winner.getName() + " wins!");
		}

		Player clearHand;
		for (int i = 0; i < players.size(); i++) {
			clearHand = players.get(i);
			clearHand.clearHand();
			scores.clear();
		}
		game();
	}

}

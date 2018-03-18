package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.common.Card;

public class Table {
	static Scanner kb = new Scanner(System.in);
	private int pot;

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
		System.out.println("You find a Blackjack table and sit down.");
		System.out.print("Dealer: \"Welcome to the Blackjack table. What is your name?\" ");
		String name = kb.next();

		// Ask to add other players
		System.out.print("How many other players would you like to play against? (up to 5) ");
		int otherAmount = kb.nextInt();

		// If otherAmount > 0, create other players, give name, and add to ArrayLists
		String[] otherPlayerNames = new String[otherAmount];
		for (int i = 0; i < otherAmount; i++) {
			System.out.print("Please give a name to player " + (i + 1) + ": ");
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

		System.out.println("\"Nice to meet you " + p1.getName() + ".\"");
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
					System.out.println();
					System.out.println("It appears there are not enough cards to play a round.");
					System.out.println("Please wait while the Dealer re-shuffles the cards into the deck.");
					System.out.println("   Shuffling...");
					System.out.println("\tShuffling...");
					System.out.println("Dealer: \"We are ready to go.\"s");
					System.out.println();
					d1.readdCards();
				}
				gamePlay();
			} else if (playAgain.equalsIgnoreCase("N")) {
				System.out.println("Dealer: \"Thank you for playing, have a great day.\"");
				System.exit(0);
			} else {
				System.out.println("Please input Y or N");
			}
		}
	}

	public void gamePlay() {
		// Clear pot
		pot = 0;

		int bet;
		while (true) {
			// Place bets
			System.out.print("Your wallet currently has " + p1.getWallet() + ". Please enter your bet: $");
			bet = kb.nextInt();
			if (bet > p1.getWallet()) {
				System.out
						.println("You don't have that much money! Please enter a number between 1 & " + p1.getWallet());
				continue;
			}
			p1.setWallet(p1.getWallet() - bet);
			pot += bet;
			break;
		}

		for (int i = 0; i < others.size(); i++) {
			Player tempBet = others.get(i);
			int betRandom = (int) (Math.random() * 500) + 1;
			if (tempBet.getWallet() < 500) {
				betRandom = (int) (Math.random() * (tempBet.getWallet() - 1) + 1);

			}
			tempBet.setWallet(tempBet.getWallet() - betRandom);
			System.out.println(tempBet.getName() + " has bet $" + betRandom);
			pot += betRandom;
		}

		// Dealer deals cards
		System.out.println("The dealer will now deal cards:");

		// Deal to player
		Card one = d1.dealCards();
		Card two = d1.dealCards();
		p1.addCard(one);
		p1.addCard(two);
		System.out.println("\tYou were dealt a " + one + " & a " + two);
		// Deal to others
		for (int i = 0; i < others.size(); i++) {
			Player tempCard = others.get(i);
			one = d1.dealCards();
			two = d1.dealCards();
			tempCard.addCard(one);
			tempCard.addCard(two);
			System.out.println("\t" + tempCard.getName() + " was dealt a " + one + " & a " + two);
		}

		// Deal to Dealer
		one = d1.dealCards();
		two = d1.dealCards();
		d1.addCard(one);
		d1.addCard(two);
		System.out.println("\tThe dealer dealt himself a card facedown & a " + two);

		// Go through turns
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
		System.out.println("\u2660\u2663 It is now your turn \u2665\u2666");
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
				System.out.println("You bust!");
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
			System.out.println("\u2660\u2663 It is now " + otherPlayer.getName() + "'s turn \u2665\u2666");
			System.out.println();
			System.out.println(otherPlayer.getName() + " currently has " + otherPlayer.getHand());
			System.out.println(
					"The value of " + otherPlayer.getName() + "'s hand is currently " + otherPlayer.getCardValue());
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
		System.out.println("\u2660\u2663 It is now the Dealer's turn \u2665\u2666");
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

	public void getWinner() {

		// Decide winner
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

		// Give money to winner
		winner.setWallet((winner.getWallet() + pot));

		if (winner == p1) {
			System.out.println("You won!");
			System.out.println("You made $" + pot);
		} else if (winner == d1) {
			System.out.println("Dealer wins!");
			System.out.println("Dealer: \"Better luck next time!\"");
		} else {
			System.out.println(winner.getName() + " wins!");
			System.out.println(winner.getName() + " made " + pot);
			System.out.println("They now have $" + winner.getWallet() + " in their wallet.");
		}

		// Clear hands of cards
		Player clearHand;
		for (int i = 0; i < players.size(); i++) {
			clearHand = players.get(i);
			clearHand.clearHand();
			scores.clear();
		}

		// Eliminate players
		for (int i = 0; i < others.size(); i++) {
			Player tempBrokeCheck = others.get(i);
			if (tempBrokeCheck.getWallet() <= 0) {
				System.out.println("Say goodbye to " + tempBrokeCheck.getName() + ", they are out of money! ");
				others.remove(i);
			}

		}
		for (int i = 0; i < others.size(); i++) {
			Player tempBrokeCheck = others.get(i);
			if (tempBrokeCheck.getWallet() <= 0) {
				System.out.println("Say goodbye to " + tempBrokeCheck.getName() + ", they are out of money! ");
				others.remove(i);
			}

		}
		// Check player's wallet to see if they have enough to continue
		if (p1.getWallet() <= 0) {
			System.out.println("You are out of money!!!");
			System.out.println("GAME OVER");
			System.out.println("Better luck next time");
			System.exit(0);
		}

		// Call game method to restart
		game();
	}

}

package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.common.Card;

public class Table {
	static Scanner kb = new Scanner(System.in);
	private double bet;
	// Create player
	Hand p1Hand = new Hand();
	Player p1 = new Gambler("", p1Hand);

	// Create Dealer
	Hand d1Hand = new Hand();
	Dealer d1 = new Dealer("Dealer", d1Hand);

	// Create other players incase
	List<Gambler> others = new ArrayList<>();
	
	// Create ArrayList to hold scores
	List<Integer> scores = new ArrayList<>();

	public static void main(String[] args) {
		Table deal = new Table();
		deal.start(kb);
		kb.close();
	}

	public void start(Scanner kb) {
		// Start game
		System.out.println("You walk into the casino with $500. Your goal is to win as much as you can. Good luck!");
		System.out.print("Welcome to the Blackjack table. What is your name? ");
		String name = kb.next();
		
		System.out.print("How many other players would you like to play against? (up to 4) ");
		int otherAmount = kb.nextInt();
		
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
			others.add(temp);
		}
		
		

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
				if (d1.checkDeckSize() < 7) {
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
		otherPlayersTurns();
		dealerTurn();

	}

	public void playerTurn() {
		// Hit or stay?
		// int keepRunning = 0;
		boolean keepGoing = true;
		System.out.println();
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665");
		System.out.println("\u2660 It is now your turn \u2666");
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665");
		System.out.println();
		while (keepGoing) {
			if (p1.getCardValue() > 21) {
				System.out.println();
				System.out.println("\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660");
				System.out.println("\u2660 You bust! \u2666");
				System.out.println("\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660");
				System.out.println();
				youLose();
				break;
				// keepRunning = 1;
				// keepGoing = false;
			}
			// while (true) {
			System.out.println();
			System.out.println("The cards in your hand right now are " + p1.getHand());
			System.out.println("Your hand's value is currently " + p1.getCardValue());
			// }

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
					scores.add(p1.getCardValue());
					break;
				} else {
					continue;
				}
			}
		}
	}
	
	public void otherPlayersTurns() {
		for (int i = 0; i < others.size(); i++) {
			Gambler otherPlayer = others.get(i);
			System.out.println("It is now " + otherPlayer.getName() + "'s turn.");
			System.out.println();
			System.out.println(otherPlayer.getName() + " currently has " + otherPlayer.getHand());
			System.out.println("The value of" + otherPlayer.getName() + "'s hand is currently " + otherPlayer.getCardValue());
			System.out.println();
			int doTheyBet = (int)(Math.random() * 5) + 12;
			while (otherPlayer.getCardValue() < doTheyBet) {
				Card hitMe = d1.dealCards();
				System.out.println(otherPlayer.getName() + " drew a " + hitMe);
				otherPlayer.addCard(hitMe);
				if (otherPlayer.getCardValue() > 21) {
					System.out.println();
					System.out.println(otherPlayer.getName() + " bust!");
					System.out.println();
				break;
				}
				System.out.println(otherPlayer.getName() + " hand is currently " + otherPlayer.getHand());
				System.out.println("The value of" + otherPlayer.getName() + "'s hand is currently " + otherPlayer.getCardValue());
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
			if (d1.getCardValue() > 21) {
				System.out.println();
				System.out.println(
						"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666");
				System.out.println("\u2663 Dealer bust! \u2665");
				System.out.println(
						"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666");
				System.out.println();
				youWin();
			}
			System.out.println("The dealer's hand is currently " + d1.getHand());
			System.out.println("The value of the dealer's hand is currently " + d1.getCardValue());
			System.out.println();
			scores.add(d1.getCardValue());
		}

		// Decide winners
		if (p1.getCardValue() == d1.getCardValue()) {
			System.out.println(
					"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666");
			System.out.println("\u2663 It's a tie! \u2665");
			System.out.println(
					"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666");
			p1.clearHand();
			d1.clearHand();
			game();
		} else if (p1.getCardValue() > d1.getCardValue()) {
			youWin();
		} else if (p1.getCardValue() < d1.getCardValue()) {
			youLose();
		}
	}

	public void youWin() {
		System.out.println();
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665");
		System.out.println("You win this round!");
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665");
		System.out.println();
		p1.addMoney(bet);
		System.out.println("Your new wallet size is " + p1.getWallet() + ".");
		System.out.println();
		p1.clearHand();
		d1.clearHand();
		game();

	}

	public void youLose() {
		System.out.println();
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2660\u2663");
		System.out.println("\u2660 You lose this round. Better luck next time! \u2666");
		System.out.println(
				"\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2660\u2663\u2665\u2666\u2660\u2663\u2665\u2660\u2663");
		System.out.println();
		p1.addMoney(-bet);
		System.out.println("Your new wallet size is " + p1.getWallet() + ".");
		System.out.println();
		if (p1.getWallet() <= 0) {
			System.out.println("It looks like you have run out of money.");
			System.out.println("\tGAME OVER");
			System.out.println("  Better luck next time.");
			System.exit(0);
		}
		p1.clearHand();
		d1.clearHand();
		game();

	}

}

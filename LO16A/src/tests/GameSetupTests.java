/*
 * Class: GameSetupTests
 * 
 * Details:
 * tests the set up of players, deck, and answer
 * 
 * Authors: Alaa Eddine Boukentar Assefar
 * 			Sophie Sengelmann-Lipps
 * 
 * Start Date: 11/05/2026
 * 
 * Collaborators: None
 * 
 * Sources: None
 */
package tests;
import static org.junit.jupiter.api.Assertions.*;


import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

class GameSetupTests {
	// our game will always have 6 players (1 human and 5 coms)
	/*
	
	Set <Player> players;
	Card cardPlayer, cardWeapon, cardRoom;
	Player person, com1, com2, com3, com4, com5;
	*/
	Board board;
	
	@BeforeEach 
	void initialization() {
		/*
		players = new HashSet<>();
		cardPlayer = new Card(CardType.PERSON);
		cardWeapon = new Card(CardType.WEAPON);
		cardRoom = new Card(CardType.ROOM);
		person = new Player("Professor Plum", Color.pink, true, 0, 5);
		com1 = new Player("Miss Scarlett", Color.red, false, 7, 23);
		com2 = new Player("Colonel Mustard", Color.yellow, false, 7, 23);
		com3 = new Player("Mrs. White", Color.white, false, 7, 23);
		com4 = new Player("Mr. Green ", Color.green, false, 7, 23);
		com5 = new Player("Mrs. Peacock", Color.blue, false, 7, 23);
		*/
		// Board is singleton, get the only instance
		board = Board.getInstance();
		//set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
	}
	

	@Test
	void testPlayer() {
		assertEquals("Professor Plum", board.person.getName());
		assertTrue(board.person.isHuman());
		
		assertEquals(Color.red, board.com1.getColor());
		assertTrue(!board.com1.isHuman());

		
		//check players starting position (picked 2 human player and player #4)
		BoardCell cell = new BoardCell(0,5);
		assertEquals(cell, board.person.getLocation());
		cell = new BoardCell(23, 13);
		assertEquals(cell, board.com4.getLocation());
		
		//check if the player loads cards
		BoardCell cell1 = new BoardCell(12,6);
		Player p1 = new Player ("john", Color.black, true, 12, 6);
		p1.setLocation(cell1);
		
		Card card1 = new Card(CardType.ROOM, "Kitchen");
		Card card2 = new Card(CardType.PERSON, "Mrs. White");
		Card card3 = new Card(CardType.WEAPON, "Gun");
		Card card4 = new Card(CardType.WEAPON, "Machine Gun");	
		p1.setHand(card1);
		p1.setHand(card2);
		p1.setHand(card3);
		
		assertTrue(p1.getHand().contains(card1));
		assertTrue(p1.getHand().contains(card2));
		assertTrue(p1.getHand().contains(card3));
		assertTrue(!p1.getHand().contains(card4));
		
		
	}
	@Test
	void testDeck() {
		Card card1 = new Card(CardType.ROOM, "Kitchen");
		Card card2 = new Card(CardType.PERSON, "Mrs. White");
		Card card3 = new Card(CardType.WEAPON, "Gun");
		Card card4 = new Card(CardType.ROOM, "");
		Card card5 = new Card(CardType.ROOM, " ");
		Card card6 = new Card(CardType.PERSON, " ");
		Card card7 = new Card(CardType.WEAPON, " ");
		
		assertTrue(board.deck.contains(card1));
		assertTrue(board.deck.contains(card2));
		assertTrue(board.deck.contains(card3));
		
		assertTrue(!board.deck.contains(card4));
		assertTrue(!board.deck.contains(card5));
		assertTrue(!board.deck.contains(card6));
		assertTrue(!board.deck.contains(card7));
		
		assertEquals(18, board.deck.size());
		
	}

	@Test
	void testCardsDelt() {
		//we will always have 6 players (1 human and 5 comps)
		assertEquals(board.person.getHand().size(), board.com1.getHand().size());
		assertEquals(board.com1.getHand().size(), board.com2.getHand().size());
		assertEquals(board.com2.getHand().size(), board.com3.getHand().size());
		assertEquals(board.com3.getHand().size(), board.com4.getHand().size());
		assertEquals(board.com4.getHand().size(), board.com5.getHand().size());
		//check if all cards dealt are unique
		Set<Card> allCards = new HashSet<>();
		allCards.addAll(board.person.getHand());
		allCards.addAll(board.com1.getHand());
		allCards.addAll(board.com2.getHand());
		allCards.addAll(board.com3.getHand());
		allCards.addAll(board.com4.getHand());
		allCards.addAll(board.com5.getHand());
		
		assertEquals(18, allCards.size());
		//check if the answer cards are unique
		
		allCards.addAll(board.solution.getSolution());		
		assertEquals(21, allCards.size());
		
	}
	

}

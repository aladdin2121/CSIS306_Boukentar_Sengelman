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
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;

class GameSetupTests {
	// our game will always have 6 players (1 human and 5 coms)
	Board board;
	
	@BeforeEach 
	void initialization() {
		
		// Board is singleton, get the only instance
		board = Board.getInstance();
		//set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
	}
	

	@Test
    public void testPlayer() {
        Collection<Player> players = board.getPlayers();

        assertEquals(6, players.size());

        Player person = findPlayer("Professor Plum");
        assertNotNull(person);
        assertEquals(Color.PINK, person.getColor());
        assertTrue(person.isHuman());
        assertTrue(person instanceof HumanPlayer);

        assertEquals(0, person.getLocation().getRow());
        assertEquals(5, person.getLocation().getCol());

        Player com1 = findPlayer("Miss Scarlett");
        assertNotNull(com1);
        assertEquals(Color.RED, com1.getColor());
        assertFalse(com1.isHuman());
        assertTrue(com1 instanceof ComputerPlayer);
        assertEquals(0, com1.getLocation().getRow());
        assertEquals(13, com1.getLocation().getCol());

        Player com2 = findPlayer("Colonel Mustard");
        assertNotNull(com2);
        assertEquals(7, com2.getLocation().getRow());
        assertEquals(23, com2.getLocation().getCol());

        Player com3 = findPlayer("Mrs. White");
        assertNotNull(com3);
        assertEquals(19, com3.getLocation().getRow());
        assertEquals(23, com3.getLocation().getCol());

        Player com4 = findPlayer("Mr. Green");
        assertNotNull(com4);
        assertEquals(23, com4.getLocation().getRow());
        assertEquals(13, com4.getLocation().getCol());

        Player com5 = findPlayer("Mrs. Peacock");
        assertNotNull(com5);
        assertEquals(20, com5.getLocation().getRow());
        assertEquals(0, com5.getLocation().getCol());

        int humanCount = 0;
        int computerCount = 0;

        for (Player player : players) {
            if (player.isHuman()) {
                humanCount++;
            } else {
                computerCount++;
            }
        }

        assertEquals(1, humanCount);
        assertEquals(5, computerCount);
    }

    // Check that the deck contains the cards from the setup file.
    @Test
    public void testDeck() {
        Collection<Card> deck = board.getDeck();
        Set<Card> allCards = new HashSet<>(deck);

        allCards.addAll(board.getAnswer().getSolution());

        assertEquals(21, allCards.size());

        // Check that the deck contains the expected card types.
        int roomCount = 0;
        int personCount = 0;
        int weaponCount = 0;

        for (Card card : allCards) {
            if (card.getType() == CardType.ROOM) {
                roomCount++;
            } else if (card.getType() == CardType.PERSON) {
                personCount++;
            } else if (card.getType() == CardType.WEAPON) {
                weaponCount++;
            }
        }

        assertEquals(9, roomCount);
        assertEquals(6, personCount);
        assertEquals(6, weaponCount);

        // Check that example cards exist in the complete collection.
        assertTrue(allCards.contains(new Card(CardType.ROOM, "Kitchen")));
        assertTrue(allCards.contains(new Card(CardType.PERSON, "Mrs. White")));
        assertTrue(allCards.contains(new Card(CardType.WEAPON, "Gun")));

        // Check that cards with empty names are not included.
        assertFalse(allCards.contains(new Card(CardType.ROOM, "")));
        assertFalse(allCards.contains(new Card(CardType.ROOM, " ")));
        assertFalse(allCards.contains(new Card(CardType.PERSON, " ")));
        assertFalse(allCards.contains(new Card(CardType.WEAPON, " ")));
    }

    // Check that the answer contains one card of each type.
    @Test
    public void testAnswer() {
        Set<Card> answer = board.getAnswer().getSolution();

        assertEquals(3, answer.size());

        int roomCount = 0;
        int personCount = 0;
        int weaponCount = 0;

        for (Card card : answer) {
            if (card.getType() == CardType.ROOM) {
                roomCount++;
            } else if (card.getType() == CardType.PERSON) {
                personCount++;
            } else if (card.getType() == CardType.WEAPON) {
                weaponCount++;
            }
        }

        assertEquals(1, roomCount);
        assertEquals(1, personCount);
        assertEquals(1, weaponCount);
    }

    // Check that all cards are dealt and no card is dealt twice.
    @Test
    public void testCardsDealt() {
        Collection<Player> players = board.getPlayers();

        Set<Card> allCards = new HashSet<>();

        for (Player player : players) {
            assertEquals(3, player.getHand().size());

            for (Card card : player.getHand()) {
                assertTrue(allCards.add(card), "A card was dealt more than once.");
            }
        }

        for (Card card : board.getAnswer().getSolution()) {
            assertTrue(allCards.add(card), "An answer card was dealt to a player.");
        }

        assertEquals(21, allCards.size());
    }

    // Check that players can hold cards in their hands.
    @Test
    public void testPlayerHand() {
        BoardCell cell = board.getCell(12, 6);

        Player player = new HumanPlayer("John", Color.BLACK, cell);

        Card card1 = new Card(CardType.ROOM, "Kitchen");
        Card card2 = new Card(CardType.PERSON, "Mrs. White");
        Card card3 = new Card(CardType.WEAPON, "Gun");
        Card card4 = new Card(CardType.WEAPON, "Machine Gun");

        player.setHand(card1);
        player.setHand(card2);
        player.setHand(card3);

        assertTrue(player.getHand().contains(card1));
        assertTrue(player.getHand().contains(card2));
        assertTrue(player.getHand().contains(card3));
        assertFalse(player.getHand().contains(card4));

        assertEquals(3, player.getHand().size());
    }

    // Find a player by name.
    private Player findPlayer(String name) {
        for (Player player : board.getPlayers()) {
            if (player.getName().equals(name)) {
                return player;
            }
        }

        return null;
    }
}

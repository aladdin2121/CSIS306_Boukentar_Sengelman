
/*
 * Class: GameSolutionTest
 *
 * Details:
 * Tests checking accusations, disproving suggestions, and handling suggestions.
 *
 * Authors: Alaa Eddine Boukentar Assefar
 *          Sophie Sengelmann-Lipps
 *
 * Start Date: 11/09/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {

	Board board;

	private Card roomCard;
	private Card personCard;
	private Card weaponCard;

	@BeforeEach
	void initialization() {
		// Board is a singleton, so get the only instance.
		board = Board.getInstance();
		// Use our configuration files.
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize the board and players.
		board.initialize();

		// Set a known answer so accusation tests are deterministic.
		roomCard = new Card(CardType.ROOM, "Kitchen");
		personCard = new Card(CardType.PERSON, "Professor Plum");
		weaponCard = new Card(CardType.WEAPON, "Gun");

		board.getAnswer().setSolution(roomCard, personCard, weaponCard);

		// Clear randomly dealt hands so each test starts with known cards.
		for (Player player : board.getPlayers()) {
			player.getHand().clear();
		}

		assertEquals(6, board.getPlayers().size());
	}

	// Confirm that accusation with correct room, person, and weapon is correct.
	@Test
	void testCheckAccusationCorrect() {
		Solution accusation = new Solution(roomCard, personCard, weaponCard);

		assertTrue(board.checkAccusation(accusation));
	}

	// Confirm that accusation with wrong person is incorrect.
	@Test
	void testCheckAccusationWrongPerson() {
		Card wrongPerson = new Card(CardType.PERSON, "Miss Scarlett");

		Solution accusation = new Solution(roomCard, wrongPerson, weaponCard);

		assertFalse(board.checkAccusation(accusation));
	}

	// Confirm that accusation with wrong weapon is incorrect.
	@Test
	void testCheckAccusationWrongWeapon() {
		Card wrongWeapon = new Card(CardType.WEAPON, "Knife");

		Solution accusation = new Solution(roomCard, personCard, wrongWeapon);

		assertFalse(board.checkAccusation(accusation));
	}

	// Confirm that accusation with wrong room is incorrect.
	@Test
	void testCheckAccusationWrongRoom() {
		Card wrongRoom = new Card(CardType.ROOM, "Study");

		Solution accusation = new Solution(wrongRoom, personCard, weaponCard);

		assertFalse(board.checkAccusation(accusation));
	}

	// Confirm that player returns matching card when only one card matches.
	@Test
	void testDisproveSuggestionOneMatchingCard() {
		Player player = new HumanPlayer("Test Player", Color.PINK, board.getCell(12, 6));

		player.updateHand(weaponCard);

		Solution suggestion = new Solution(new Card(CardType.ROOM, "Study"), new Card(CardType.PERSON, "Miss Scarlett"),
				weaponCard);

		assertEquals(weaponCard, player.disproveSuggestion(suggestion));
	}

	// Confirm that player randomly selects between multiple matching cards.
	@Test
	void testDisproveSuggestionMultipleMatchingCards() {
		Player player = new HumanPlayer("Test Player", Color.PINK, board.getCell(12, 6));

		Card person = new Card(CardType.PERSON, "Professor Plum");
		Card weapon = new Card(CardType.WEAPON, "Gun");

		player.updateHand(person);
		player.updateHand(weapon);

		Solution suggestion = new Solution(new Card(CardType.ROOM, "Kitchen"), person, weapon);

		Set<Card> expectedCards = new HashSet<>();
		expectedCards.add(person);
		expectedCards.add(weapon);

		Set<Card> returnedCards = new HashSet<>();

		// Repeat to check that both matching cards can be selected.
		for (int i = 0; i < 100; i++) {
			Card returned = player.disproveSuggestion(suggestion);

			assertNotNull(returned);
			assertTrue(expectedCards.contains(returned));

			returnedCards.add(returned);
		}

		assertEquals(expectedCards, returnedCards);
	}

	// Confirm that player returns null when none of their cards match.
	@Test
	void testDisproveSuggestionNoMatchingCards() {
		Player player = new HumanPlayer("Test Player", Color.PINK, board.getCell(12, 6));

		player.updateHand(new Card(CardType.WEAPON, "Knife"));

		Solution suggestion = new Solution(new Card(CardType.ROOM, "Kitchen"),
				new Card(CardType.PERSON, "Miss Scarlett"), new Card(CardType.WEAPON, "Gun"));

		assertNull(player.disproveSuggestion(suggestion));
	}

	// Confirm that handleSuggestion returns null when nobody can disprove.
	@Test
	void testHandleSuggestionNoPlayerCanDisprove() {
		Solution suggestion = new Solution(roomCard, personCard, weaponCard);

		assertNull(board.handleSuggestion(0, suggestion));
	}

	// Confirm that suggesting player is not asked to disprove their own suggestion.
	@Test
	void testHandleSuggestionOnlySuggestingPlayerCanDisprove() {
		List<Player> players = new ArrayList<>(board.getPlayers());

		players.get(0).updateHand(roomCard);

		Solution suggestion = new Solution(roomCard, personCard, weaponCard);

		assertNull(board.handleSuggestion(0, suggestion));
	}

	// Confirm that human player can disprove a suggestion.
	@Test
	void testHandleSuggestionHumanCanDisprove() {
		List<Player> players = new ArrayList<>(board.getPlayers());

		int humanIndex = -1;

		// Find the human player's position in the player list.
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) instanceof HumanPlayer) {
				humanIndex = i;
				break;
			}
		}

		assertTrue(humanIndex >= 0, "A human player should be in the player list.");

		Player human = players.get(humanIndex);
		human.updateHand(roomCard);

		// The player before the human suggests, so the human is checked first.
		int suggestingPlayerIndex = (humanIndex - 1 + players.size()) % players.size();

		Solution suggestion = new Solution(roomCard, personCard, weaponCard);

		assertEquals(roomCard, board.handleSuggestion(suggestingPlayerIndex, suggestion));
	}

	// Confirm that players are queried in turn order and the first matching player
	// returns a card.
	@Test
	void testHandleSuggestionPlayerOrder() {
		List<Player> players = new ArrayList<>(board.getPlayers());

		Card firstPlayerCard = new Card(CardType.WEAPON, "Gun");
		Card secondPlayerCard = new Card(CardType.PERSON, "Professor Plum");

		players.get(1).updateHand(firstPlayerCard);
		players.get(2).updateHand(secondPlayerCard);

		Solution suggestion = new Solution(roomCard, personCard, weaponCard);

		// Player 1 is checked before player 2.
		assertEquals(firstPlayerCard, board.handleSuggestion(0, suggestion));
	}

	// Confirm that handleSuggestion skips players who cannot disprove.
	@Test
	void testHandleSuggestionSkipsPlayersWithoutMatchingCards() {
		List<Player> players = new ArrayList<>(board.getPlayers());

		// Player 1 has no matching cards.
		// Player 2 has a matching person card.
		players.get(2).updateHand(personCard);

		Solution suggestion = new Solution(roomCard, personCard, weaponCard);

		// Player 1 is checked first, but player 2 should be able to disprove.
		assertEquals(personCard, board.handleSuggestion(0, suggestion));
	}

}

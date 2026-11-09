
/*
 * Class: ComputerAITest
 *
 * Details:
 * Tests computer player suggestions and movement target selection.
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
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

class ComputerAITest {

	Board board;

	@BeforeEach
	void initialization() {
		// Board is a singleton, so get the only instance.
		board = Board.getInstance();
		// Use our configuration files.
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize the board and players.
		board.initialize();

		assertEquals(6, board.getPlayers().size());
	}

	// Confirm that comp suggestion uses the room where comp is located.
	@Test
	void testCreateSuggestionRoom() {
		ComputerPlayer computer = createComputerInKitchen();
		Solution suggestion = computer.createSuggestion();

		Card expectedRoom = new Card(CardType.ROOM, "Kitchen");

		assertEquals(expectedRoom, suggestion.getRoom());
	}

	// Confirm that comp selects the only unseen person and weapon.
	@Test
	void testCreateSuggestionOnlyUnseenCards() {
		ComputerPlayer computer = createComputerInKitchen();

		List<Card> people = getCardsOfType(CardType.PERSON);
		List<Card> weapons = getCardsOfType(CardType.WEAPON);

		Card expectedPerson = people.get(0);
		Card expectedWeapon = weapons.get(0);

		Card personInHand = people.get(1);
		Card weaponInHand = weapons.get(1);

		// Mark every other person as seen except the selected person and card in hand.
		for (Card card : people) {
			if (!card.equals(expectedPerson) && !card.equals(personInHand)) {
				computer.updateSeen(card);
			}
		}

		// Mark every other weapon as seen except the selected weapon and card in hand.
		for (Card card : weapons) {
			if (!card.equals(expectedWeapon) && !card.equals(weaponInHand)) {
				computer.updateSeen(card);
			}
		}

		// These cards are in the computer's hand and should not be suggested.
		computer.updateHand(personInHand);
		computer.updateHand(weaponInHand);

		Solution suggestion = computer.createSuggestion();

		assertEquals(expectedPerson, suggestion.getPerson());
		assertEquals(expectedWeapon, suggestion.getWeapon());
		assertEquals(new Card(CardType.ROOM, "Kitchen"), suggestion.getRoom());
	}

	// Confirm that comp randomly selects from multiple unseen weapons.
	@Test
	void testCreateSuggestionMultipleUnseenWeapons() {
		ComputerPlayer computer = createComputerInKitchen();

		List<Card> weapons = getCardsOfType(CardType.WEAPON);

		Card weapon1 = weapons.get(0);
		Card weapon2 = weapons.get(1);

		// Mark every weapon except these two as seen.
		for (Card card : weapons) {
			if (!card.equals(weapon1) && !card.equals(weapon2)) {
				computer.updateSeen(card);
			}
		}

		Set<Card> expectedWeapons = new HashSet<>();
		expectedWeapons.add(weapon1);
		expectedWeapons.add(weapon2);

		Set<Card> returnedWeapons = new HashSet<>();

		// Repeat to check that both unseen weapons can be selected.
		for (int i = 0; i < 100; i++) {
			Solution suggestion = computer.createSuggestion();

			assertNotNull(suggestion.getWeapon());
			assertTrue(expectedWeapons.contains(suggestion.getWeapon()));

			returnedWeapons.add(suggestion.getWeapon());
		}

		assertEquals(expectedWeapons, returnedWeapons);
	}

	// Confirm that comp randomly selects from multiple unseen people.
	@Test
	void testCreateSuggestionMultipleUnseenPeople() {
		ComputerPlayer computer = createComputerInKitchen();

		List<Card> people = getCardsOfType(CardType.PERSON);

		Card person1 = people.get(0);
		Card person2 = people.get(1);

		// Mark every person except these two as seen.
		for (Card card : people) {
			if (!card.equals(person1) && !card.equals(person2)) {
				computer.updateSeen(card);
			}
		}

		Set<Card> expectedPeople = new HashSet<>();
		expectedPeople.add(person1);
		expectedPeople.add(person2);

		Set<Card> returnedPeople = new HashSet<>();

		// Repeat to check that both unseen people can be selected.
		for (int i = 0; i < 100; i++) {
			Solution suggestion = computer.createSuggestion();

			assertNotNull(suggestion.getPerson());
			assertTrue(expectedPeople.contains(suggestion.getPerson()));

			returnedPeople.add(suggestion.getPerson());
		}

		assertEquals(expectedPeople, returnedPeople);
	}

	// Confirm that comp selects randomly from available targets when no rooms are
	// available.
	@Test
	void testSelectTargetNoRooms() {
		ComputerPlayer computer = createComputerInKitchen();

		List<BoardCell> walkways = findTwoWalkways();

		Set<BoardCell> targets = new HashSet<>();
		targets.add(walkways.get(0));
		targets.add(walkways.get(1));

		Set<BoardCell> selectedTargets = new HashSet<>();

		// Repeat to check that both available targets can be selected.
		for (int i = 0; i < 100; i++) {
			BoardCell selected = computer.selectTarget(targets);

			assertNotNull(selected);
			assertTrue(targets.contains(selected));

			selectedTargets.add(selected);
		}

		assertEquals(targets, selectedTargets);
	}

	// Confirm that comp selects an unseen room when one is available.
	@Test
	void testSelectTargetUnseenRoom() {
		ComputerPlayer computer = createComputerInKitchen();

		BoardCell kitchen = board.getRoom('K').getCenterCell();
		BoardCell walkway = findTwoWalkways().get(0);

		Set<BoardCell> targets = new HashSet<>();
		targets.add(kitchen);
		targets.add(walkway);

		// The computer has not seen the Kitchen card.
		assertEquals(kitchen, computer.selectTarget(targets));
	}

	// Confirm that comp selects randomly from available targets when the room has
	// been seen.
	@Test
	void testSelectTargetSeenRoom() {
		ComputerPlayer computer = createComputerInKitchen();

		BoardCell kitchen = board.getRoom('K').getCenterCell();
		BoardCell walkway = findTwoWalkways().get(0);

		// Mark the Kitchen card as seen.
		computer.updateSeen(new Card(CardType.ROOM, "Kitchen"));

		Set<BoardCell> targets = new HashSet<>();
		targets.add(kitchen);
		targets.add(walkway);

		Set<BoardCell> selectedTargets = new HashSet<>();

		// Both the room and walkway should be possible selections.
		for (int i = 0; i < 100; i++) {
			BoardCell selected = computer.selectTarget(targets);

			assertNotNull(selected);
			assertTrue(targets.contains(selected));

			selectedTargets.add(selected);
		}

		assertEquals(targets, selectedTargets);
	}

	// Confirm that comp randomly selects from multiple unseen rooms.
	@Test
	void testSelectTargetMultipleUnseenRooms() {
		ComputerPlayer computer = createComputerInKitchen();

		BoardCell kitchen = board.getRoom('K').getCenterCell();
		BoardCell study = board.getRoom('S').getCenterCell();

		Set<BoardCell> targets = new HashSet<>();
		targets.add(kitchen);
		targets.add(study);

		Set<BoardCell> selectedTargets = new HashSet<>();

		// Both rooms are unseen, so either should be a valid selection.
		for (int i = 0; i < 100; i++) {
			BoardCell selected = computer.selectTarget(targets);

			assertNotNull(selected);
			assertTrue(targets.contains(selected));

			selectedTargets.add(selected);
		}

		assertEquals(targets, selectedTargets);
	}

	// Create a computer player whose current location is in the Kitchen.
	private ComputerPlayer createComputerInKitchen() {
		BoardCell kitchen = board.getRoom('K').getCenterCell();

		return new ComputerPlayer("Test Computer", Color.YELLOW, kitchen);
	}

	// Return all cards of a particular type from the complete deck.
	private List<Card> getCardsOfType(CardType type) {
		List<Card> cards = new ArrayList<>();

		for (Card card : board.getDeck()) {
			if (card.getType() == type) {
				cards.add(card);
			}
		}

		return cards;
	}

	// Find two walkway cells for target selection tests.
	private List<BoardCell> findTwoWalkways() {
		List<BoardCell> walkways = new ArrayList<>();

		for (int row = 0; row < board.getNumRows(); row++) {
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);

				if (cell.getInitial() == 'W') {
					walkways.add(cell);
				}

				if (walkways.size() == 2) {
					return walkways;
				}
			}
		}

		assertTrue(walkways.size() >= 2, "The board must contain at least two walkway cells for these tests.");

		return walkways;
	}

}

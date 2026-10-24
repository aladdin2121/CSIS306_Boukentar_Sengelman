package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileInitTests {

	private static Board board;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();

		board.setConfigFiles(
				"ClueLayout.csv",
				"ClueSetup.txt"
				);

		board.initialize();
	}

	@Test
	public void testSingleton() {
		Board anotherBoard = Board.getInstance();

		assertSame(board, anotherBoard);
	}

	@Test
	public void testRoomNames() {
		assertEquals("Conservatory", board.getRoom('C').getName());
		assertEquals("Kitchen", board.getRoom('K').getName());
		assertEquals("Ballroom", board.getRoom('B').getName());
		assertEquals("Billiard Room", board.getRoom('R').getName());
		assertEquals("Library", board.getRoom('L').getName());
		assertEquals("Study", board.getRoom('S').getName());
		assertEquals("Dining Room", board.getRoom('D').getName());
		assertEquals("Lounge", board.getRoom('O').getName());
		assertEquals("Hall", board.getRoom('H').getName());
		assertEquals("Unused", board.getRoom('X').getName());
		assertEquals("Walkway", board.getRoom('W').getName());
	}

	@Test
	public void testBoardDimensions() {
		assertEquals(24, board.getNumRows());
		assertEquals(24, board.getNumColumns());
	}

	@Test
	public void testDoorDirections() {
		BoardCell cell = board.getCell(3, 5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());

		cell = board.getCell(5, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());

		cell = board.getCell(16, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());

		cell = board.getCell(21, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());

		cell = board.getCell(9, 5);
		assertFalse(cell.isDoorway());
	}

	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;

		for (int row = 0; row < board.getNumRows(); row++) {
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);

				if (cell.isDoorway()) {
					numDoors++;
				}
			}
		}

		assertEquals(26, numDoors);
	}

	@Test
	public void testRoomCentersAndLabels() {
		// Conservatory
		assertSame(
				board.getCell(1, 2),
				board.getRoom('C').getCenterCell()
				);

		assertSame(
				board.getCell(2, 2),
				board.getRoom('C').getLabelCell()
				);

		// Kitchen
		assertSame(
				board.getCell(8, 2),
				board.getRoom('K').getCenterCell()
				);

		assertSame(
				board.getCell(9, 2),
				board.getRoom('K').getLabelCell()
				);

		// Ballroom
		assertSame(
				board.getCell(14, 3),
				board.getRoom('B').getCenterCell()
				);

		assertSame(
				board.getCell(15, 3),
				board.getRoom('B').getLabelCell()
				);
	}

	@Test
	public void testSecretPassages() {
		// Conservatory to Dining Room
		assertEquals('D', board.getCell(4, 4).getSecretPassage());

		// Dining Room to Conservatory
		assertEquals('C', board.getCell(20, 15).getSecretPassage());

		// Lounge to Library
		assertEquals('L', board.getCell(21, 0).getSecretPassage());
	}
}
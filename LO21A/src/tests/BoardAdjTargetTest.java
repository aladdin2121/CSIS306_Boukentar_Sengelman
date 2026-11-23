
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {

    private static Board board;

    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    // Test room center adjacencies.
    @Test
    public void testAdjacenciesRooms() {
        Set<BoardCell> testList = board.getAdjList(1, 2);

        // Conservatory has two doors and two secret passages.
        assertEquals(4, testList.size());
        assertTrue(testList.contains(board.getCell(3, 5)));
        assertTrue(testList.contains(board.getCell(5, 3)));
        assertTrue(testList.contains(board.getCell(21, 19)));
        assertTrue(testList.contains(board.getCell(2, 19)));

        // Ballroom has two doors.
        testList = board.getAdjList(14, 3);
        assertEquals(2, testList.size());
        assertTrue(testList.contains(board.getCell(12, 3)));
        assertTrue(testList.contains(board.getCell(15, 6)));

        // Dining Room has one door and two secret passages.
        testList = board.getAdjList(21, 19);
        assertEquals(3, testList.size());
        assertTrue(testList.contains(board.getCell(19, 17)));
        assertTrue(testList.contains(board.getCell(1, 2)));
        assertTrue(testList.contains(board.getCell(2, 19)));

        // Regular room cells should not have adjacencies.
        assertTrue(board.getAdjList(0, 0).isEmpty());
    }

    // Test that doorways connect to rooms and neighboring walkways.
    @Test
    public void testAdjacencyDoor() {
        Set<BoardCell> testList = board.getAdjList(3, 5);

        assertEquals(4, testList.size());
        assertTrue(testList.contains(board.getCell(1, 2)));
        assertTrue(testList.contains(board.getCell(2, 5)));
        assertTrue(testList.contains(board.getCell(3, 6)));
        assertTrue(testList.contains(board.getCell(4, 5)));

        testList = board.getAdjList(9, 4);

        assertEquals(4, testList.size());
        assertTrue(testList.contains(board.getCell(8, 2)));
        assertTrue(testList.contains(board.getCell(8, 4)));
        assertTrue(testList.contains(board.getCell(9, 5)));
        assertTrue(testList.contains(board.getCell(10, 4)));

        testList = board.getAdjList(16, 6);

        assertEquals(3, testList.size());
        assertTrue(testList.contains(board.getCell(15, 12)));
        assertTrue(testList.contains(board.getCell(15, 6)));
        assertTrue(testList.contains(board.getCell(17, 6)));
    }

    // Test different walkway situations.
    @Test
    public void testAdjacencyWalkways() {
        // Bottom edge of the board.
        Set<BoardCell> testList = board.getAdjList(23, 13);

        assertEquals(1, testList.size());
        assertTrue(testList.contains(board.getCell(22, 13)));

        // Walkway next to a room cell.
        testList = board.getAdjList(4, 5);

        assertEquals(3, testList.size());
        assertTrue(testList.contains(board.getCell(3, 5)));
        assertTrue(testList.contains(board.getCell(5, 5)));
        assertTrue(testList.contains(board.getCell(4, 6)));

        // Walkway with four neighboring walkways.
        testList = board.getAdjList(11, 5);

        assertEquals(4, testList.size());
        assertTrue(testList.contains(board.getCell(10, 5)));
        assertTrue(testList.contains(board.getCell(11, 4)));
        assertTrue(testList.contains(board.getCell(11, 6)));
        assertTrue(testList.contains(board.getCell(12, 5)));
    }

    // Test targets when starting in the Dining Room.
    @Test
    public void testTargetsInDiningRoom() {
        board.calcTargets(board.getCell(21, 19), 1);
        Set<BoardCell> targets = board.getTargets();

        assertEquals(3, targets.size());
        assertTrue(targets.contains(board.getCell(1, 2)));
        assertTrue(targets.contains(board.getCell(2, 19)));
        assertTrue(targets.contains(board.getCell(19, 17)));

        board.calcTargets(board.getCell(21, 19), 3);
        targets = board.getTargets();

        assertEquals(5, targets.size());
        assertTrue(targets.contains(board.getCell(13, 19)));
        assertTrue(targets.contains(board.getCell(19, 15)));

        board.calcTargets(board.getCell(21, 19), 4);
        targets = board.getTargets();

        assertEquals(5, targets.size());
        assertTrue(targets.contains(board.getCell(19, 14)));
        assertTrue(targets.contains(board.getCell(19, 20)));
    }

    // Test targets when starting in the Kitchen.
    @Test
    public void testTargetsInKitchen() {
        board.calcTargets(board.getCell(8, 2), 1);
        Set<BoardCell> targets = board.getTargets();

        assertEquals(2, targets.size());
        assertTrue(targets.contains(board.getCell(9, 4)));
        assertTrue(targets.contains(board.getCell(11, 2)));

        board.calcTargets(board.getCell(8, 2), 3);
        targets = board.getTargets();

        assertEquals(7, targets.size());
        assertTrue(targets.contains(board.getCell(7, 4)));
        assertTrue(targets.contains(board.getCell(11, 4)));
        assertTrue(targets.contains(board.getCell(12, 3)));

        board.calcTargets(board.getCell(8, 2), 4);
        targets = board.getTargets();

        assertEquals(11, targets.size());
        assertTrue(targets.contains(board.getCell(8, 6)));
        assertTrue(targets.contains(board.getCell(14, 3)));
    }

    // Test targets when starting at a doorway.
    @Test
    public void testTargetsAtDoor() {
        board.calcTargets(board.getCell(3, 5), 1);
        Set<BoardCell> targets = board.getTargets();

        assertEquals(4, targets.size());
        assertTrue(targets.contains(board.getCell(1, 2)));
        assertTrue(targets.contains(board.getCell(2, 5)));
        assertTrue(targets.contains(board.getCell(3, 6)));

        board.calcTargets(board.getCell(3, 5), 3);
        targets = board.getTargets();

        assertEquals(7, targets.size());
        assertTrue(targets.contains(board.getCell(1, 2)));
        assertTrue(targets.contains(board.getCell(1, 6)));
        assertTrue(targets.contains(board.getCell(5, 4)));

        board.calcTargets(board.getCell(3, 5), 4);
        targets = board.getTargets();

        assertEquals(8, targets.size());
        assertTrue(targets.contains(board.getCell(0, 6)));
        assertTrue(targets.contains(board.getCell(2, 6)));
    }

    // Test targets when starting in the first walkway.
    @Test
    public void testTargetsInWalkway1() {
        board.calcTargets(board.getCell(11, 5), 1);
        Set<BoardCell> targets = board.getTargets();

        assertEquals(4, targets.size());
        assertTrue(targets.contains(board.getCell(10, 5)));
        assertTrue(targets.contains(board.getCell(11, 4)));

        board.calcTargets(board.getCell(11, 5), 3);
        targets = board.getTargets();

        assertEquals(10, targets.size());
        assertTrue(targets.contains(board.getCell(9, 4)));
        assertTrue(targets.contains(board.getCell(13, 6)));

        board.calcTargets(board.getCell(11, 5), 4);
        targets = board.getTargets();

        assertEquals(14, targets.size());
        assertTrue(targets.contains(board.getCell(8, 2)));
        assertTrue(targets.contains(board.getCell(14, 3)));
    }

    // Test targets when starting in a second walkway.
    @Test
    public void testTargetsInWalkway2() {
        board.calcTargets(board.getCell(12, 6), 1);
        Set<BoardCell> targets = board.getTargets();

        assertEquals(4, targets.size());
        assertTrue(targets.contains(board.getCell(11, 6)));
        assertTrue(targets.contains(board.getCell(13, 6)));

        board.calcTargets(board.getCell(12, 6), 3);
        targets = board.getTargets();

        assertEquals(9, targets.size());
        assertTrue(targets.contains(board.getCell(9, 6)));
        assertTrue(targets.contains(board.getCell(15, 6)));

        board.calcTargets(board.getCell(12, 6), 4);
        targets = board.getTargets();

        assertEquals(11, targets.size());
        assertTrue(targets.contains(board.getCell(8, 6)));
        assertTrue(targets.contains(board.getCell(14, 3)));
    }

    // Test occupied locations.
    @Test
    public void testTargetsOccupied() {
        try {
            // An occupied walkway should block movement.
            board.getCell(14, 6).setOccupied(true);

            board.calcTargets(board.getCell(13, 6), 4);
            Set<BoardCell> targets = board.getTargets();

            assertEquals(7, targets.size());
            assertFalse(targets.contains(board.getCell(14, 6)));
            assertFalse(targets.contains(board.getCell(14, 3)));

        } finally {
            board.getCell(14, 6).setOccupied(false);
        }

        try {
            // An occupied room can still be entered.
            board.getCell(8, 2).setOccupied(true);

            board.calcTargets(board.getCell(11, 1), 2);
            Set<BoardCell> targets = board.getTargets();

            assertEquals(4, targets.size());
            assertTrue(targets.contains(board.getCell(8, 2)));

        } finally {
            board.getCell(8, 2).setOccupied(false);
        }

        try {
            // An occupied doorway should block leaving a room.
            board.getCell(12, 3).setOccupied(true);

            board.calcTargets(board.getCell(14, 3), 1);
            Set<BoardCell> targets = board.getTargets();

            assertEquals(1, targets.size());
            assertTrue(targets.contains(board.getCell(15, 6)));
            assertFalse(targets.contains(board.getCell(12, 3)));

        } finally {
            board.getCell(12, 3).setOccupied(false);
        }
    }
}

package tests;
import experiment.TestBoard;
import experiment.TestBoardCell;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	//adjacency tests
	
	@Test
	public void testAdjacency() {
		//top left
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		
		//bot right
		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		
		//right edge
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		
		//left edge
		cell = board.getCell(2, 0);
		testList = cell.getAdjList();
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		
		//center
		cell = board.getCell(2, 2);
		testList = cell.getAdjList();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
	}
	
	
	//target creation tests
	
	@Test
	public void testTargetsNormal() {
		//0,0 roll 3
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		
		//0,0 roll 1
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		
		//0,0 roll 6
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		
		//2,2 roll 6
		cell = board.getCell(2, 2);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		
		//2,2 roll 2
		board.calcTargets(cell, 2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		
		//2,0 roll 3
		cell = board.getCell(2, 0);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		
		//2,3 roll 6
		cell = board.getCell(2, 3);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
	}
	
	@Test
	public void testTargetsRoom() {
		//0,0 roll 1
		board.getCell(0, 1).setRoom(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 1)));
		
		//0,0 roll 6
		board.getCell(3, 3).setRoom(true);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	@Test
	public void testTargetsOccupied() {
		//0,0 roll 1
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertFalse(targets.contains(board.getCell(0, 1)));
		
		//0,0 roll 6
		board.getCell(3, 3).setOccupied(true);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertFalse(targets.contains(board.getCell(3, 3)));
		
		
	}
	
	@Test
	public void testTargetsMixed() {
		//0,0 roll 1
		board.getCell(1, 0).setRoom(true);
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> targets = board.getTargets();
		board.calcTargets(cell, 1);
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertFalse(targets.contains(board.getCell(0, 1)));
		
		//reset rooms
		board.getCell(1, 0).setRoom(false);
		board.getCell(0, 1).setOccupied(false);
		
		//0,0 roll 6
		board.getCell(1, 1).setRoom(true);
		board.getCell(3, 3).setOccupied(true);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		assertFalse(targets.contains(board.getCell(3, 3)));
		assert(targets.contains(board.getCell(1, 1)));
		
		
	}
}

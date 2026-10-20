package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	final static int COLS = 4;
	final static int ROWS = 4;
	
	private TestBoardCell[][] cellGrid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	public TestBoard() {
		targets = new HashSet<>();
		visited = new HashSet<>();
		
		cellGrid = new TestBoardCell[4][4];
		
		//loops through and creates all cells
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cellGrid[i][j] = new TestBoardCell(i, j);
			}
		}
		
		//calculates at creation of board and stores to each cell
		calcAdjacencies();
	}
	
	//calculates all adjacent cells in the grid
	private void calcAdjacencies() {
		TestBoardCell cell;
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cell = cellGrid[i][j];
				
				//checks locations of cell and adds adjacent cells only if they exist
				if (i > 0) cell.addAdjacency(cellGrid[i - 1][j]);
				if (i < ROWS - 1) cell.addAdjacency(cellGrid[i + 1][j]);
				if (j > 0) cell.addAdjacency(cellGrid[i][j - 1]);
				if (j < COLS - 1) cell.addAdjacency(cellGrid[i][j + 1]);
				}
			}
		}

	public void calcTargets(TestBoardCell start, int length) {
		//clear sets so that last call of this function does not affect curr call
		targets.clear();
		visited.clear();
		
		//ends fcn if no start or impossible lengths
		if (start == null || length <= 0) {
			return;
		}
		
		visited.add(start);
		
		findAllTargets(start, length, visited);
	}
	
	//recursive fcn to calc all the targets that can be traveled to
	private void findAllTargets(TestBoardCell current, int stepsRemaining, Set<TestBoardCell> visited) {
		for (TestBoardCell next : current.getAdjList()) {
			if (visited.contains(next) || next.getOccupied()) {
				continue;
			}
			visited.add(next);
			if (next.isRoom() || stepsRemaining == 1) {
				targets.add(next);
			} else {
				findAllTargets(next, stepsRemaining - 1, visited);
			}
			visited.remove(next);
		}
	}
	public TestBoardCell getCell(int row, int col) {
		return cellGrid[row][col];
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}

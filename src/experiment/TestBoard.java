package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] cellGrid;
	private Set<TestBoardCell> targets = new HashSet<>();
	
	public TestBoard() {
		cellGrid = new TestBoardCell[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cellGrid[i][j] = new TestBoardCell(i, j);
			}
		}
	}
	
	public void calcTargets(TestBoardCell start, int length) {
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		return cellGrid[row][col];
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}

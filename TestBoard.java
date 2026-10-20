package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	final static int ROW = 4;
	final static int COL = 4;
	private TestBoardCell[][] cellGrid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	
	
	public TestBoard() {
		
		targets = new HashSet<>();
		visited = new HashSet<>();
		
		cellGrid = new TestBoardCell[ROW][COL];
		
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				cellGrid[i][j] = new TestBoardCell(i, j);
			}
		}
		
		for (int i = 0; i<ROW;i++) {
			for (int j=0;j<COL;j++) {
				//to keep using the same board cells we use cellGrid[i][j]
				TestBoardCell cell = cellGrid [i][j];
				if(i>0) {cell.addAdjacency(cellGrid[i-1][j]);}
				if(i<ROW-1) {cell.addAdjacency(cellGrid[i+1][j]);}
				if(j>0) {cell.addAdjacency(cellGrid[i][j-1]);}
				if(j<COL-1) {cell.addAdjacency(cellGrid[i][j+1]);}
			}
		}
	}
	
	public void calcTargets(TestBoardCell start, int length) {
		
		if (length == 0 ) {
			targets.add(start);
			return;
		}
		
		visited.add(start);
				
		for (TestBoardCell adjCell : start.getAdjList()) {
			if (!visited.contains(adjCell)) {
				calcTargets(adjCell, length-1);
			}
		}
		visited.remove(start);
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		return cellGrid[row][col];
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}

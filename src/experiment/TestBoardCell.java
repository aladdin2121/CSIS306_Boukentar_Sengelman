package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int col, row;
	private Set<TestBoardCell> adjList = new HashSet<>();
	private boolean isRoom, isOccupied;
	
	
	public TestBoardCell(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		this.adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setRoom(boolean i) {
		this.isRoom = i;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean i) {
		isOccupied = i;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}

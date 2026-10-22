package clueGame;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int col, row;
	private Set<BoardCell> adjList = new HashSet<>();
	private boolean isRoom, isOccupied;
	
	
	public BoardCell(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	
	
	
	public void addAdjacency(BoardCell cell) {
		this.adjList.add(cell);
		
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public void setRoom(boolean i) {
		this.isRoom = i;
		
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean i) {
		this.isOccupied = i;
		
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}

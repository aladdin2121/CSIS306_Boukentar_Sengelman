package clueGame;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int col, row;
	private Set<BoardCell> adjList = new HashSet<>();
	private boolean isRoom, isOccupied, isDoorway,roomCenter, roomLabel;
	DoorDirection direction;
	
	
	public BoardCell(int row, int col) {
		this.col = col;
		this.row = row;
		isRoom = false;
		isOccupied =false; 
		isDoorway = false;
		roomCenter =false; 
		roomLabel = false;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
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
	public void setIsDoorWay(boolean itIs) {
		this.isDoorway = itIs;
	}
	public boolean isDoorway() {
		return this.isDoorway;
	}
	public void setDirection(DoorDirection direct) {
		this.direction = direct;
	}
	public DoorDirection getDoorDirection() {
		return this.direction;
	}
	public void setRoomCenter(boolean center) {
		this.roomCenter = center; 
	}
	public boolean isRoomCenter() {
		return this.roomCenter;
	}
	public void setRoomLabel(boolean label) {
		this.roomLabel= label;
	}
	public boolean isLabel() {
		return this.roomLabel;
	}
}

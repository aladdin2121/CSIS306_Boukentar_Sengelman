package clueGame;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int col, row;
	private char initial, secretPass;
	private Set<BoardCell> adjList = new HashSet<>();
	private boolean isRoom, isOccupied, isDoorway, roomLabel, roomCenter;
	private DoorDirection direction = DoorDirection.NONE;

	public BoardCell(int row, int col) {
		this.col = col;
		this.row = row;
	}

	public void addAdj(BoardCell cell) {
		this.adjList.add(cell);

	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public void setRoom(boolean room) {
		this.isRoom = room;
	}

	public boolean isRoom() {
		return isRoom;
	}

	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}

	public boolean getOccupied() {
		return isOccupied;
	}

	public boolean isDoorway() {
		return isDoorway;
	}

	public void setIsDoorWay(boolean doorway) {
		this.isDoorway = doorway;
	}

	public void setDirection(DoorDirection direct) {
		this.direction = direct;
	}

	public DoorDirection getDoorDirection() {
		return this.direction;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public void setLabel(boolean label) {
		roomLabel = label;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public char getSecretPassage() {
		return secretPass;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPass = secretPassage;
	}
}

/*
 * Class: BoardCell
 * 
 * Details:
 * it stores info of boardcell such as the adjacency list and it's location and 
 * what type of cell it is
 * 
 * Authors: Alaa Eddine Boukentar Assefar
 * 			Sophie Sengelmann-Lipps
 * 
 * Start Date: 10/17/2026
 * 
 * Collaborators: None
 * 
 * Sources: None
 */


import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int col, row;
	private char initial, secretPass;
	private Set<BoardCell> adjList = new HashSet<>();
	private boolean isRoom, isOccupied = false, isDoorway = false, roomLabel = false, roomCenter = false,
			isSecretPass = false, isEmpty = false;
	private DoorDirection direction = DoorDirection.NONE;

	public BoardCell(int row, int col) {
		this.col = col;
		this.row = row;
	}

	public void addAdj(BoardCell cell) {
		this.adjList.add(cell);

	}

	public Set<BoardCell> getAdjList() {
		return this.adjList;
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public char getInitial() {
		return this.initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public void setRoom(boolean room) {
		this.isRoom = room;
	}

	public boolean isRoom() {
		return this.isRoom;
	}

	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}

	public boolean getOccupied() {
		return this.isOccupied;
	}

	public boolean isDoorway() {
		return this.isDoorway;
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
		return this.roomLabel;
	}

	public void setLabel(boolean label) {
		this.roomLabel = label;
	}

	public boolean isRoomCenter() {
		return this.roomCenter;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public char getSecretPassage() {
		return this.secretPass;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPass = secretPassage;
		this.isSecretPass = true;
	}

	public boolean isSecretPass() {
		return this.isSecretPass;
	}

	public void setEmpty(boolean empty) {
		this.isEmpty = empty;
	}

	public boolean isEmpty() {
		return this.isEmpty;
	}
}

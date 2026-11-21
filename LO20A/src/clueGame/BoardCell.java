
/*
 * Class: BoardCell
 *
 * Details:
 * it stores info of boardcell such as the adjacency list and it's location and
 * what type of cell it is
 *
 * Authors: Alaa Eddine Boukentar Assefar
 *          Sophie Sengelman-Lipps
 *
 * Start Date: 10/17/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */

package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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

	// Draw this cell on the game board.
	public void draw(Graphics2D g, int x, int y, int cellSize) {

		// Draw the cell based on its type.
		if (isEmpty()) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, cellSize, cellSize);
		}

		else if (isRoom()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, cellSize, cellSize);

			// Draw an indication of a secret passage.
			if (isSecretPass()) {
				g.setColor(Color.BLUE);

				int markerSize = Math.max(5, cellSize / 3);

				g.drawString("S", x + cellSize / 3, y + (2 * cellSize) / 3);
			}
		}

		else {
			// Draw walkways and doorways.
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, cellSize, cellSize);

			g.setColor(Color.BLACK);
			g.drawRect(x, y, cellSize, cellSize);
		}

		// Draw the doorway indicator.
		if (isDoorway()) {
			drawDoor(g, x, y, cellSize);
		}
	}

	// Draw a doorway marker on the side facing the room.
	private void drawDoor(Graphics2D g, int x, int y, int cellSize) {

		int doorWidth = Math.max(3, cellSize / 5);

		g.setColor(Color.BLUE);
		g.setStroke(new BasicStroke(3));

		switch (getDoorDirection()) {

		case UP:
			g.drawLine(x + cellSize / 4, y, x + (3 * cellSize) / 4, y);
			break;

		case DOWN:
			g.drawLine(x + cellSize / 4, y + cellSize, x + (3 * cellSize) / 4, y + cellSize);
			break;

		case LEFT:
			g.drawLine(x, y + cellSize / 4, x, y + (3 * cellSize) / 4);
			break;

		case RIGHT:
			g.drawLine(x + cellSize, y + cellSize / 4, x + cellSize, y + (3 * cellSize) / 4);
			break;

		default:
			break;
		}

		g.setStroke(new BasicStroke(1));
	}

}

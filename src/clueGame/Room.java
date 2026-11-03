/*
 * Class: Room
 * 
 * Details:
 * keeps trcuk of rooms and their center cell and label cell
 * 
 * Authors: Alaa Eddine Boukentar Assefar
 * 			
 * 
 * Start Date: 10/25/2026
 * 
 * Collaborators: None
 * 
 * Sources: None
 */
package clueGame;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;

	public Room (String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
}

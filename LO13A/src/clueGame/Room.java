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
	public void setLabelCell(BoardCell target) {
		this.labelCell = target;
	}
	public void setCenterCell(BoardCell target) {
		this.centerCell=target;
	}
	public BoardCell getLabelCell() {
		return this.labelCell;
	}
	public BoardCell getCenterCell(){
		return this.centerCell;
	}

}

/*
 * Class: Setting
 * 
 * Details:
 * keeps track of the enum DoorDirection
 * 
 * Authors: Alaa Eddine Boukentar Assefar
 * 
 * Start Date: 10/22/2026
 * 
 * Collaborators: None
 * 
 * Sources: googled enum functions
 */

package clueGame;

public enum DoorDirection { UP('^'), DOWN ('v'), LEFT ('<'), RIGHT ('>'), NONE;
	private final char sign;

	private DoorDirection (char sign) {
		this.sign = sign;
	}
	private DoorDirection() {
		this.sign = '\0';
	}

	@Override
	public String toString(){
		if (this.sign == '\0') {
			return "NONE";
		}
		return this.name();
	}



}

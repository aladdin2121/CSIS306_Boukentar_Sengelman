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

public enum DoorDirection { UP('^'), DOWN ('v'), LEFT ('<'), RIGHT ('>'), NONE(' ');
	private char sign;
	
	private DoorDirection (char sign) {
		this.sign = sign;
	}
	@Override
	
	public String toString(){
		return this.name();
	}
	
	

}

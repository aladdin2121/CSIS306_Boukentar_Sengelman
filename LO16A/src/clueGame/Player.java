/*
 * Class: Player
 * 
 * Details:
 * keeps track of player
 * 
 * Authors: Alaa Eddine Boukentar Assefar
 * 			Sophie Sengelmann-Lipps
 * 			
 * 
 * Start Date: 11/06/2026
 * 
 * Collaborators: None
 * 
 * Sources: None
 */

package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	private boolean isHuman;
	private BoardCell location;
	
	private int startingRow;
	private int startingCol;
	
	private Set<Card> hand;
<<<<<<< HEAD

	public Player(String name, Color color, boolean isHuman, BoardCell location) {
=======
	
	public Player(String name, Color color, boolean isHuman, int row, int col) {
>>>>>>> ea9e9dc11c4bd6ee04a4594461a1613c275b4981
		super();
		this.name = name;
		this.color = color;
		this.isHuman = isHuman;
		this.hand = new HashSet<>();
		this.startingRow = row;
		this.startingCol = col;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public boolean isHuman() {
		return isHuman;
	}

	public BoardCell getLocation() {
		return location;
	}
<<<<<<< HEAD

	public void setLocation(BoardCell location) {
		this.location = location;
	}

	public void setHand(Card card) {
=======
	public void setLocation(BoardCell cell) {
		this.location = cell;
	}
	public void setHand(Card card) {
	
>>>>>>> ea9e9dc11c4bd6ee04a4594461a1613c275b4981
		this.hand.add(card);
	}

	public Set<Card> getHand() {
		return this.hand;
	}
<<<<<<< HEAD

	public void updateHand(Card card) {
		this.hand.add(card);
	}

=======
	
	public int getStartingRow() {
		return this.startingRow;
	}
	public int getStartingCol() {
		return this.startingCol;
	}
	
	
	
>>>>>>> ea9e9dc11c4bd6ee04a4594461a1613c275b4981
}

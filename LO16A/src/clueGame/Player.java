/*
 * Class: Player
 * 
 * Details:
 * keeps track of player
 * 
 * Authors: Alaa Eddine Boukentar Assefar
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

public class Player {
	private String name;
	private Color color;
	private boolean isHuman;
	private BoardCell location;
	
	private int startingRow;
	private int startingCol;
	
	private Set<Card> hand;
	
	public Player(String name, Color color, boolean isHuman, int row, int col) {
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
	public void setLocation(BoardCell cell) {
		this.location = cell;
	}
	public void setHand(Card card) {
	
		this.hand.add(card);
	}
	public Set<Card> getHand(){
		return this.hand;
	}
	
	public int getStartingRow() {
		return this.startingRow;
	}
	public int getStartingCol() {
		return this.startingCol;
	}
	
	
	
}

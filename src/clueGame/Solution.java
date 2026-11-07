/*
 * Class: Solution
 * 
 * Details:
 * keeps truck of the solution. It uses singleton instance because we will have only one solution
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

import java.util.HashSet;
import java.util.Set;

public class Solution {
	private Set<Card> solution;
	private Card room;
	private Card person;
	private Card weapon;

	private static final Solution theInstance = new Solution();

	private Solution() {
		super();
		this.solution = new HashSet<>();

	}

	public static Solution getInstance() {
		return theInstance;
	}

	public void setSolution(Card room, Card person, Card weapon) {
		this.solution.clear();

		this.room = room;
		this.person = person;
		this.weapon = weapon;
		solution.add(this.room);
		solution.add(this.person);
		solution.add(this.weapon);

	}

	public Set<Card> getSolution() {
		return this.solution;
	}

	public Set<Card> getAnswer() {
		return this.solution;
	}

	public Card getRoom() {
		return this.room;
	}

	public Card getPerson() {
		return this.person;
	}

	public Card getWeapon() {
		return this.weapon;
	}

}

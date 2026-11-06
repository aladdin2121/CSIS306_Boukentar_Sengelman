/*
 * Class: Solution
 * 
 * Details:
 * keeps truck of the solution. It uses singleton instance because we will have only one solution
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Solution {
	private Set <Card>solution;
	private Card room;
	private Card person;
	private Card weapon;
	
	private static final Solution theInstance = new Solution();

	private Solution() {
		super();
		solution = new HashSet<>();
		
	}
	
	private Solution(Card room, Card person, Card weapon) {
		super();
		this.room = room;
		this.person = person;
		this.weapon = weapon;
		this.solution.clear();
		solution.add(this.room);
		solution.add(this.person);
		solution.add(this.weapon);
		
		
	}
	
	public static Solution getInstance() {
		return theInstance;
	}
	public Set<Card> getSolution(){
		return this.solution;
	}
	
	
	

}

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	private boolean isHuman;
	private BoardCell location;
	private Set<Card> hand;
	private Set<Card> seenCards = new HashSet<>();

	public Player(String name, Color color, boolean isHuman, BoardCell location) {
		super();
		this.name = name;
		this.color = color;
		this.isHuman = isHuman;
		this.location = location;
		this.hand = new HashSet<>();
	}

	// stub
	public Card disproveSuggestion(Solution suggestion) {
		List<Card> matchingCards = new ArrayList<>();

	    for (Card card : suggestion.getAnswer()) {

	        if (hand.contains(card)) {
	            matchingCards.add(card);
	        }
	    }

	    if (matchingCards.isEmpty()) {
	        return null;
	    }

	    int randomIndex = (int) (Math.random() * matchingCards.size());

	    return matchingCards.get(randomIndex);
	}

	// basic, incomplete implementation
	public void updateSeen(Card card) {
		seenCards.add(card);
	}
	
	public Set<Card> getSeenCards() {
	    return this.seenCards;
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

	public void setLocation(BoardCell location) {
		this.location = location;
	}

	public void setHand(Card card) {
		this.hand.add(card);
	}

	public Set<Card> getHand() {
		return this.hand;
	}

	public void updateHand(Card card) {
		this.hand.add(card);
	}
	

}

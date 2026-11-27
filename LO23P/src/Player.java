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



import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	// Check whether the player can disprove a suggestion.
	public Card disproveSuggestion(Solution suggestion) {

	    if (suggestion == null) {
	        return null;
	    }

	    List<Card> matchingCards = new ArrayList<>();

	    // Find all cards in the player's hand that match the suggestion.
	    for (Card card : hand) {
	        if (suggestion.getSolution().contains(card)) {
	            matchingCards.add(card);
	        }
	    }

	    // Return null if no cards match.
	    if (matchingCards.isEmpty()) {
	        return null;
	    }

	    // Randomly select a card if multiple cards match.
	    Collections.shuffle(matchingCards);

	    return matchingCards.get(0);
	}

	// Add a card to the player's seen cards.
	public void updateSeen(Card card) {
	    seenCards.add(card);
	}

	// Return all cards the player has seen.
	public Set<Card> getSeenCards() {
	    return seenCards;
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

	// Draw the player at their current location on the board.
	public void draw(Graphics2D g, int cellSize, int xOffset, int yOffset) {

	    if (location == null) {
	        return;
	    }

	    int diameter = cellSize * 3 / 4;

	    int x = xOffset + location.getCol() * cellSize
	            + (cellSize - diameter) / 2;

	    int y = yOffset + location.getRow() * cellSize
	            + (cellSize - diameter) / 2;

	    g.setColor(color);
	    g.fillOval(x, y, diameter, diameter);

	    // Draw a black outline around the player.
	    g.setColor(Color.BLACK);
	    g.drawOval(x, y, diameter, diameter);
	}
}

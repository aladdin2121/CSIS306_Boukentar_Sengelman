/*
 * Class: Card
 * 
 * Details:
 * keeps track of Card
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

import java.util.Objects;

public class Card {
	private String cardName;
	private CardType type;

	public Card(CardType type, String cardName) {
		super();
		this.cardName = cardName;
		this.type = type;
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Card)) {
			return false;
		}

		Card other = (Card) obj;

		return Objects.equals(this.cardName, other.cardName) && this.type == other.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.cardName, this.type);
	}

}

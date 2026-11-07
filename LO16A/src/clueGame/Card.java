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

<<<<<<< HEAD
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
=======

@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Card)) {
	        return false;
	    }
		Card card = (Card)obj;
		if (this.cardName.equalsIgnoreCase(card.getCardName()) && this.type==card.getType()) {
>>>>>>> ea9e9dc11c4bd6ee04a4594461a1613c275b4981
			return true;
		}

		if (!(obj instanceof Card)) {
			return false;
		}

		Card other = (Card) obj;

		return Objects.equals(this.cardName, other.cardName) && this.type == other.type;
	}
<<<<<<< HEAD

	@Override
	public int hashCode() {
		return Objects.hash(this.cardName, this.type);
	}

=======
@Override
public int hashCode() {
	return java.util.Objects.hash(cardName.toLowerCase(), type);
}



@Override
public String toString() {
	return "Card [cardName=" + cardName + ", type=" + type + "]";
}
	
	
>>>>>>> ea9e9dc11c4bd6ee04a4594461a1613c275b4981
}

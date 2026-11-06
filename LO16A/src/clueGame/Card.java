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

public class Card {
	private String cardName;
	private CardType type;
	
	
	
	public Card( CardType type, String cardName) {
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



	public boolean equals(Card card) {
		if (this.cardName.equals(card.getCardName()) && this.type==card.getType()) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
}

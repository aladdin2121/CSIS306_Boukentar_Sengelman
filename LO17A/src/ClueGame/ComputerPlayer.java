/*
 * Class: HumanPlayer
 *
 * Details:
 * Represents the human player.
 *
 * Authors: Alaa Eddine Boukentar Asseffar
 * 			Sophie Sengelman-Lipps
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
import java.util.List;
import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color, BoardCell location) {
		super(name, color, false, location);
	}

	
	public Solution createSuggestion() {
		Board board = Board.getInstance();

        List<Card> unseenPeople = new ArrayList<>();
        List<Card> unseenWeapons = new ArrayList<>();

        for (Card card : board.getDeck()) {

            if (getHand().contains(card) || getSeenCards().contains(card)) {
                continue;
            }

            if (card.getType() == CardType.PERSON) {
                unseenPeople.add(card);
            }

            if (card.getType() == CardType.WEAPON) {
                unseenWeapons.add(card);
            }
        }

        Card person = unseenPeople.get((int) (Math.random() * unseenPeople.size()));
        Card weapon = unseenWeapons.get((int) (Math.random() * unseenWeapons.size()));

        Room currentRoom = board.getRoom(getLocation());

        Card roomCard  =null;

        for (Card card : board.getDeck()) {
            if (card.getType() == CardType.ROOM && card.getCardName().equals(currentRoom.getName())) {
                roomCard = card;
                break;
            }
        }

        return new Solution(roomCard, person, weapon);
	}

	
	public BoardCell selectTarget(Set<BoardCell> targets) {
		Board board = Board.getInstance();

        List<BoardCell> unseenRoomTargets = new ArrayList<>();

        for (BoardCell target : targets) {

            if (!target.isRoomCenter()) {
                continue;
            }

            Room room = board.getRoom(target);

            Card roomCard = null;

            for (Card card : board.getDeck()) {
                if (card.getType() == CardType.ROOM && card.getCardName().equals(room.getName())) {
                    roomCard = card;
                    break;
                }
            }

            if (roomCard != null && !getHand().contains(roomCard) && !getSeenCards().contains(roomCard)) {

                unseenRoomTargets.add(target);
            }
        }

        if (!unseenRoomTargets.isEmpty()) {
            int randomIndex = (int) (Math.random() * unseenRoomTargets.size());
            return unseenRoomTargets.get(randomIndex);
        }

        List<BoardCell> availableTargets = new ArrayList<>(targets);

        int randomIndex = (int) (Math.random() * availableTargets.size());

        return availableTargets.get(randomIndex);
    
	}
}
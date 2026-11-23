/*
 * Class: ComputerPlayer
 *
 * Details:
 * controls the computer player's suggestions and movement targets
 *
 * Authors: Alaa Eddine Boukentar Assefar
 *          Sophie Sengelman-Lipps
 *
 * Start Date: 11/20/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name, Color color, BoardCell location) {
        super(name, color, false, location);
    }

    // Create a suggestion using the room where the computer is located.
    public Solution createSuggestion() {

        Board board = Board.getInstance();

        // Find the room where the computer is currently located.
        Room currentRoom = board.getRoom(getLocation());

        if (currentRoom == null) {
            return null;
        }

        Card roomCard = new Card(CardType.ROOM, currentRoom.getName());

        // Find an unseen person and weapon.
        Card personCard = chooseUnseenCard(CardType.PERSON);
        Card weaponCard = chooseUnseenCard(CardType.WEAPON);

        if (personCard == null || weaponCard == null) {
            return null;
        }

        return new Solution(roomCard, personCard, weaponCard);
    }

    // Randomly select a card that is not in the player's hand or seen cards.
    private Card chooseUnseenCard(CardType type) {

        List<Card> possibleCards = new ArrayList<>();

        for (Card card : Board.getInstance().getDeck()) {

            if (card.getType() == type
                    && !getHand().contains(card)
                    && !getSeenCards().contains(card)) {

                possibleCards.add(card);
            }
        }

        if (possibleCards.isEmpty()) {
            return null;
        }

        Collections.shuffle(possibleCards);

        return possibleCards.get(0);
    }

    // Select a movement target, preferring rooms the computer has not seen.
    public BoardCell selectTarget(Set<BoardCell> targets) {

        if (targets == null || targets.isEmpty()) {
            return null;
        }

        List<BoardCell> unseenRooms = new ArrayList<>();

        // Find all available rooms that the computer has not seen.
        for (BoardCell target : targets) {

            if (target.isRoom()) {

                Room room = Board.getInstance().getRoom(target);

                if (room != null) {

                    Card roomCard = new Card(CardType.ROOM, room.getName());

                    if (!getHand().contains(roomCard)
                            && !getSeenCards().contains(roomCard)) {

                        unseenRooms.add(target);
                    }
                }
            }
        }

        // Prefer an unseen room if one is available.
        if (!unseenRooms.isEmpty()) {
            Collections.shuffle(unseenRooms);
            return unseenRooms.get(0);
        }

        // Otherwise, randomly choose from all available targets.
        List<BoardCell> allTargets = new ArrayList<>(targets);

        Collections.shuffle(allTargets);

        return allTargets.get(0);
    }

}

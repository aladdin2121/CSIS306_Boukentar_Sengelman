/*
 * Class: KnownCardsPanel
 *
 * Details:
 * Displays the cards in the player's hand and cards that the
 * player has seen. Seen cards are color-coded based on the
 * player who showed the card.
 *
 * Authors: Alaa Eddine Boukentar Assefar
 *          Sophie Sengelman-Lipps
 *
 * Start Date: 11/15/2026
 *
 * Collaborators: None
 *
 * Sources: None
 */
package clueGame;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class KnownCardsPanel extends JPanel {

    // Panels for each card category.
    private JPanel peoplePanel;
    private JPanel roomsPanel;
    private JPanel weaponsPanel;

    /**
     * Constructor for the panel.
     *
     * @param handCards cards in the player's hand
     * @param seenCards cards seen and the colors of the players who showed them
     */
    public KnownCardsPanel(Collection<Card> handCards, Map<Card, Color> seenCards) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create the three card category panels.
        peoplePanel = createCategoryPanel("People", CardType.PERSON, handCards, seenCards);
        roomsPanel = createCategoryPanel("Rooms", CardType.ROOM, handCards, seenCards);
        weaponsPanel = createCategoryPanel("Weapons", CardType.WEAPON, handCards, seenCards);

        add(peoplePanel);
        add(roomsPanel);
        add(weaponsPanel);
    }

    // Create a panel for a particular card category.
    private JPanel createCategoryPanel(String title, CardType type,
            Collection<Card> handCards, Map<Card, Color> seenCards) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder(new EtchedBorder(), title));
        panel.setAlignmentX(LEFT_ALIGNMENT);

        // Display cards in the player's hand.
        panel.add(new JLabel("In Hand:"));

        List<Card> hand = getCardsOfType(handCards, type);

        if (hand.isEmpty()) {
            panel.add(createCardField("None", Color.WHITE));
        } else {
            for (Card card : hand) {
                panel.add(createCardField(card.getCardName(), Color.WHITE));
            }
        }

        panel.add(Box.createVerticalStrut(5));

        // Display cards the player has seen.
        panel.add(new JLabel("Seen:"));

        List<Card> seen = getCardsOfType(seenCards.keySet(), type);

        if (seen.isEmpty()) {
            panel.add(createCardField("None", Color.WHITE));
        } else {
            for (Card card : seen) {
                Color cardColor = seenCards.get(card);

                if (cardColor == null) {
                    cardColor = Color.LIGHT_GRAY;
                }

                panel.add(createCardField(card.getCardName(), cardColor));
            }
        }

        return panel;
    }

    // Create a non-editable text field displaying a card.
    private JTextField createCardField(String cardName, Color color) {

        JTextField field = new JTextField(cardName, 12);

        field.setEditable(false);
        field.setBackground(color);
        field.setOpaque(true);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                field.getPreferredSize().height));

        return field;
    }

    // Return cards of a particular type, sorted alphabetically.
    private List<Card> getCardsOfType(Collection<Card> cards, CardType type) {

        List<Card> matchingCards = new ArrayList<>();

        for (Card card : cards) {
            if (card.getType() == type) {
                matchingCards.add(card);
            }
        }

        matchingCards.sort(Comparator.comparing(Card::getCardName));

        return matchingCards;
    }
    public void refresh(Collection<Card> handCards, Map<Card, Color> seenCards) {
    	this.removeAll();
    	
    	peoplePanel = createCategoryPanel("People", CardType.PERSON, handCards, seenCards);
        roomsPanel = createCategoryPanel("Rooms", CardType.ROOM, handCards, seenCards);
        weaponsPanel = createCategoryPanel("Weapons", CardType.WEAPON, handCards, seenCards);

        add(peoplePanel);
        add(roomsPanel);
        add(weaponsPanel);     
        revalidate();
        repaint();
        
    	
    }

    /**
     * Main to test the panel.
     *
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Create sample cards in the player's hand.
            List<Card> handCards = new ArrayList<>();

            handCards.add(new Card(CardType.PERSON, "Professor Plum"));
            handCards.add(new Card(CardType.PERSON, "Miss Scarlett"));
            handCards.add(new Card(CardType.WEAPON, "Wrench"));

            // Create sample seen cards and their corresponding player colors.
            Map<Card, Color> seenCards = new HashMap<>();

            seenCards.put(
                    new Card(CardType.PERSON, "Colonel Mustard"), Color.YELLOW);

            seenCards.put(
                    new Card(CardType.PERSON, "Mrs. White"), Color.PINK);

            seenCards.put(
                    new Card(CardType.PERSON, "Miss Scarlett"), Color.GREEN);
            
            // Create the known cards panel.
            KnownCardsPanel panel = new KnownCardsPanel(handCards, seenCards);
            // Create the frame.
            JFrame frame = new JFrame("Known Cards");

            frame.setContentPane(new JScrollPane(panel));
            frame.setSize(220, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            
            //add more card

            seenCards.put(
                    new Card(CardType.ROOM, "Hall"), Color.YELLOW);

            seenCards.put(
                    new Card(CardType.ROOM, "Ballroom"), Color.PINK);

            seenCards.put(
                    new Card(CardType.ROOM, "Kitchen"), Color.GREEN);

            seenCards.put(
                    new Card(CardType.WEAPON, "Lead Pipe"), Color.YELLOW);

            seenCards.put(
                    new Card(CardType.WEAPON, "Dagger"), Color.PINK);
            //update the panel
            panel.refresh(handCards, seenCards);
            

            

        });
    }

}
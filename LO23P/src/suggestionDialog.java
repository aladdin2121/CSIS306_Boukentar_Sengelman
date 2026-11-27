import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class suggestionDialog extends JDialog {
	
	
	JLabel currentRoom;
	JLabel person;
	JTextField currentRoomField;
	JLabel weapon;
	JButton submit;
	JButton cancel;	
	JPanel suggestionPanel;
	JComboBox<Card> personBox;
	JComboBox<Card> weaponBox;
	
	public suggestionDialog() {
		
	setTitle("Make a Suggestion");
	currentRoom = new JLabel("Current room");
	person = new JLabel ("Person");
	weapon = new JLabel ("Weapon");
	
	currentRoomField = new JTextField();
	currentRoomField.setEditable(false);
	personBox = new JComboBox();
	weaponBox = new JComboBox();
	
	
	
	submit = new JButton("Submit");
	
	cancel = new JButton("Cancel");
	cancel.addActionListener(e -> dispose());
	
	
	
	setModal(true);
	
	
		
	suggestionPanel = new JPanel(new GridLayout(4,2));
	
	suggestionPanel.add(currentRoom );
	suggestionPanel.add(currentRoomField);
	suggestionPanel.add(person);
	suggestionPanel.add(personBox);
	suggestionPanel.add(weapon);
	suggestionPanel.add(weaponBox);
	suggestionPanel.add(submit);
	suggestionPanel.add(cancel);
	
	add(suggestionPanel);
	
	
	pack();
	
	
	
	}
	public void setCurrentRoom(String room) {
		currentRoomField.setText(room);
	}
	public void setPersonWeaponBox(Set<Card> personCards, Set<Card> weaponCards) {
		for(Card card: personCards) {
			personBox.addItem(card);
		}
		for(Card card: weaponCards) {
			weaponBox.addItem(card);
		}
		
		
	}
	public void showMessage() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	

}

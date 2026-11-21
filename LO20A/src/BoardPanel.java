/*
 * Class: BoardPanel
 * 
 * Details:
 * it creates the board graphics
 * 
 * Authors: Alaa Eddine Boukentar Assefar
 * 			Sophie SengelmanLipps 
 * 			
 * 
 * Start Date: 11/20/2026
 * 
 * Collaborators: None
 * 
 * Sources: None
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardPanel extends JPanel{
	Board board;
	JPanel gameBoard;
	private final int boxSize = 25;
	
	public BoardPanel() {
		board = Board.getInstance();
		gameBoard = new JPanel(new GridLayout(board.getNumRows(),board.getNumColumns()));
		 setPreferredSize(new Dimension(board.getNumColumns() * boxSize + 10, board.getNumRows() * boxSize + 10));
	}
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

	    for (int row = 0; row < board.getNumRows(); row++) {

	        for (int col = 0; col < board.getNumColumns(); col++) {

	            BoardCell cell = board.getCell(row, col);

	            int x = col * boxSize;
	            int y = row * boxSize;

	            if (cell.isRoom()) {
	            	g.setColor(Color.LIGHT_GRAY);
	                g.fillRect(x, y, boxSize, boxSize);

	                if (cell.isSecretPass()) {

	                    g.setColor(Color.BLACK);

	                    g.fillRect(x , y , boxSize, boxSize);

	                    g.setColor(Color.YELLOW);

	                    g.fillRect(x + boxSize/3,y + boxSize/3,boxSize/2 ,boxSize/2 );
	                }

	                if (cell.isLabel()) {
	                	g.setColor(Color.MAGENTA);
	                    g.setFont(new Font("Arial", Font.BOLD, 9));
                 

	                    g.drawString(board.getRoom(cell.getInitial()).getName(), x-boxSize/2, y-boxSize/2);
	                }

	                

	            }
	            else if (cell.isEmpty()) {

	                g.setColor(Color.BLACK);
	                g.fillRect(x, y, boxSize, boxSize);

	            }
	            else if (cell.isDoorway()) {

	                g.setColor(Color.YELLOW);
	                g.fillRect(x, y, boxSize, boxSize);

	                g.setColor(Color.BLUE);

	                int doorSize = 5;

	                if (cell.getDoorDirection() == DoorDirection.UP) {
	                    g.fillRect(x + boxSize / 2 - doorSize / 2,
	                               y, doorSize, doorSize);
	                }

	                else if (cell.getDoorDirection() == DoorDirection.DOWN) {
	                    g.fillRect(x + boxSize / 2 - doorSize / 2,
	                               y + boxSize - doorSize,
	                               doorSize, doorSize);
	                }

	                else if (cell.getDoorDirection() == DoorDirection.LEFT) {
	                    g.fillRect(x, y + boxSize / 2 - doorSize / 2,
	                               doorSize, doorSize);
	                }

	                else if (cell.getDoorDirection() == DoorDirection.RIGHT) {
	                    g.fillRect(x + boxSize - doorSize,
	                               y + boxSize / 2 - doorSize / 2,
	                               doorSize, doorSize);
	                }
	                g.setColor(Color.BLACK);
	                g.drawRect(x, y, boxSize, boxSize);

	            }
	            
	            else {

	                g.setColor(Color.CYAN);
	                g.fillRect(x, y, boxSize, boxSize);

	                g.setColor(Color.BLACK);
	                g.drawRect(x, y, boxSize, boxSize);

	            }
	        }
	    }
	    for(Player player: board.getPlayers()) {
	    	int xLocation = player.getLocation().getCol()*boxSize;
	    	int yLocation = player.getLocation().getRow()*boxSize;
	    	g.setColor(player.getColor());
	    	g.fillOval(xLocation, yLocation, boxSize, boxSize);
	    }
				
			
		
		
		
	}
}

package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Board {
	final static int NUM_ROWS = 25;
	final static int NUM_COLUMNS = 25;
	
	private BoardCell[][] cellGrid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private Map<Character, Room> room;
	private String layoutConfigFile;
	private String setupConfigFile;
	/*
     * variable and methods used for singleton pattern
     */
     private static Board theInstance = new Board();
     // constructor is private to ensure only one can be created
     private Board() {
    	 
            super() ;
            targets = new HashSet<>();
    		visited = new HashSet<>();
    		room = new HashMap<>();
    		
    		
    		cellGrid = new BoardCell[NUM_ROWS][NUM_COLUMNS];
    		
    		for (int i = 0; i < NUM_ROWS; i++) {
    			for (int j = 0; j < NUM_COLUMNS; j++) {
    				cellGrid[i][j] = new BoardCell(i, j);
    			}
    		}
    		
    		for (int i = 0; i<NUM_ROWS;i++) {
    			for (int j=0;j<NUM_COLUMNS;j++) {
    				//to keep using the same board cells we use cellGrid[i][j]
    				BoardCell cell = cellGrid [i][j];
    				if(i>0) {cell.addAdjacency(cellGrid[i-1][j]);}
    				if(i<NUM_ROWS-1) {cell.addAdjacency(cellGrid[i+1][j]);}
    				if(j>0) {cell.addAdjacency(cellGrid[i][j-1]);}
    				if(j<NUM_COLUMNS-1) {cell.addAdjacency(cellGrid[i][j+1]);}
    			}
    		}
            
     }
     // this method returns the only Board
     public static Board getInstance() {
            return theInstance;
     }
     /*
      * initialize the board (since we are using singleton pattern)
      */
     
	
	public void setConfigFiles(String csv, String txt) {
		
		this.layoutConfigFile = csv;
		this.setupConfigFile = txt;
		
	}
	public void initialize() {
		
		setUpConfig();
		layOutConfig();
		
		
		
	}
	public void calcTargets(BoardCell start, int length) {
		
		if (length == 0 ) {
			targets.add(start);
			return;
		}
		
		visited.add(start);
				
		for (BoardCell adjCell : start.getAdjList()) {
			if (!visited.contains(adjCell)) {
				calcTargets(adjCell, length-1);
			}
		}
		visited.remove(start);
		
	}
	public Room getRoom(char c) {
		return room.get(c);
	}
	public BoardCell getCell(int row, int col) {
		return cellGrid[row][col];
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public int getNumRows() {
		return this.NUM_ROWS;
	}
	public int getNumColumns() {
		return this.NUM_COLUMNS;
	}
	//reading clueConfig.txt and loading rooms
	private void setUpConfig() {
		
		try {
		File file = new File(this.setupConfigFile);
		Scanner scan = new Scanner (file);
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			String[] tokens = line.split(", ");
			if (tokens[0].equalsIgnoreCase("room")){
				String roomName = tokens[1];
				char roomChar = tokens[2].charAt(0);
				Room r1 = new Room (roomName);
				room.put(roomChar, r1);
				
			}
			//split the 2 kind of spaces, in case we want to make walkways and 
			//unused their own object and not a room object
			else if (tokens[0].equalsIgnoreCase("space")){
				String roomName = tokens[1];
				char roomChar = tokens[2].charAt(0);
				Room r1 = new Room (roomName);
				room.put(roomChar, r1);
				
			}
		}
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	private void layOutConfig() {
		
		
	}

}

package clueGame;


public class Board {
	private static int NUM_ROWS = 4;
	private static int NUM_COLUMNS = 4;
	
	/*
     * variable and methods used for singleton pattern
     */
     private static Board theInstance = new Board();
     // constructor is private to ensure only one can be created
     private Board() {
            super() ;
     }
     // this method returns the only Board
     public static Board getInstance() {
            return theInstance;
     }
     /*
      * initialize the board (since we are using singleton pattern)
      */
     
	
	public void setConfigFiles(String s, String a) {
		
	}
	public void initialize() {
		
	}
	public Sting getRoom(char c) {
		String s="";
		return s;
	}
	

}

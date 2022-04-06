package assignment1;

import java.util.Scanner;

public class HumanAgent extends Agent {
	private final Scanner scanner = new Scanner(System.in);
//	private final List<String> validCoordinates;

	
	
    public HumanAgent(Board board) {
        super(board);
    }

    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     * @return the valid human inputted Move
     */
    @Override
    public Move getMove() {
        Move move = null;
        boolean valid1 = false;
        boolean valid2 = false;
        boolean valid3 = false;
        String fromCoordString = null;
        String toCoordString = null;
        Coordinate fromCoord;
        Coordinate toCoord;
    	while (!valid3) {
	        while (!valid1) {
	        	System.out.print("Enter a Starting Cell: ");
	            fromCoordString = scanner.nextLine();
	            if (fromCoordString.matches("[a-zA-Z]\\d") && fromCoordString.length() == 2 &&
	            		Character.digit(fromCoordString.charAt(0), 36) - 10 < board.size &&
	            		Integer.parseInt(fromCoordString.substring(1)) <= board.size) {
	            	valid1 = true;
	            } else {
	            	System.out.print("Invalid input. Please enter a letter and a number within bounds (eg. A1) \n");
	            }
	        }
	        while (!valid2) {
	        	System.out.print("Enter a Destination Cell: ");
	            toCoordString = scanner.nextLine();
	            if (toCoordString.matches("[a-zA-Z]\\d") && toCoordString.length() == 2
	            		&& Character.digit(toCoordString.charAt(0), 36) - 10 < board.size &&
	            		Integer.parseInt(toCoordString.substring(1)) <= board.size) {
	            	valid2 = true;
	            } else {
	            	System.out.print("Invalid input. Please enter a letter and a number within bounds (eg. A1)");
	            }
	        }
	        fromCoord = new Coordinate(Integer.parseInt(fromCoordString.substring(1)) - 1, Character.digit(fromCoordString.charAt(0), 36) - 10);
	        toCoord = new Coordinate(Integer.parseInt(toCoordString.substring(1)) - 1, Character.digit(toCoordString.charAt(0), 36) - 10);
	        move = new Move(this.board.board[fromCoord.row][fromCoord.col], this.board.board[toCoord.row][toCoord.col]);
	        if (!board.isValidMove(move)) {
	        	System.out.print("Invalid move.");
	        	valid1 = false;
	        	valid2 = false;
	        } else {
	        	valid3 = true;
	        }
        }
        return move;
    }
}

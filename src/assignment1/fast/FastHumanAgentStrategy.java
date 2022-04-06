package assignment1.fast;

import assignment1.Agent;
import assignment1.Board;
import assignment1.Coordinate;
import assignment1.Move;

import java.io.IOException;
import java.util.Scanner;

public class FastHumanAgentStrategy extends Agent implements FastAgentStrategy {
    private final Scanner scanner = new Scanner(System.in);
    private int timeLimit = 20000;

    /**
     * An Agent that can play ThreeMusketeers
     *
     * @param board a Board the Agent can play on
     */
    public FastHumanAgentStrategy(Board board) {
        super(board);
    }

    @Override
    public Move getMove() {
        Move move = null;
        String fromCoordString = null;
        String toCoordString = null;
        Coordinate fromCoord;
        Coordinate toCoord;
        System.out.print("Enter a Starting Cell: ");
        long start = System.currentTimeMillis();
        try {

            while (System.currentTimeMillis() - start < timeLimit) {

                if (System.in.available() > 0) {
                    fromCoordString = scanner.nextLine();
                    if (fromCoordString.matches("[a-zA-Z]\\d") && fromCoordString.length() == 2 &&
                            Character.digit(fromCoordString.charAt(0), 36) - 10 < board.size &&
                            Integer.parseInt(fromCoordString.substring(1)) <= board.size) {
                    } else {
                        System.out.print("Invalid input. Please enter a letter and a number within bounds (eg. A1) \n");
                        continue;
                    }

                    System.out.print("Enter a Destination Cell: ");
                    toCoordString = scanner.nextLine();
                    if (toCoordString.matches("[a-zA-Z]\\d") && toCoordString.length() == 2
                            && Character.digit(toCoordString.charAt(0), 36) - 10 < board.size &&
                            Integer.parseInt(toCoordString.substring(1)) <= board.size) {
                    } else {
                        System.out.print("Invalid input. Please enter a letter and a number within bounds (eg. A1)");
                        continue;
                    }
                    fromCoord = new Coordinate(Integer.parseInt(fromCoordString.substring(1)) - 1, Character.digit(fromCoordString.charAt(0), 36) - 10);
                    toCoord = new Coordinate(Integer.parseInt(toCoordString.substring(1)) - 1, Character.digit(toCoordString.charAt(0), 36) - 10);
                    move = new Move(this.board.board[fromCoord.row][fromCoord.col], this.board.board[toCoord.row][toCoord.col]);
                    if (!board.isValidMove(move)) {
                        System.out.print("Invalid move.");
                    } else {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(move != null) {
        return move;
    }
    else {
    	System.out.println("\n Time is up...");
    	System.exit(0);
    	return null;
    }
    
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}

package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import assignment1.Exceptions.InvalidMoveException;

public class FileToMoveHistory implements Converter{

	private String boardFilePath;
	private Board board;

	public void MoveHistoryToFile(Board board, String boardFilePath) {
		this.board = board;
		this.boardFilePath = boardFilePath;
	}
	
	public static List<Move> convert(Board board, String movesFilePath) throws InvalidMoveException {
		Scanner scanner;
		List<Move> moveList = null;
		int col1;
		int col2;
		
		File movesFile = new File(movesFilePath);
        try {
            scanner = new Scanner(movesFile);
            while (scanner.hasNextLine()) {
            	String moveText = scanner.nextLine();
            	String[] moveData = moveText.split(" ");
            	col1 = Utils.convertCharToInt(moveData[0].charAt(1)) - 1;
            	col2 = Utils.convertCharToInt(moveData[2].charAt(1)) - 1;
            	moveList.add(new Move(new Cell(new Coordinate(moveData[0].charAt(1), col1)), new Cell(new Coordinate(moveData[2].charAt(1), col2))));
            }
        scanner.close();
        } catch (FileNotFoundException e) {
        	return moveList;
        }
		return moveList;
	}

}

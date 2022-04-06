package assignment1;

import java.util.ArrayList;
import java.util.List;

import assignment1.Exceptions.InvalidMoveException;

public class MoveHistory {

	private String movesFilePath;
	private Board board;
	private final List<Move> moves;

	public MoveHistory(Board board, List<Move> moves) {
		this.moves = moves;
		this.board = board;
	}
	
	static void export(Board board, List<Move> moves) {
		MoveHistoryToFile.convert(board, moves);
	}
	
	static List<Move> load(Board board, String filePath) throws InvalidMoveException {
		return FileToMoveHistory.convert(board, filePath);
	}
	
}

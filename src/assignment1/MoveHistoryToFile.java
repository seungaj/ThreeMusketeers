package assignment1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class MoveHistoryToFile implements Converter{

	private Board board;
	private Object movesFilePath;
	private List<Move> moves;

	public MoveHistoryToFile(Board board, List<Move> moves) {
		this.board = board;
		this.moves = moves;
	}
	
	public static void convert(Board board, List<Move> moves) {
		File file = new File(board.filePath + " Moves.txt");
		
        try {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            for (Move move: moves) {
                writer.write(move.toString());
                writer.newLine();
                }
            writer.close();
            System.out.printf("Saved board to %s.\n", board.filePath + " Moves.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", board.filePath + " Moves.txt");
        }
		
	}

}

package assignment1;

import java.util.ArrayList;
import java.util.List;

public class BoardEvaluatorImpl implements BoardEvaluator {

    /**
     * Calculates a score for the given Board
     * A higher score means the Musketeer is winning
     * A lower score means the Guard is winning
     * Add heuristics to generate a score given a Board
     * @param board Board to calculate the score of
     * @return int Score of the given Board
     */
    @Override
    // score is determined by how many moves are available to a Musketeer.
    public int evaluateBoard(Board board) { // TODO
    	List<Cell> MusketeerCells = new ArrayList<Cell>();
        int score;
        MusketeerCells = board.getMusketeerCells();
        score = ((board.getPossibleDestinations(MusketeerCells.get(0)).size() + board.getPossibleDestinations(MusketeerCells.get(1)).size() + board.getPossibleDestinations(MusketeerCells.get(2)).size()) / 12) * 100;
        return score;
    }
}

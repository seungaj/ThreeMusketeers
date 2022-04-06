package assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class GreedyHuman extends Agent {
    Board boardCopy;
    BoardEval boardEvaluator;
    int depth = 10;
    public ArrayList<Chance> chanceMoves = new ArrayList<Chance>();

    public GreedyHuman(Board board) {
        super(board);
        this.boardEvaluator = new BoardEval();
    }

    public GreedyHuman(Board board, BoardEval boardEvaluator, int depth) {
        super(board);
        this.boardEvaluator = boardEvaluator;
        this.depth = depth;
    }

    /**
     * Gets a valid move that the GreedyAgent can do.
     * Uses MiniMax strategy with Alpha Beta pruning
     * @return a valid Move that the GreedyAgent can perform on the Board
     */
    @Override
    public void getmove() {
        boardCopy = Singleton.getInstance().getBoard();
        int bestScore = boardCopy.getTurn().equals(Piece.Type.MUSKETEER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move chosenMove = null;

        List<Move> possibleMoves = boardCopy.getPossibleMoves();
        for (Move move: possibleMoves) {
            Move moveCopy = new Move(move);
            Piece.Type turn = boardCopy.getTurn();
            boardCopy.move(move);
    		
            int score = this.minimax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            Chance c = new Chance(move.toString());
            c.setScore(String.valueOf(score));
            this.chanceMoves.add(c);
            if (turn.equals(Piece.Type.MUSKETEER) && score > bestScore) {
                bestScore = score;
                chosenMove = new Move(moveCopy);
            }
            else if (turn.equals(Piece.Type.GUARD) && score < bestScore) {
                bestScore = score;
                chosenMove = new Move(moveCopy);
            }

            boardCopy.undoMove(moveCopy);
        }

        assert chosenMove != null;
        
        //return new Move(
                //board.getCell(chosenMove.fromCell.getCoordinate()),
                //board.getCell(chosenMove.toCell.getCoordinate()));
    }

    /**
     * Runs minimax with alpha beta pruning to find the optimal moves
     * Uses heuristics implemented by a BoardEvaluator
     * @param depth a counter to stop the algorithm from going deeper than the given depth
     * @param alpha value used for alpha beta pruning
     * @param beta value used for alpha beta pruning
     * @return
     */
    private int minimax(int depth, int alpha, int beta) {
        if (depth == 0 || boardCopy.isGameOver()) {
            return boardEvaluator.evaluateBoard(boardCopy);
        }

        int bestScore = boardCopy.getTurn().equals(Piece.Type.MUSKETEER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        List<Move> possibleMoves = boardCopy.getPossibleMoves();
        for (Move move: possibleMoves) {
            Move moveCopy = new Move(move);
            Piece.Type turn = boardCopy.getTurn();
            boardCopy.move(move);

            int score = this.minimax(depth - 1, alpha, beta);

            if (turn.equals(Piece.Type.MUSKETEER)) {
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, bestScore);
            }
            else {
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, bestScore);
            }

            boardCopy.undoMove(moveCopy);

            if (beta <= alpha) {
                break;
            }
        }
        return boardEvaluator.evaluateBoard(boardCopy);
    }

	@Override
	public Move getMove() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Select a move: ");
        while (!scanner.hasNext("[0123456789]")) {
            System.out.print("Invalid option.");
            scanner.next();
        }
        return board.getPossibleMoves().get(Integer.parseInt(scanner.next()));
	}
}

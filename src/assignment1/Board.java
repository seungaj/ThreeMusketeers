package assignment1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Iterator;

import assignment1.Exceptions.InvalidMoveException;

public class Board {
    public int size = 5;

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;
    String filePath;

    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     * @param board Board to copy
     */
    public Board(Board board) {
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = new Cell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }

    /**
     * @return the Piece.Type (Muskeeteer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }

    /**
     * Get the cell located on the board at the given coordinate.
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) {
        return this.board[coordinate.row][coordinate.col];
    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }

    /**
     * Gets all the Musketeer cells on the board.
     * @return List of cells
     */
    public List<Cell> getMusketeerCells() {
    	List<Cell> musketeerCells = new ArrayList<Cell>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (this.board[row][col].hasPiece()) {
                	if (this.board[row][col].getPiece().getType() == Piece.Type.MUSKETEER) {
                    	musketeerCells.add(this.board[row][col]);
                	}
                }
            }
        }
        return musketeerCells;
    }

    /**
     * Gets all the Guard cells on the board.
     * @return List of cells
     */
    public List<Cell> getGuardCells() {
    	List<Cell> guardCells = new ArrayList<Cell>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (this.board[row][col].hasPiece()) {
                	if (this.board[row][col].getPiece().getType() == Piece.Type.GUARD) {
                    	guardCells.add(this.board[row][col]);
                	}
                }
            }
        }
        return guardCells;
    }

	
    /**
     * Executes the given move on the board and changes turns at the end of the method.
     * @param move a valid move
     */
    public void move(Move move) {
    	int fromR;
    	int fromC;
    	int toR;
    	int toC;
    	Piece.Type nextTurn;
    	fromR = move.fromCell.getCoordinate().row;
    	fromC = move.fromCell.getCoordinate().col;
    	toR = move.toCell.getCoordinate().row;
    	toC = move.toCell.getCoordinate().col;

    	this.board[toR][toC].setPiece(this.board[fromR][fromC].getPiece());
    	this.board[fromR][fromC].removePiece();

    	if (this.board[toR][toC].getPiece().getType() == Piece.Type.GUARD) {
    		nextTurn = Piece.Type.MUSKETEER;
    	} else {
    		nextTurn = Piece.Type.GUARD;
    	}

    	this.turn = nextTurn;
    	
    	
    }

    /**
     * Undo the move given.
     * @param move Copy of a move that was done and needs to be undone. The move copy has the correct piece info in the
     *             from and to cell fields. Changes turns at the end of the method.
     */
    public void undoMove(Move move) {
    	int fromR;
    	int fromC;
    	int toR;
    	int toC;
    	fromR = move.fromCell.getCoordinate().row;
    	fromC = move.fromCell.getCoordinate().col;
    	toR = move.toCell.getCoordinate().row;
    	toC = move.toCell.getCoordinate().col;

    	this.board[fromR][fromC].setPiece(move.fromCell.getPiece());
    	if (this.board[toR][toC].hasPiece()) {
    		this.board[toR][toC].setPiece(move.toCell.getPiece());
    	} else {
    		this.board[toR][toC].removePiece();
    	}
    	

    	if (this.turn == Piece.Type.GUARD) {
    		this.turn = Piece.Type.MUSKETEER;
    	} else {
    		this.turn = Piece.Type.GUARD;
    	}
    }

    /**
     * Checks if the given move is valid. Things to check:
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     * @param move a move
     * @return     True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) {
//    	String fromPiece = "_";
//    	String toPiece = "_";
    	int fromR;
    	int fromC;
    	int toR;
    	int toC;
    	
//		if (move.fromCell.hasPiece()) {
//    		fromPiece  = move.fromCell.getPiece().getSymbol();
//    	} else {
//    		return false;
//    	}
//		if (move.toCell.hasPiece()) {
//    		toPiece  = move.fromCell.getPiece().getSymbol();
//    	}

    	fromR = move.fromCell.getCoordinate().row;
    	fromC = move.fromCell.getCoordinate().col;
    	toR = move.toCell.getCoordinate().row;
    	toC = move.toCell.getCoordinate().col;
    	if (fromR == toR && fromC == toC) {
    		return false;
    	}
    	if (Math.abs(fromR - toR) + Math.abs(fromC - toC) > 1) {
    		return false;
    	}
		
        if (!move.fromCell.getPiece().canMoveOnto(move.toCell)) {
        	return false;
        
//        	if (toPiece == "X") {
//        		return false;
//        	}
//        	if (toPiece == "_") {
//        		return false;
//        	}
//        if (fromPiece == "O") {
//        	if (toPiece == "O") {
//        		return false;
//        	}
//        	if (toPiece == "X") {
//        		return false;
//        	}
        	
        }
    return true;
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     * @return      Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() {
    	List<Cell> turnCells = new ArrayList<Cell>();
    	List<Cell> possibleCells = new ArrayList<Cell>();
    	int i;

        if (this.turn == Piece.Type.MUSKETEER) {
        	turnCells = this.getMusketeerCells();
        } else {
        	turnCells = this.getGuardCells();
        }
        for (i = 0; i < turnCells.size(); i++) {
        	if (this.getPossibleDestinations(turnCells.get(i)).size() > 0) {
        		possibleCells.add(turnCells.get(i));
        	}
        }
        return possibleCells;
    }

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) {
    	List<Cell> possibleCells = new ArrayList<Cell>();
    	Cell toCell;
    	Move move;
    	
    	if (!(fromCell.getCoordinate().row == 0)) {
    		toCell = this.board[fromCell.getCoordinate().row - 1][fromCell.getCoordinate().col];
    		move = new Move(fromCell, toCell);
    		if (this.isValidMove(move)) {
    			possibleCells.add(toCell);
    		}
    	}
    	
    	if (!(fromCell.getCoordinate().row == this.size - 1)) {
    		toCell = this.board[fromCell.getCoordinate().row + 1][fromCell.getCoordinate().col];
    		move = new Move(fromCell, toCell);
    		if (this.isValidMove(move)) {
    			possibleCells.add(toCell);
    		}
    	}
    	
    	if (!(fromCell.getCoordinate().col == 0)) {
    		toCell = this.board[fromCell.getCoordinate().row][fromCell.getCoordinate().col - 1];
    		move = new Move(fromCell, toCell);
    		if (this.isValidMove(move)) {
    			possibleCells.add(toCell);
    		}
    	}
    	
    	if (!(fromCell.getCoordinate().col == this.size - 1)) {
    		toCell = this.board[fromCell.getCoordinate().row][fromCell.getCoordinate().col + 1];
    		move = new Move(fromCell, toCell);
    		if (this.isValidMove(move)) {
    			possibleCells.add(toCell);
    		}
    	}
    	
    	return possibleCells;
    }
    
    

    /**
     * Get all the possible moves that can be made this turn.
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() {
    	List<Cell> possibleFromCells = new ArrayList<Cell>();
    	List<Cell> possibleToCells = new ArrayList<Cell>();
    	List<Move> possibleMoves = new ArrayList<Move>();
    	int i;
    	int j;
    	
    	possibleFromCells = this.getPossibleCells();
    	
    	for (i = 0; i < possibleFromCells.size(); i++) {
    		possibleToCells = this.getPossibleDestinations(possibleFromCells.get(i));
    		for (j = 0; j < possibleToCells.size(); j++) {
    			possibleMoves.add(new Move(possibleFromCells.get(i), possibleToCells.get(j)));
    		}
    	}
    	return possibleMoves;
    }
    

    
    public boolean isMusketeerDanger() {
//          long numRows = cells.stream().map(cell -> cell.getCoordinate().row).distinct().count();
//          long numCols = cells.stream().map(cell -> cell.getCoordinate().col).distinct().count();
//          return numRows == 1 || numCols == 1;
      	
      	List<Integer> nearRow = new ArrayList<Integer>();
      	List<Integer> nearCol = new ArrayList<Integer>();
      	
      	MusketeerCollection myMusks = new MusketeerCollection();
        for (Cell c : getMusketeerCells()) {
        	myMusks.add(c);
        }
        
        Iterator<Cell> it = myMusks.iterator();
        while (it.hasNext()) {
        	Cell cell = it.next();
        	nearRow.add(cell.getCoordinate().row);
        	nearCol.add(cell.getCoordinate().col);
        }

      	Boolean ans = false;
      	List<Integer> findNearRow =  nearRow.stream().distinct().collect(Collectors.toList());
      	List<Integer> findNearCol =  nearCol.stream().distinct().collect(Collectors.toList());
      	if (findNearRow.size() == 2) {
      		return Math.abs(findNearRow.get(0) - findNearRow.get(1)) == 1;
      	}if(findNearCol.size() == 2) {
      		return Math.abs(findNearCol.get(0) - findNearCol.get(1)) == 1;
      	}
      	return false;
    }
    
    public void dangerSign(boolean bool) {
    	if (bool) {
    		System.out.println("Musketeers in danger. Watch out!");
    	}
    }

    /**
     * Checks if the game is over and sets the winner if there is one.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOver() {
    	List<Cell> musketeerCells = new ArrayList<Cell>();
    	
    	//GUARD WIN CONDITIONS
    	
    	//All Musketeers in the same row or column
    	musketeerCells = this.getMusketeerCells();
    	if (musketeerCells.get(0).getCoordinate().col == musketeerCells.get(1).getCoordinate().col &&
    			musketeerCells.get(0).getCoordinate().col == musketeerCells.get(2).getCoordinate().col ||
    			musketeerCells.get(0).getCoordinate().row == musketeerCells.get(1).getCoordinate().row &&
    			musketeerCells.get(0).getCoordinate().row == musketeerCells.get(2).getCoordinate().row) {
    		this.winner = Piece.Type.GUARD;
    		return true;
    	}
    	
    	//MUSKETEER WIN CONDITIONS
    	
    	//No guards on board
    	if (this.getGuardCells().isEmpty()) {
    		this.winner = Piece.Type.MUSKETEER;
    		return true;
    	}
    	
    	//Musketeers cannot move
    	if (this.turn == Piece.Type.MUSKETEER && getPossibleMoves().isEmpty()) {
    		this.winner = Piece.Type.MUSKETEER;
    		return true;
    	}
    	
    	return false;
    }

    /**
     * Saves the current board state to the boards directory
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);
        
        this.filePath = filePath;
        filePath = filePath + ".txt";
        
        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("  | A B C D E\n");
        boardStr.append("--+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path.
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    private void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece: pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(new Guard());
                    case "X" -> cell.setPiece(new Musketeer());
                    default -> cell.setPiece(null);
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }
}

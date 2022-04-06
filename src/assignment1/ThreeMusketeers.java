package assignment1;

import assignment1.Exceptions.InvalidMoveException;
import assignment1.register.Register;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreeMusketeers {

    private final Board board;
    private Agent musketeerAgent, guardAgent;
    private final Scanner scanner = new Scanner(System.in);
    private List<Move> moves = new ArrayList<>();
    private int gameMode;

    // All possible game modes
    public enum GameMode {
        Human("Human vs Human"),
        HumanRandom("Human vs Computer (Random)"),
        HumanGreedy("Human vs Computer (Greedy)");

        private final String gameMode;
        GameMode(final String gameMode) {
            this.gameMode = gameMode;
        }
    }

    /**
     * Default constructor to load Starter board
     */
    public ThreeMusketeers() {
        this.board = new Board();
    }

    /**
     * Constructor to load custom board
     * @param boardFilePath filepath of custom board
     */
    public ThreeMusketeers(String boardFilePath) {
    	String basicPath = boardFilePath;
        this.board = Singleton.getInstance().getBoard();
        
        basicPath.replaceAll(".txt", "");
        File file = new File(boardFilePath + " Moves.txt");
        if(file.exists() && !file.isDirectory()) { 
            try {
				this.moves = MoveHistory.load(this.board, boardFilePath + " Moves.txt");
			} catch (InvalidMoveException e) {
				return;
			}
        }
    }
    
    /**
     * Restart the game
     */
    public void restart(int mode) {
    	if(mode == 1) {
    		while(this.moves.size() != 0) {
    			Move move;
        		move = new Move(moves.remove(moves.size()-1));
        		Singleton.getInstance().getBoard().undoMove(move);
        	}
    	System.out.println("");
    	System.out.println("Returning back to main menu...");
    	System.out.println("");
    	String boardFileName = "Boards/Starter.txt";
        ThreeMusketeers game = new ThreeMusketeers(boardFileName);

        game.play();
    }
    	else{
    		System.out.println("");
        	System.out.println("Resetting board...");
        	System.out.println("");
        	while(this.moves.size() != 0) {
        		this.undoMove();
        	}
        	this.play(GameMode.values()[this.gameMode]);
    	}
    }

    /**
     * Play game with human input mode selector
     */
    public void play(){
        System.out.println("Welcome! \n");
        final GameMode mode = getModeInput();
        System.out.println("Playing " + mode.gameMode);
        play(mode);
    }

    /**
     * Play game without human input mode selector
     * @param mode the GameMode to run
     */
    public void play(GameMode mode){
        selectMode(mode);
        runGame();
    }

    /**
     * Mode selector sets the correct agents based on the given GameMode
     * @param mode the selected GameMode
     */
    private void selectMode(GameMode mode) {
        switch (mode) {
            case Human -> {
                musketeerAgent = new HumanAgent(board);
                guardAgent = new HumanAgent(board);
            }
            case HumanRandom -> {
                String side = getSideInput();
                
                // The following statement may look weird, but it's what is known as a ternary statement.
                // Essentially, it sets musketeerAgent equal to a new HumanAgent if the value M is entered,
                // Otherwise, it sets musketeerAgent equal to a new RandomAgent
                musketeerAgent = side.equals("M") ? new HumanAgent(board) : new RandomAgent(board);
                
                guardAgent = side.equals("G") ? new HumanAgent(board) : new RandomAgent(board);
            }
            case HumanGreedy -> {
                String side = getSideInput();
                musketeerAgent = side.equals("M") ? new HumanAgent(board) : new GreedyAgent(board);
                guardAgent = side.equals("G") ? new HumanAgent(board) : new GreedyAgent(board);
            }
        }
    }

    /**
     * Runs the game, handling human input for move actions
     * Handles moves for different agents based on current turn 
     */
    private void runGame() {
        while(!board.isGameOver()) {
            System.out.println("\n" + board);

            final Agent currentAgent;
            if (board.getTurn() == Piece.Type.MUSKETEER)
                currentAgent = musketeerAgent;
            else
                currentAgent = guardAgent;

            if (currentAgent instanceof HumanAgent) // Human move
                switch (getInputOption()) {
                    case "M":
                        move(currentAgent);
                        break;
                    case "G":
                    	moveWithStrategy();
                    	break;
                    case "U":
                        if (moves.size() == 0) {
                            System.out.println("No moves to undo.");
                            continue;
                        }
                        else if (moves.size() == 1 || isHumansPlaying()) {
                            undoMove();
                        }
                        else {
                            undoMove();
                            undoMove();
                        }
                        break;
                    case "S":
                        board.saveBoard();
                        MoveHistory.export(board, moves);
                        break;
                    case "R":
                    	System.out.println("");
                    	System.out.println("For main menu, type '1'. To start over while conserving settings, type '2': ");
                    	while (!scanner.hasNext("[12]")) {
                            System.out.print("Invalid option. Enter '1' or '2': ");
                            scanner.next();
                        }
                    	if(scanner.hasNext("1")) {
                    		this.restart(1);
                    	}
                    	else if(scanner.hasNext("2")) {
                    		this.restart(2);
                    	}
                    	break;
                }
            else { // Computer move
                System.out.printf("[%s] Calculating move...\n", currentAgent.getClass().getSimpleName());
                move(currentAgent);
                board.dangerSign(board.isMusketeerDanger());
            }
        }

        System.out.println(board);
        System.out.printf("\n%s won!%n", board.getWinner().getType());
        System.out.println("");
        System.out.println("Would you like to play again? Type 'Y' for yes, 'N' for no: ");
        while (!scanner.hasNext("[YNyn]")) {
            System.out.print("Invalid option. Enter 'Y' or 'N': ");
            scanner.next();
        }
        if(scanner.hasNext("Y")) {
        	System.out.println("For main menu, type '1'. To start over while conserving settings, type '2': ");
        	while (!scanner.hasNext("[12]")) {
                scanner.next();
            }
        	if(scanner.hasNext("1")) {
        		this.restart(1);
        	}
        	else if(scanner.hasNext("2")) {
        		this.restart(2);
        	}
        }
        else {
        	System.out.println("");
        	System.out.println("Bye bye!");
        	System.exit(0);
        }
    }

    private void moveWithStrategy() {
    	ChanceDirector director = new ChanceDirector();
    	director.construct();
		ArrayList<Chance> chances = director.getChances();

		for (int i =0; i<chances.size(); i++) {
			System.out.println(i + " " + chances.get(i).toString());
		}
		GreedyHuman human = new GreedyHuman(board);
		Move move;
		move = human.getMove();
		moves.add(new Move(move));
        board.move(move);
    }

	/**
     * Gets a move from the given agent, adds a copy of the move using the copy constructor to the moves stack, and
     * does the move on the board.
     * @param agent Agent to get the move from.
     */
    protected void move(final Agent agent) { // TODO
    	final Move move = agent.getMove();
        moves.add(new Move(move));
        board.move(move);
    }

    /**
     * Removes a move from the top of the moves stack and undoes the move on the board.
     */
    private void undoMove() { // TODO
    	Move move = this.moves.get(moves.size() - 1);
    	board.undoMove(move);
    	this.moves.remove(moves.size() - 1);
    }

    /**
     * Get human input for move action
     * @return the selected move action, 'M': move, 'U': undo, and 'S': save
     */
    private String getInputOption() {
        System.out.printf("[%s] Enter 'M' to move, 'G' to move with strategy, 'U' to undo, 'S' to save and 'R' to restart: ", board.getTurn().getType());
        while (!scanner.hasNext("[MUSRGmusrg]")) {
            System.out.print("Invalid option. Enter 'M', 'G', 'U', 'S', or 'R': ");
            scanner.next();
        }
        return scanner.next().toUpperCase();
    }

    /**
     * Returns whether both sides are human players
     * @return True if both sides are Human, False if one of the sides is a computer
     */
    private boolean isHumansPlaying() {
        return musketeerAgent instanceof HumanAgent && guardAgent instanceof HumanAgent;
    }

    /**
     * Get human input for side selection
     * @return the selected Human side for Human vs Computer games,  'M': Musketeer, G': Guard
     */
    private String getSideInput() {
        System.out.print("Enter 'M' to be a Musketeer or 'G' to be a Guard: ");
        while (!scanner.hasNext("[MGmg]")) {
            System.out.println("Invalid option. Enter 'M' or 'G': ");
            scanner.next();
        }
        return scanner.next().toUpperCase();
    }

    /**
     * Get human input for selecting the game mode
     * @return the chosen GameMode
     */
    private GameMode getModeInput() {
        System.out.println("""
                    0: Human vs Human
                    1: Human vs Computer (Random)
                    2: Human vs Computer (Greedy)""");
        System.out.print("Choose a game mode to play i.e. enter a number: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid option. Enter 0, 1, or 2: ");
            scanner.next();
        }
        final int mode = scanner.nextInt();
        if (mode < 0 || mode > 2) {
            System.out.println("Invalid option.");
            return getModeInput();
        }
        this.gameMode = mode;
        return GameMode.values()[mode];
    }

    private boolean login(){

        int res = -1;
        while (res == -1){
            System.out.println("""
                    0: Login
                    1: Register""");
            res = scanner.nextInt();
            if (res < 0 | res > 1) res = -1;
        }
        return res == 0;
    }

    public static void main(String[] args) {
        Register register = new Register();
        String boardFileName = "Boards/Starter.txt";
        ThreeMusketeers game = new ThreeMusketeers(boardFileName);
        if (game.login()) register.login();
        else register.register();
        game.play();
    }
}

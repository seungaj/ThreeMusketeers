package assignment1;

public abstract class Agent {

    protected Board board;
    
    /**
     * An Agent that can play ThreeMusketeers
     * @param board a Board the Agent can play on
     */
    public Agent(Board board){
        this.board = board;
    }
    
     /**
     * Gets a valid move that the Agent can perform on the Board
     * @return a valid Move that the Agent can do
     */
    public abstract Move getMove();

	/**
	 * Gets a valid move that the GreedyAgent can do.
	 * Uses MiniMax strategy with Alpha Beta pruning
	 * @return a valid Move that the GreedyAgent can perform on the Board
	 */
	public void getmove() {
		// TODO Auto-generated method stub
		
	}
}

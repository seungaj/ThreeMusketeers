package assignment1;

import java.util.ArrayList;

public class ChanceDirector {
	private ArrayList<Chance> chances = new ArrayList<Chance>();

	public ChanceDirector() {
	}

	public void construct() {
		if (Singleton.getInstance().getBoard().getTurn() == Piece.Type.GUARD) {
			GreedyHuman human = new GreedyHuman(Singleton.getInstance().getBoard());
			human.getmove();
			for(Chance m:human.chanceMoves) {
				chances.add(m);
			}
		}
		else if(Singleton.getInstance().getBoard().getTurn() == Piece.Type.MUSKETEER) {
			GreedyHuman human = new GreedyHuman(Singleton.getInstance().getBoard());
			human.getmove();
			for(Chance m:human.chanceMoves) {
				chances.add(m);
			}
		}
	}
	
	public ArrayList<Chance> getChances() {
		return this.chances;
	}
}

package assignment1;

import java.util.ArrayList;
import java.util.Iterator;

public class MusketeerCollectionIterator implements Iterator<Cell> {
	private ArrayList<Cell> muskCells;
	private int index;
	
	public MusketeerCollectionIterator(ArrayList<Cell> c) {
		this.muskCells = c;
		index = 0;
	}

	
	public boolean hasNext() {
		return this.index < this.muskCells.size();
	}
	
	public Cell next() {
		return this.muskCells.get(index++);
	}
}

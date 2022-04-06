package assignment1;

import java.util.ArrayList;
import java.util.Iterator;


public class MusketeerCollection implements Iterable<Cell>{
	ArrayList<Cell> muskCells;
	
	public MusketeerCollection() {
		muskCells = new ArrayList<Cell>();
	}
	
	public void add(Cell cell) {
		muskCells.add(cell);
	}
	
	public Iterator<Cell> iterator(){
		return new MusketeerCollectionIterator(muskCells);
	}
}

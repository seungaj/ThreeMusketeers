package assignment1;

public class Chance {
	
	private String name;
	private int score;

	public Chance(String name) {
		this.name = name;
	}
	public void setScore(String score) {
		this.score = Integer.parseInt(score);
	}
	
	public String toString() {
		return name + ": " + "score = " + score;
	}
}

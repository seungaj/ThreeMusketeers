package assignment1;

public class ChanceBuilder {
	
	private String name;
	
	public ChanceBuilder(String name) {
		this.name = name;
	}

	public Chance getChance() {
		Chance c = new Chance(this.name);
		return c;
	}
}

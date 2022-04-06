package assignment1;

class Singleton
{
	private static Singleton instance;
	private Board board;

	private Singleton()
	{
	}

	public static Singleton getInstance()
	{
		if (instance == null)
		{
			synchronized(Singleton.class)
			{
				if (instance == null)
				{
					instance = new Singleton();
					instance.addAttribute();
				}
			}            
		}

		return instance;
	}

	public void addAttribute()
	{
		instance.board = new Board();
		
	}

	public Board getBoard() {
		return instance.board;
	}
}

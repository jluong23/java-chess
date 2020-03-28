package boardgame;

public enum Colour {
	WHITE, BLACK;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
	
}

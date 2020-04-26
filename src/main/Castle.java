package main;

public enum Castle {
	KING_SIDE, QUEEN_SIDE;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

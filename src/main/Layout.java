package main;

public enum Layout {
	STANDARD, ROOK_TEST;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

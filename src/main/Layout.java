package main;

public enum Layout {
	STANDARD, EMPTY;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

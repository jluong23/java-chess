package main;

public enum Layout {
	STANDARD, EMPTY, TEST_TWO_KNIGHTS;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

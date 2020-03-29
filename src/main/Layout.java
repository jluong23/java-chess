package main;

public enum Layout {
	STANDARD;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

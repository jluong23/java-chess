package main;

public enum Layout {
	DEFAULT;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

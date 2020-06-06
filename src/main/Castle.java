package main;

public enum Castle {
	KING_SIDE("O-O"), QUEEN_SIDE("O-O-O");
	
	private String code;
	Castle(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

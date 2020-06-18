package main;

import boardgame.Coordinate;
import boardgame.Direction;

public enum Castle {
	KING_SIDE("O-O", Direction.RIGHT), QUEEN_SIDE("O-O-O", Direction.LEFT);
	
	private String code;
	private Direction direction;
	Castle(String code, Direction direction) {
		this.code = code;
		this.direction = direction;
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}
	
	/**
	 * Get a castle enum from the code 
	 * @param code - The code to get the castle enum from
	 * @return castle enum
	 */
	public static Castle fromCode(String code) {
		for (Castle side : Castle.values()) {
			if(code.equalsIgnoreCase(side.getCode())) return side;
		}
		return null;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Checks if a coordinate is a castling move
	 * @param coordinate - the coordinate to check
	 * @return boolean result whether coordinate is a castling move or not
	 */
	public static boolean isCastlingMove(Coordinate coordinate) {
		if(Castle.fromCode(coordinate.getCoordinate()) == null) return false;
		return true;
	}

}

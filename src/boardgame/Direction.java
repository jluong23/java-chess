package boardgame;

public enum Direction {
	UP(1,0),
	DOWN(-1,0),
	LEFT(0,-1),
	RIGHT(0,1),
	UP_LEFT(1,-1),
	UP_RIGHT(1,1),
	DOWN_LEFT(-1,-1),
	DOWN_RIGHT(-1,1);
	
	Direction(int dr, int dc) {
		this.dr = dr;
		this.dc = dc;
	}
	
	public static Direction[] horizontal() {
		return new Direction[] {Direction.LEFT, Direction.RIGHT};
	}
	public static Direction[] vertical() {
		return new Direction[] {Direction.UP, Direction.DOWN};
	}
	
	public static Direction[] horizontalAndVertical() {
		Direction[] directions = new Direction[4];
		System.arraycopy(horizontal(), 0, directions, 0, horizontal().length);
		System.arraycopy(vertical(), 0, directions, 2, vertical().length);
		return directions;
	}
	
	public static Direction[] diagonalForwards() {
		return new Direction[] {Direction.UP_LEFT, Direction.UP_RIGHT};
	}
	public static Direction[] diagonalBackwards() {
		return new Direction[] {Direction.DOWN_LEFT, Direction.DOWN_RIGHT};
	}
	
	public static Direction[] diagonal() {
		Direction[] directions = new Direction[4];
		System.arraycopy(diagonalForwards(), 0, directions, 0, diagonalForwards().length);
		System.arraycopy(diagonalBackwards(), 0, directions, 2, diagonalBackwards().length);
		return directions;
	}
	
	public int dr; //change in row value on board
	public int dc; //change in col value on board
	
	
}

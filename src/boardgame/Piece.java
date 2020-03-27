package boardgame;
public abstract class Piece {
	
	//attributes
	private String symbol;
	private Colour colour;
	private int timesMoved;
	private Coordinate position;
	
	public Piece(Colour colour) {
		this.colour = colour;
		this.timesMoved = 0;
	}
		
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the colour
	 */
	public Colour getColour() {
		return colour;
	}

	/**
	 * @param colour the colour to set
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}

	/**
	 * @return the timesMoved
	 */
	public int getTimesMoved() {
		return timesMoved;
	}

	/**
	 * @return the position
	 */
	public Coordinate getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
	}

	//methods
	public abstract Coordinate[] getPossibleMoves();
	
	/**
	 * Move piece to a given coordinate on board
	 * @param coordinate
	 * @param board
	 */
	public void move(Coordinate coordinate, Board board) {	
		//set previous position to null
		board.set(getPosition(), null);
		//set new position to this piece
		board.set(coordinate, this);
		this.timesMoved++;
	}
	/**
	 * Returns array of squares attacked by piece
	 * Default to getPossibleMoves()
	 * @return tiles The tiles the piece attacks
	 */
	public Coordinate[] getSquaresAttacking() {
		return this.getPossibleMoves();
	}
	
}

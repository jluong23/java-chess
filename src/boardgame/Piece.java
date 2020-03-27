package boardgame;
public abstract class Piece {
	
	//attributes
	private String symbol;
	private Colour colour;
	private int timesMoved;
	private Coordinate position;
	
	public Piece(Colour colour, Coordinate position) {
		this.colour = colour;
		this.position = position;
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
	 * @param timesMoved the timesMoved to set
	 */
	public void setTimesMoved(int timesMoved) {
		this.timesMoved = timesMoved;
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
	
	public void move(Coordinate coordinate) {		
		
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

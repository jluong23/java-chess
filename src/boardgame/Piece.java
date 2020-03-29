package boardgame;

import java.util.ArrayList;

import boardgame.exceptions.NoBoardException;
import boardgame.exceptions.TooManyPlayersException;

public abstract class Piece {
	
	//attributes
	private char symbol;
	private Player player;
	private int timesMoved;
	private Coordinate position;
	private Board board;
	private int pointValue;
	private boolean alive;
	
	/**
	 * Creates a new piece object
	 * @param player the player controlling the piece
	 */
	public Piece(Player player) {
		this.player = player;
		this.timesMoved = 0;
	}

	/**
	 * @return the symbol
	 */
	public char getSymbol() {
		return symbol;
	}


	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * @return the timesMoved
	 */
	public int getTimesMoved() {
		return timesMoved;
	}

	/**
	 * @return the pointValue
	 */
	public int getPointValue() {
		return pointValue;
	}
	/**
	 * @param pointValue the pointValue to set
	 */
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}
	/**
	 * @param alive the alive to set
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
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
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
	}

	//methods
	
	/**
	 * Fetch arraylist of tiles the piece can move to
	 * @return tiles Arraylist of tiles
	 */
	
	/**
	 * Fetch arraylist of tiles the piece can move to
	 * @return tiles Arraylist of tiles
	 * @throws TooManyPlayersException - If the piece's board attribute is null
	 */
	public abstract ArrayList<Coordinate> getPossibleMoves() throws NoBoardException;
	
	/**
	 * Move piece to a given coordinate on board
	 * @param coordinate
	 * @param board
	 */
	public void move(Coordinate coordinate, Board board) {	
		//set previous position to null
		board.setPiece(getPosition(), null);
		//set new position to this piece
		board.setPiece(coordinate, this);
		this.timesMoved++;
	}
	/**
	 * Returns arraylist of squares attacked by piece
	 * Default to getPossibleMoves()
	 * @return tiles The tiles the piece attacks
	 * 
	 */
	public ArrayList<Coordinate> getSquaresAttacking() {
		try {
			return this.getPossibleMoves();
		} catch (NoBoardException e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	
	public static ArrayList<Piece> generatePiece(){
		
	}
	
	@Override
	public String toString() {
		return player.getColour() + " " +  symbol + " at position " + position;
	}
	
}

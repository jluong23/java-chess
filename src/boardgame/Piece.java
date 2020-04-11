package boardgame;
import java.util.ArrayList;

import boardgame.exceptions.*;
import boardgame.*;
public abstract class Piece implements Cloneable{
	
	//attributes
	private char symbol;
	private Player player;
	private int timesMoved;
	private Coordinate position;
	private Board board;
	private int pointValue;
	private boolean alive;
	private Direction[] moveableDirections;
	private Direction[] attackableDirections;
	private int moveableDistance;
	private int attackDistance;

	/**
	 * Creates a new piece object
	 * @param player the player controlling the piece
	 */
	public Piece(Player player) {
		this.player = player;
		this.timesMoved = 0;
		player.getMyPieces().add(this); 
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
	 * @param position the position to set
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
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
	 * @return the moveableDirections
	 */
	public Direction[] getMoveableDirections() {
		return moveableDirections;
	}

	/**
	 * @param moveableDirections the moveableDirections to set
	 */
	public void setMoveableDirections(Direction[] moveableDirections) {
		this.moveableDirections = moveableDirections;
	}

	/**
	 * @return the moveableDistance
	 */
	public int getMoveableDistance() {
		return moveableDistance;
	}

	/**
	 * @param moveableDistance the moveableDistance to set
	 */
	public void setMoveableDistance(int moveableDistance) {
		this.moveableDistance = moveableDistance;
	}
	
	/**
	 * @return the attackDistance
	 */
	public int getAttackDistance() {
		return attackDistance;
	}

	/**
	 * @param attackDistance the attackDistance to set
	 */
	public void setAttackDistance(int attackDistance) {
		this.attackDistance = attackDistance;
	}
	
	/**
	 * @return the attackableDirections
	 */
	public Direction[] getAttackableDirections() {
		return attackableDirections;
	}

	/**
	 * @param attackableDirections the attackableDirections to set
	 */
	public void setAttackableDirections(Direction[] attackableDirections) {
		this.attackableDirections = attackableDirections;
	}

	//methods
	
	/**
	 * Fetch the tiles the piece can perform a given action in a given a direction and number of tiles to search
	 * @param dir the direction to search in
	 * @param action the action this piece should perform
	 * @param numTiles the number of tiles to search 
	 * @param action the action to seaerch for eg. attack or defend
	 * @return moves - the possible moves the piece can make in the given direction
	 */
	protected abstract ArrayList<Coordinate> searchTiles(Direction dir, int numTiles, boardgame.Action action);
	
	/**
	 * Fetch arraylist of all tiles the piece can perform an action to from this piece's current position.
	 * searchTiles() is called for all directions of the action.
	 * @return moves - Arraylist of coordinates the piece can move to
	 * @throws NoBoardException - If the piece's board attribute is null
	 */
	public ArrayList<Coordinate> getMoves(boardgame.Action action) throws NoBoardException{
		if(board == null)throw new NoBoardException(this); //if the piece does not have a board attribute
		else {
			ArrayList<Coordinate> moves = new ArrayList<>();
			
			//get which list of directions to check for given an action
			Direction[] directionList = null;
			switch(action) {
			case ATTACK:
				directionList = getAttackableDirections();
				break;
			case MOVE_TO:
				directionList = getMoveableDirections();
				break;
			}
			for (Direction direction : directionList) {
				//for all directions they can move in, fetch the tiles the piece can move to in each of those directions
				moves.addAll(searchTiles(direction, moveableDistance, action));
			}
			return moves;
		}
	}
	
	/**
	 * Call getMoves() for all possible actions
	 * @return allMoves - an array list of all possible moves for all possible actions
	 * @throws NoBoardException - If the piece's board attribute is null
	 */
	public ArrayList<Coordinate> getAllMoves() throws NoBoardException{
		ArrayList<Coordinate> allMoves = new ArrayList<Coordinate>();
		for (boardgame.Action action : boardgame.Action.values()) {
			allMoves.addAll(getMoves(action));
		}
		return allMoves;
	}
	/**
	 * Move piece to a given coordinate on board
	 * @param coordinate - Coordinate to move to
	 */
	public void move(Coordinate coordinate) throws InvalidMoveException {	
		if(board == null)throw new NoBoardException(this); //if the piece does not have a board attribute
		else if(getAllMoves().contains(coordinate)) {
			//set previous position to null
			board.setPiece(getPosition(), null);
			//set new position to this piece
			board.setPiece(coordinate, this);
			this.timesMoved++;				
		}
		else {
			throw new InvalidMoveException(this, coordinate);
		}
	}
	
	/**
	 * Return a shallow copy of the instance
	 * @return clone A clone of the instance object
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return n shallow copies of the instance as an ArrayList
	 * @param n the number of copies to make
	 * @return duplicatePieces the clones of the instance in an ArrayList
	 */
	public ArrayList<Piece> clone(int n){
		ArrayList<Piece> duplicatePieces = new ArrayList<Piece>();
		for (int i = 0; i < n; i++) {
			duplicatePieces.add( (Piece) this.clone());
		}
		return duplicatePieces;
	}
	
	@Override
	public String toString() {
		return Character.toString(symbol);
	}

	
}

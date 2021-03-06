package boardgame;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import boardgame.exceptions.InvalidSettingsException;
import boardgame.exceptions.NoBoardException;
public abstract class Piece implements Cloneable{
	
	//attributes
	private char icon;
	private String name;
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
		name = getClass().getSimpleName().toUpperCase();

	}
	/**
	 * @return the icon
	 */
	public char getIcon() {
		return icon;
	}


	/**
	 * @param icon the icon to set
	 */
	public void setIcon(char icon) {
		this.icon = icon;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * 
	 * @return Returns first letter of name of piece
	 */
	public char getFirstLetterName() {
		return getName().toCharArray()[0];
	}
	
	/**
	 * 
	 * @return Returns first letter of name of piece with first letter of player colour prefixed
	 */
	public String getFirstLetterNameWithColourPrefix() {
		if(getPlayer().getColour() == null)throw new NoSuchElementException(player + "has no colour attribute");
		else 
			{
				return getPlayer().getColour().toString().charAt(0) + Character.toString(getFirstLetterName());
			}
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
	 * Get all moves for the unit, not necessarily a valid move.
	 * @param directions - the directions to search in 
	 * @param numTiles - the number of tiles the piece can move
	 * @param numTiles - the number of tiles this piece can perform an action in
	 * @param action - the action to check
	 * @return
	 */
	public abstract Set<Coordinate> getTotalMoves(Action action);
	
	/**
	 * Fetch arraylist of all tiles the piece can perform an action to from this piece's current position.
	 * @return moves - Arraylist of coordinates the piece can move to
	 * @throws NoBoardException - If the piece's board attribute is null
	 */
	public Set<Coordinate> getValidMoves(boardgame.Action action) throws NoBoardException{
		if(board == null)throw new NoBoardException(this); //if the piece does not have a board attribute
		else {
			//total moves for that action
			Set<Coordinate> totalMoves = getTotalMoves(action);
			Set<Coordinate> validMoves = new HashSet<Coordinate>();
			
			for (Coordinate move : totalMoves) {
				if(this.validMove(move)) validMoves.add(move);
			}
			return validMoves;
		}
	}
	
	/**
	 * Checks if a move is valid in the context of the current board configuration and game.
	 * Note this is not to do with how pieces moves.
	 * eg. For chess, a king can't stay in check, and pinned pieces can not move to put their king in chess.
	 * @param coordinate - the coordinate to check for this piece to move to
	 * @return boolean result whether move to coordinate is valid
	 * @throws InvalidSettingsException 
	 */
	public abstract boolean validMove(Coordinate coordinate) throws NoBoardException, InvalidSettingsException;
	
	/**
	 * Call getValidMoves() for all possible actions
	 * @return allMoves - an array list of all possible moves for all possible valid actions
	 * @throws NoBoardException - If the piece's board attribute is null
	 */
	public Set<Coordinate> getAllValidMoves() throws NoBoardException{
		Set<Coordinate> allMoves = new HashSet<Coordinate>();
		for (boardgame.Action action : boardgame.Action.values()) {
			allMoves.addAll(getValidMoves(action));
		}
		return allMoves;
	}

	/**
	 * Call getTotalMoves() for all possible actions
	 * @return allMoves - an array list of all possible moves for all possible actions (not necessarily valid)
	 * @throws NoBoardException - If the piece's board attribute is null
	 */
	public Set<Coordinate> getAllTotalMoves() throws NoBoardException{
		Set<Coordinate> allMoves = new HashSet<Coordinate>();
		for (boardgame.Action action : boardgame.Action.values()) {
			allMoves.addAll(getTotalMoves(action));
		}
		return allMoves;
	}
	/**
	 * Move piece to a given coordinate on board
	 * @param coordinate - Coordinate to move to
	 * @return boolean result whether the move is valid or not
	 */
	public boolean move(Coordinate coordinate){	
		if(board == null)return false; //if the piece does not have a board attribute
		//check if valid move
		else if(getAllValidMoves().contains(coordinate)) {
			// check if it was a capture
			if(getBoard().at(coordinate) != null) {
				//do capture consequence, abstract method depending on board game.
				//pass in the piece captures
				captureConsequnce(board.at(coordinate));
			}
			
			//make the move
			//set previous position to null
			board.setPiece(getPosition(), null);
			//set new position to this piece
			board.setPiece(coordinate, this);
			this.timesMoved++;								
			return true;
		}else {
			return false;			
		}
	}
	/**
	 * Called when this piece captures another piece.
	 * For chess, the consequences will be: 
	 * <ul>
	 * <li> Add to this piece's owner's captured list. </li>
	 * <li> Set the piece's alive attribute to false </li>
	 * <li> Set the captured piece's position to null. </li>
	 * </ul>
	 * Another game may choose to do something else.
	 * @param capturedPiece - The piece captured
	 */
	protected abstract void captureConsequnce(Piece capturedPiece);

	/**
	 * Checks if a piece on a given row index according to their position attribute
	 * @param rowIndex - the row index to check on
	 * @return onRow - whether the piece is on the given row index
	 */
	public boolean onRow(int rowIndex) {
		return rowIndex == getPosition().getIndexes()[0];
	}
	
	//used as space when creating new board positions with played pseudo moves
	private static Piece pseudoCapturedPiece;
	private static Coordinate pseudoOriginalCoordinate;
	private static Coordinate pseudoNewCoordinate;
	
	/**
	 * Place this piece at a new coordinate. Used to check for valid board positions
	 * @param coordinate - the coordinate to place this piece on
	 */
	public void makePseudoMove(Coordinate coordinate) {
		//the piece that this piece is capturing, can be null if no capture and piece is only moving to a square
		pseudoCapturedPiece = getBoard().at(coordinate);
		if(pseudoCapturedPiece != null) {
			//remove from pieces list of the player if a capture occurs
			pseudoCapturedPiece.getPlayer().getMyPieces().remove(pseudoCapturedPiece);
		}
		//store original and new position as class variables, place back after moving the piece
		pseudoOriginalCoordinate = getPosition();
		pseudoNewCoordinate = coordinate;
		//emulate the move made, move this piece to the coordinate checked and set previous position to null
		getBoard().setPiece(coordinate, this);
		getBoard().setPiece(pseudoOriginalCoordinate, null);
	}
	
	/**
	 * Undo the pseudo move played, placing the piece back to where it was in the original position
	 */
	public void popPseudoMove() {
		//place captured piece back
		getBoard().setPiece(pseudoOriginalCoordinate, this);
		//reset piece back to original position,
		getBoard().setPiece(pseudoNewCoordinate, pseudoCapturedPiece);
		//add captured piece back to player pieces list if capture occured
		if(pseudoCapturedPiece != null) {
			getBoard().getOtherPlayer(getPlayer()).getMyPieces().add(pseudoCapturedPiece);
		}
		
		//reset pseudo variables
		pseudoCapturedPiece = null;
		pseudoOriginalCoordinate = null;
		pseudoNewCoordinate = null;
	}
	
	/**
	 * Return a shallow copy of the instance
	 * @return clone A clone of the instance object
	 */
	public Piece clone() {
		Piece clone;
		try {
			clone = (Piece) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		// add the clone to player pieces
		player.getMyPieces().add(clone); 
		return clone;

	}
	
	@Override
	public String toString() {
		return Character.toString(icon);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	
}

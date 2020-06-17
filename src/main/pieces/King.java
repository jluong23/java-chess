package main.pieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import boardgame.Action;
import boardgame.Coordinate;
import boardgame.Direction;
import boardgame.Piece;
import boardgame.Player;
import boardgame.exceptions.InvalidColourException;
import boardgame.exceptions.InvalidSettingsException;
import boardgame.exceptions.NoBoardException;
import main.Castle;
import main.ChessCoordinate;
import main.ChessPiece;

public class King extends ChessPiece {

	public King(Player player) {
		super(player);
		//king can move and attack in all directions
		setMoveableDirections(Direction.values());
		setAttackableDirections(Direction.values());

		//moving and attacking 1 distance away
		setMoveableDistance(1);
		setAttackDistance(1);
		
	}
	/**
	 * return whether this King object is in check on it's board attribute
	 * @return boolean result 
	 */
	public boolean inCheck() throws NoBoardException{
		if(getBoard() == null) throw new NoBoardException(this);
		else {
			Player otherPlayer = getBoard().getOtherPlayer(getPlayer());
			//return if this king's position is attacked by the other player
			return getBoard().squareAttacked(getPosition(), otherPlayer);
		}
	}
	/**
	 * Returns whether the king is checkmated on it's board attribute (in check and has no valid moves)
	 * @return boolean result
	 */
	public boolean inCheckmate() {
		return inCheck() && getAllValidMoves().size() == 0;
	}
	
	/**
	 * Returns whether the king is in stale mate (not in check and player has no valid moves)
	 * @return boolean result
	 */
	public boolean inStalemate() {
		//assume kings player does not have moves until a counter example is found in foreach loop
		boolean hasMoves = false;
		for (Set<Coordinate> moves : getPlayer().getAllValidMoves().values()) {
			if(moves.size() > 0) hasMoves = true;
		}
		return !inCheck() && !hasMoves;
	}
	
	
	
	@Override
	public boolean move(Coordinate coordinate) {
		//check if castling is legal and correct code for castling is entered
		if(getAllValidMoves().contains(coordinate) && Castle.isCastlingMove(coordinate)) {
			this.castle(Castle.fromCode(coordinate.getCoordinate()));
			return true;
		} else {
			return super.move(coordinate);			
		}
	}
	
	@Override
	public Set<Coordinate> getTotalMoves(Action action) {
		Set<Coordinate> currentMoves = super.getTotalMoves(action);
		//add castle moves
		currentMoves.add(new ChessCoordinate(Castle.QUEEN_SIDE.getCode()));
		currentMoves.add(new ChessCoordinate(Castle.KING_SIDE.getCode()));
		return currentMoves;
	}
	
	@Override
	public boolean validMove(Coordinate coordinate) throws NoBoardException, InvalidSettingsException {
		//for castling, the coordinate's string is the code for castling, "O-O-O" for kingside or "O-O" for queenside
		
		if(Castle.isCastlingMove(coordinate)) return canCastle(Castle.fromCode(coordinate.getCoordinate()));
		//regular king move, return overriden valid move
		return super.validMove(coordinate);
	}
	
	/**
	 * Castle the king to the given side on the king's board attribute, assuming the castle is valid
	 * @param side - the side to castle on 
	 */
	public void castle(Castle side) {
		Coordinate newKingCoordinate, newRookCoordinate;
		int newKingColumnIndex, newRookColumnIndex;
		
		//set new column indexes for king and rook
		switch (side) {
		case KING_SIDE:
			//king should be 1 away from edge of board, rook should be to the left of it
			newKingColumnIndex = getBoard().getBoardArray().length - 2;
			newRookColumnIndex = newKingColumnIndex - 1;
			break;
		case QUEEN_SIDE:
			//king should be 2 away from edge of board, rook should be to the right of it
			newKingColumnIndex = 2;
			newRookColumnIndex = newKingColumnIndex + 1;
			break;
		default:
			throw new NoSuchElementException(side + " side for castling does not exist");
		}
		
		int[] newKingIndexes = {getFirstRowIndex(), newKingColumnIndex};
		int[] newRookIndexes = {getFirstRowIndex(), newRookColumnIndex};
		newKingCoordinate = ChessCoordinate.toCoordinate(newKingIndexes);
		newRookCoordinate = ChessCoordinate.toCoordinate(newRookIndexes);

		ArrayList<Coordinate> adjacentTiles = getAdjacentTiles(side.getDirection());
		Iterator<Coordinate> iterator = adjacentTiles.iterator();
		boolean rookFound = false;
		while(!rookFound) {
			Piece piece = getBoard().at(iterator.next());
			if(piece != null && piece.getName().equalsIgnoreCase("Rook")) {
				rookFound = true;
				//set old positions to null
				getBoard().setPiece(getPosition(), null);
				getBoard().setPiece(piece.getPosition(), null);
				
				//set new positions
				getBoard().setPiece(newKingCoordinate, this);
				getBoard().setPiece(newRookCoordinate, piece);
			}
		}
	}
	/**
	 * Returns a boolean result whether a king can castle given the current board configuration. 
	 * <br><br>Conditions: 
	 * <ol>
	 * <li>The king and castling rook cannot have moved before castling.</li>
	 * <li>The king cannot castle out of check/checkmate.</li>
	 * <li>The king cannot castle through check.</li>
	 * <li>The pieces in between the king and rook must move out of the way before castling.</li>
	 * </ol>
	 * @param side
	 * @return
	 */
	public boolean canCastle(Castle side) {		
		//can't castle out of check or castle if king has already moved
		if(inCheck() || getTimesMoved() > 0)return false;
		
		ArrayList<Piece> aliveRooks = getPlayer().getPieces("Rook", true);
		//can't castle if king has no ally rooks on the board
		if(aliveRooks.size() == 0)return false;
		
		//direction of castle
		Direction direction = side.getDirection();
		
		//7 for white, 0 for black in a default chess board
		int firstRowIndex = getFirstRowIndex();
		
		//get the adjacent coordinates in the direction
		ArrayList<Coordinate> adjacentTiles = getAdjacentTiles(direction);
		//castling through check initially false until a square attacked is found
		boolean castleThroughCheck = false;
		
		for (Coordinate coordinate: adjacentTiles) {
			//if non rook piece on the according side of king, return false
			Piece piece = getBoard().at(coordinate);
			if(piece!=null) {
				if(!piece.getName().equalsIgnoreCase("Rook"))return false;
				else{
					//case where the piece is a rook adjacent to king
					//can only castle if that rook hasn't moved, 
					//both king and rook are on first rank and
					
					return onRow(firstRowIndex) && piece.getTimesMoved() == 0 && !castleThroughCheck;
				}
			}else {
				//square is empty, check if attacked by enemy piece, 
				//setting castle through check to true (as a counter example is found)
				Player otherPlayer = getBoard().getOtherPlayer(getPlayer());
				if(getBoard().squareAttacked(coordinate, otherPlayer)) castleThroughCheck = true;
					
			}
		}
		
		return false; //exhausted all adjacent pieces without finding a rook, rook must have moved already		
	}
	
	/**
	 * Returns the first row index of the king on the board using the king's player attribute colour
	 * @return first row index
	 */
	private int getFirstRowIndex() {
		switch (getPlayer().getColour()) {
		case WHITE:
			//default to index 7 on standard chess board
			return getBoard().getBoardArray().length-1;
		case BLACK:
			return 0;
		default:
			throw new InvalidColourException(this + " has an invalid colour, needs to be black or white.");
		}
	}
	
}

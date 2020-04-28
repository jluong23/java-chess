package main.pieces;

import java.util.ArrayList;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import boardgame.*;
import boardgame.exceptions.InvalidColourException;
import boardgame.exceptions.NoBoardException;
import main.Castle;
import main.ChessPiece;
import main.ChessPieceNames;

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
		
		//figure out which the rook is on to castle. right for kingside, left for queenside
		Direction direction = null;
		switch (side) {
		case KING_SIDE:
			direction = Direction.RIGHT;
			break;
		case QUEEN_SIDE:
			direction = Direction.LEFT;
			break;
		default:
			throw new RuntimeErrorException(null, "Castle side parameter not registered, must be Castle.KING_SIDE or Castle.QUEEN_SIDE");
		}
		
		//figure out the firstRowIndex for the colour. 7 for white, 0 for black in a default chess board
		int firstRowIndex = 0;
		switch (getPlayer().getColour()) {
		//when black, already set to 0
		case WHITE:
			//default to index 7 on standard chess board
			firstRowIndex = getBoard().getBoardArray().length-1;
			break;	
		default:
			throw new InvalidColourException(this + " has an invalid colour, needs to be black or white.");
		}
		
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
	
}

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
			for (Direction dir : Direction.values()) {
				Piece pieceAhead = pieceAhead(dir);
				if(pieceAhead != null) {
					
					//initi
					boolean inCheck = false;
					
					//if this king is in a coordinate covered by an enemy piece, this king is in check
					HashMap<Piece, ArrayList<Coordinate>> opponentTilesCovered = getBoard().getOtherPlayer(getPlayer()).getTotalMoves(Action.ATTACK);
					for (ArrayList<Coordinate> tileCoveredList : opponentTilesCovered.values()) {
						if(tileCoveredList.contains(this.getPosition())) inCheck = true;
					}
					
					if(inCheck) return true; 
				}
			}
		}
		//can't be in check, exhausted all directions
		return false;
	}
	
	public boolean canCastle(Castle side) {
		int firstRank = 0;
		switch (getPlayer().getColour()) {
		//when black, firstRank already set to 0
		case WHITE:
			//default to index 7 on standard chess board
			firstRank = getBoard().getBoardArray().length-1;
			break;	
		default:
			throw new InvalidColourException(this + " has an invalid colour, needs to be black or white.");
		}
		
		ArrayList<Piece> aliveRooks = getPlayer().getPieces("Rook", true);
		
		
		switch (side) {
		case KING_SIDE:
		case QUEEN_SIDE:
			
		default:
			throw new RuntimeErrorException(null, "Castle side parameter not registered, must be Castle.KING_SIDE or Castle.QUEEN_SIDE");
		}
	}
	
}

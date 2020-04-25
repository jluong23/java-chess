package main.pieces;

import boardgame.*;
import boardgame.exceptions.NoBoardException;
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
					//if the unit ahead is covering the square this king is currently on
					boolean isAttacked = pieceAhead.getTotalMoves(Action.ATTACK).contains(this.getPosition());
					boolean oppositeColour = this.getPlayer().getColour() != pieceAhead.getPlayer().getColour();
					//only return true if both conditions met, keep checking other directions if not met
					if(isAttacked && oppositeColour) return true; 
				}
			}
		}
		//can't be in check, exhausted all directions
		return false;
	}
	
}

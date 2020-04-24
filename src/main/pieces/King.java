package main.pieces;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import boardgame.*;
import boardgame.exceptions.NoBoardException;
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
			for (Direction dir : Direction.values()) {
				Piece pieceAhead = pieceAhead(dir);
				if(pieceAhead != null) {
					//in the direction i'm checking, the unit can attack in the same direction
					boolean isAttacking = Arrays.asList(pieceAhead.getAttackableDirections()).contains(dir);
					boolean oppositeColour = this.getPlayer().getColour() != pieceAhead.getPlayer().getColour();
					//only return true if both conditions met, keep checking other directions if not met
					if(isAttacking && oppositeColour) return true; 
				}
			}
		}
		//can't be in check, exhausted all directions
		return false;
	}
}

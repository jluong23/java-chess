package main.pieces;
import java.util.ArrayList;
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
			HashMap<Piece, ArrayList<Coordinate>> otherPlayerAttacks = getBoard().getOtherPlayer(getPlayer()).getAllMoves(Action.ATTACK);
			for (ArrayList<Coordinate> attacks : otherPlayerAttacks.values()) {
				if(attacks.contains(this.getPosition())) return true;
			}
			//must not be in check if attacks does not contain this king's position
			return false;
		}
	}
}

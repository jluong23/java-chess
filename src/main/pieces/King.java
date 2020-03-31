package main.pieces;
import boardgame.*;
import main.ChessPiece;

public class King extends ChessPiece {

	public King(Player player) {
		super(player);
		//king can move in all directions
		setMoveableDirections(Direction.values());
		//moving and attacking 1 distance away
		setMoveableDistance(1);
		setAttackDistance(1);
		
		
	}

}

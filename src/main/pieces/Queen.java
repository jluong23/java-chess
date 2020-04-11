package main.pieces;

import boardgame.*;
import main.ChessPiece;

public class Queen extends ChessPiece {

	public Queen(Player player) {
		super(player);
		//queen can move in all directions
		setAttackableDirections(Direction.values());
		setMoveableDirections(Direction.values());
	}


}

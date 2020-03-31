package main.pieces;

import boardgame.*;
import main.ChessPiece;

public class Queen extends ChessPiece {

	public Queen(Player player) {
		super(player);
		//queen can move in all directions
		setMoveableDirections(Direction.values());
	}


}

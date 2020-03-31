package main.pieces;

import boardgame.*;
import main.ChessPiece;

public class Bishop extends ChessPiece {

	public Bishop(Player player) {
		super(player);
		//bishops move diagonally
		setMoveableDirections(Direction.diagonal());
	}

}

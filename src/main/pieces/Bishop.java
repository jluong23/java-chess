package main.pieces;

import boardgame.*;
import main.ChessPiece;

public class Bishop extends ChessPiece {

	public Bishop(Player player) {
		super(player);
		//bishops move and attack diagonally
		setMoveableDirections(Direction.diagonal());
		setAttackableDirections(Direction.diagonal());
	}

}

package main.pieces;

import boardgame.*;
import main.ChessPiece;

public class Rook extends ChessPiece {

	public Rook(Player player) {
		super(player);
		//rook can move and attack horizontally and vertically
		setAttackableDirections(Direction.horizontalAndVertical());
		setMoveableDirections(Direction.horizontalAndVertical());
	}
	


}

package main.pieces;

import boardgame.*;
import main.ChessPiece;

public class Rook extends ChessPiece {

	public Rook(Player player) {
		super(player);
		//rook can move horizontally and vertically
		setMoveableDirections(Direction.horizontalAndVertical());
	}
	


}

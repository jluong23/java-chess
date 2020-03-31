package main.pieces;
import java.util.ArrayList;

import boardgame.*;
import main.ChessPiece;

public class Pawn extends ChessPiece {

	public Pawn(Player player) {
		super(player);
		setMoveableDirections(new Direction[] {Direction.UP});
		setMoveableDistance(2);
		setAttackDistance(1);
	}
	
	@Override

	public ArrayList<Coordinate> getSquaresAttacking() {
		//pawns attack diagonally
		return null;
	}


}

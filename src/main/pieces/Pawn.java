package main.pieces;
import java.util.ArrayList;

import boardgame.*;
import boardgame.exceptions.InvalidMoveException;
import main.ChessPiece;

public class Pawn extends ChessPiece {

	private static int FIRST_MOVE_DISTANCE = 2;
	private static int NORMAL_MOVE_DISTANCE = 1;

	public Pawn(Player player) {
		super(player);
		setMoveableDirections(new Direction[] {Direction.UP});
		setMoveableDistance(FIRST_MOVE_DISTANCE);
		setAttackDistance(NORMAL_MOVE_DISTANCE);
		
		//black pawns attack down the board from white's perspective
		switch(this.getPlayer().getColour()) {
		case BLACK:
			setAttackableDirections(Direction.diagonalBackwards());
		case WHITE:
			setAttackableDirections(Direction.diagonalForwards());
	
		}
	}
	
	@Override
	public ArrayList<Coordinate> getSquaresAttacking() {
		ArrayList<Coordinate> squaresAttacking = new ArrayList<Coordinate>();
		for (Direction dir : getMoveableDirections()) {
			squaresAttacking.addAll(getMoveableTiles(dir, getAttackDistance()));
		}
		return squaresAttacking;
	}

	@Override
	public void move(Coordinate coordinate) throws InvalidMoveException{
		super.move(coordinate);
		//pawns can move two squares on the first move, but only one afterwards
		setMoveableDistance(NORMAL_MOVE_DISTANCE);
	}

}

package main.pieces;

import boardgame.Coordinate;
import boardgame.Direction;
import boardgame.Player;
import main.ChessPiece;

public class Pawn extends ChessPiece {

	//amount of squares a pawn can move on the first move
	private static int FIRST_MOVE_DISTANCE = 2;
	//normal amount of squares a pawn can move after the first move
	private static int NORMAL_MOVE_DISTANCE = 1;

	public Pawn(Player player) {
		super(player);		
		setMoveableDistance(FIRST_MOVE_DISTANCE);
		setAttackDistance(NORMAL_MOVE_DISTANCE);
		
		//black pawns attack and move down the board from white's perspective
		switch(this.getPlayer().getColour()) {
		case BLACK:
			setMoveableDirections(new Direction[] {Direction.DOWN});
			setAttackableDirections(Direction.diagonalBackwards());
			break;
		case WHITE:
			setMoveableDirections(new Direction[] {Direction.UP});
			setAttackableDirections(Direction.diagonalForwards());
			break;
	
		}
	}
	
	@Override
	public boolean move(Coordinate coordinate){
		boolean result = super.move(coordinate);
		setMoveableDistance(NORMAL_MOVE_DISTANCE);
		return result;
		
	}

}

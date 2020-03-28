package boardgame;

import java.util.ArrayList;

import main.ChessPiece;

public class Rook extends ChessPiece {

	public Rook(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	@Override
	public ArrayList<Coordinate> getPossibleMoves() throws HasNoBoardException{
		if(this.getBoard() == null) {
			throw new HasNoBoardException(this);
		}
		else {
			ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
			return moves;
		}
	}


}

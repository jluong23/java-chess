package test;

import boardgame.HasNoBoardException;
import boardgame.Player;
import boardgame.Rook;
import main.ChessBoard;

public class TestCreateInstances {
	
	public static void main(String[] args) {
		Player p = new Player(null);
		Rook r = new Rook(p);
		ChessBoard b = new ChessBoard(null);
		r.setBoard(b);
		try {
			System.out.println(r.getPossibleMoves());
		} catch (HasNoBoardException e) {
			e.printStackTrace();
		}
	}
}

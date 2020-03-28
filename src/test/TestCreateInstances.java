package test;

import boardgame.Colour;
import boardgame.NoBoardException;
import boardgame.Player;
import main.ChessBoard;
import main.King;
import main.Rook;

public class TestCreateInstances {
	
	public static void main(String[] args) {
		Player p = new Player(Colour.BLACK);
		Rook k = new Rook(p);
		ChessBoard b = new ChessBoard(null);
		k.setBoard(b);
		try {
			System.out.println(k.getPossibleMoves());
		} catch (NoBoardException e) {
			e.printStackTrace();
		}
		System.out.println(k);
	}
}

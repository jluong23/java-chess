package test;

import boardgame.Colour;
import boardgame.Player;
import main.ChessBoard;
import main.pieces.Bishop;

public class TestCreateInstances {
	
	public static void main(String[] args) {
		Player p = new Player(Colour.WHITE);
		Bishop k = new Bishop(p);
		ChessBoard b = new ChessBoard(null);
		k.setBoard(b);
		System.out.println(k.getPossibleMoves());
		System.out.println(k);

	}
}

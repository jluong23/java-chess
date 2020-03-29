package test;

import java.util.ArrayList;
import java.util.Arrays;

import boardgame.Colour;
import boardgame.Player;
import main.ChessBoard;
import main.Layout;

public class TestCreateInstances {
	
	public static void main(String[] args) {
		Player[] playersArray = {new Player(Colour.WHITE), new Player(Colour.BLACK)};
		ArrayList<Player> players = (ArrayList<Player>) Arrays.asList(playersArray);
		ChessBoard b = new ChessBoard(Layout.STANDARD,players);
		System.out.println(b);

	}
}

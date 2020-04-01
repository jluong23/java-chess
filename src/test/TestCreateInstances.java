package test;

import java.util.Arrays;
import java.util.List;

import boardgame.Colour;
import boardgame.Player;
import main.ChessBoard;
import main.Layout;

public class TestCreateInstances {
	
	public static void main(String[] args) {
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
		Player[] playersArray = {p1,p2};
		List<Player> players = Arrays.asList(playersArray);
		ChessBoard b = new ChessBoard(Layout.STANDARD,players);
		System.out.println(b);
		
	}
}

package main;

import java.util.Arrays;
import java.util.List;

import boardgame.Colour;
import boardgame.Player;

public class Run {
	public static void main(String[] args) {
		//test starting the game
		Layout layout = Layout.STANDARD;
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
		Player[] playersArray = {p1,p2};
		List<Player> players = Arrays.asList(playersArray);
		ChessBoard b = new ChessBoard(layout,players);
		b.startGameLoop();
	}
}

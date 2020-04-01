/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boardgame.Colour;
import boardgame.Direction;
import boardgame.Player;
import main.ChessBoard;
import main.ChessCoordinate;
import main.Layout;
import main.pieces.Pawn;
import main.pieces.Rook;

/**
 * @author james
 *
 */
public class TestGetPossibleMoves {
	ChessBoard b;
	List<Player> players;
	Player p1,p2;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p1 = new Player(Colour.WHITE);
		p2 = new Player(Colour.BLACK);
		Player[] playersArray = {p1,p2};
		players = Arrays.asList(playersArray);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()}.
	 */
	@Test
	public void testRook() {
		b = new ChessBoard(Layout.EMPTY,players);
		Rook rook = new Rook(p1); //white rook
		b.setPiece(new ChessCoordinate("a1"), rook); //place rook at c4
		System.out.println(b);
		System.out.println(rook.getPossibleMoves());
		//rook should be able to move to (a2..a8) vertically and (b1..h1) horizontally
		
		b.setPiece(new ChessCoordinate("A3"), new Pawn(p1));
		//now pawn is at a3, rook can only move to a2 vertically but same horizontally
		System.out.println(rook.getPossibleMoves());
	}

}

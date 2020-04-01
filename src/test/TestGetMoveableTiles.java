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
import main.pieces.Rook;

/**
 * @author james
 *
 */
public class TestGetMoveableTiles {
	ChessBoard b;
	List<Player> players;
	
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
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
	 * Test method for {@link main.ChessPiece#getMoveableTiles(boardgame.Direction, int)}.
	 */
	@Test
	public void testRook() {
		b = new ChessBoard(Layout.EMPTY,players);
		Rook rook = new Rook(players.get(0)); //white rook
		b.setPiece(new ChessCoordinate("C4"), rook); //place rook at c4
		System.out.println(b);
		rook.getMoveableTiles(Direction.DOWN, rook.getMoveableDistance());

	}

}

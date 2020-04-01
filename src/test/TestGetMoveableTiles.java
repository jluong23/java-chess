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
	public void testGetMoveableTiles() {
		b = new ChessBoard(Layout.ROOK_TEST,players);
		System.out.println(b);
		Rook r = (Rook) b.at(new ChessCoordinate("C4"));
		r.getMoveableTiles(Direction.UP, r.getMoveableDistance());

	}

}

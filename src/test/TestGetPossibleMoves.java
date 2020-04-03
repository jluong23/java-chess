/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boardgame.Colour;
import boardgame.Coordinate;
import boardgame.Direction;
import boardgame.Piece;
import boardgame.Player;
import main.ChessBoard;
import main.ChessCoordinate;
import main.Layout;
import main.pieces.Bishop;
import main.pieces.King;
import main.pieces.Pawn;
import main.pieces.Rook;

/**
 * @author james
 *
 */
public class TestGetPossibleMoves {
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
	
	
	private void performTest(String[] expectedStrings, Piece piece) {
		List<Coordinate> expectedArr = ChessCoordinate.toCoordinate(expectedStrings);
		List<Coordinate> actualArr = piece.getPossibleMoves();
		//sort values as we want to check if they compare the same values
		Collections.sort(expectedArr);
	    Collections.sort(actualArr);
		assertEquals(expectedArr, actualArr);
	}
	

	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()} for the {@link main.pieces.Rook} class.
	 */
	@Test
	public void testRook() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Rook rook = new Rook(p1); //white rook
		
		//first test
		b.setPiece(new ChessCoordinate("a1"), rook); //place rook at c4
		//rook should be able to move to (a2..a8) vertically and (b1..h1) horizontally
		String [] expectedCoordsString = new String[]{
				"B1","C1","D1","E1","F1","G1","H1","A2","A3","A4","A5","A6","A7","A8"};
		performTest(expectedCoordsString, rook);
		
		//second test
		//now pawn is at a3, rook can only move to a2 vertically but same horizontally
		b.setPiece(new ChessCoordinate("A3"), new Pawn(p1));
		expectedCoordsString = new String[]{
				"B1","C1","D1","E1","F1","G1","H1","A2"};
		performTest(expectedCoordsString, rook);

		//third test
		//now black pawn is at a3, rook should be able to attack it on a3 and move to a2, same horizontally
		b.setPiece(new ChessCoordinate("A3"), new Pawn(p2));
		expectedCoordsString = new String[]{
				"B1","C1","D1","E1","F1","G1","H1","A2","A3"};
		performTest(expectedCoordsString, rook);
	}
	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()} for the {@link main.pieces.King} class.
	 */
	@Test
	public void testKing() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		King king = new King(p1);
		b.setPiece(new ChessCoordinate("b2"), king);
		String[] expectedCoordsString = new String[]{
				"A1","A2","A3","B3","B1","C1","C2","C3"};
		performTest(expectedCoordsString, king);
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()} for the {@link main.pieces.Bishop} class.
	 */
	@Test
	public void testBishop() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Bishop bishop = new Bishop(p1);
		b.setPiece(new ChessCoordinate("c3"), bishop);
		String[] expectedCoordsString = new String[]{
				"A2","B2","D4","E5","F6","H7", // '/' diagonal
				"A5","B4","D2","E1" // '\' diagonal
				}; 
		performTest(expectedCoordsString, bishop);
	}
	
}






/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boardgame.*;
import boardgame.exceptions.InvalidMoveException;
import main.ChessBoard;
import main.ChessCoordinate;
import main.Layout;
import main.pieces.*;
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
				"A1","B2","D4","E5","F6","G7","H8", // '/' diagonal
				"A5","B4","D2","E1" // '\' diagonal
				}; 
		performTest(expectedCoordsString, bishop);
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()} for the {@link main.pieces.Pawn} class.
	 */
	@Test
	public void testPawn() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Pawn pawn = new Pawn(p1);

		b.setPiece(new ChessCoordinate("c2"), pawn);
		String[] expectedCoordsString = new String[]{
				"C3","C4"
		};
		performTest(expectedCoordsString, pawn);
		
		try {
			pawn.move(new ChessCoordinate("c4"));
		} catch (InvalidMoveException e) {
			System.out.println(e);
		}
		expectedCoordsString = new String[]{
				"C5"
		};
		performTest(expectedCoordsString, pawn);

	}
	
	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()} for the {@link main.pieces.Knight} class.
	 */
	@Test
	public void testKnight() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Knight knight = new Knight(p1);

		b.setPiece(new ChessCoordinate("c4"), knight);
		String[] expectedCoordsString = new String[]{
				"b2","a3", //bottom left
				"a5","b6", //top left
				"d6","e5", //top right
				"e3","d2" //bottom right
				
		};
		performTest(expectedCoordsString, knight);
		
		//knight can take pawn, expected coordinates remain the same
		b.setPiece(new ChessCoordinate("b2"), new Pawn(p2));
		performTest(expectedCoordsString, knight);
		try {
			knight.move(new ChessCoordinate("a3"));
		} catch (InvalidMoveException e) {
			System.out.println(e);
		}
		expectedCoordsString = new String[]{
				"b5","c4", //top right
				"b1","c2" //bottom right
		};		
		performTest(expectedCoordsString, knight);
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()} for the {@link main.pieces.Knight} class.
	 */
	@Test
	public void testQueen() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Queen queen = new Queen(p1);
		b.setPiece(new ChessCoordinate("b2"), queen);
		String[] expectedCoordsString = new String[]{
			"a1","c3","d4","e5","f6","g7","h8", // '/' diagonal
			"a3","c1", // '\' diagonal
			"a2","c2","d2","e2","f2","g2","h2", // horizontal coordinates
			"b1","b3","b4","b5","b6","b7","b8" // vertical coordinates
		};
							
		performTest(expectedCoordsString, queen);
	}
	
	
	
	
	
}






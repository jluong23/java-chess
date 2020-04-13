/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boardgame.*;
import boardgame.exceptions.InvalidCoordinateException;
import boardgame.exceptions.InvalidMoveException;
import main.ChessBoard;
import main.ChessCoordinate;
import main.Layout;
import main.pieces.*;
/**
 * @author james
 *
 */
public class TestPieces {
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
	
	
	private void performMovementTest(String[] expectedStrings, Piece piece) {
		List<Coordinate> expectedArr = ChessCoordinate.toCoordinate(expectedStrings);
		List<Coordinate> actualArr = piece.getMoves(Action.MOVE_TO);
		//sort values as we want to check if they compare the same values
		Collections.sort(expectedArr);
	    Collections.sort(actualArr);
		assertEquals(expectedArr, actualArr);
	}
	private void performAttackTest(String[] expectedStrings, Piece piece) {
		List<Coordinate> expectedArr = ChessCoordinate.toCoordinate(expectedStrings);
		List<Coordinate> actualArr = piece.getMoves(Action.ATTACK);
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
		performMovementTest(expectedCoordsString, rook);
		performAttackTest(new String[] {}, rook); //no enemies to attack

		
		//second test
		//now pawn is at a3, rook can only move to a2 vertically but same horizontally
		b.setPiece(new ChessCoordinate("A3"), new Pawn(p1));
		expectedCoordsString = new String[]{
				"B1","C1","D1","E1","F1","G1","H1","A2"};
		performMovementTest(expectedCoordsString, rook);

		//third test
		//now black pawn is at a3, rook should be able to attack it on a3 and move to a2, same horizontally
		b.setPiece(new ChessCoordinate("A3"), new Pawn(p2));
		expectedCoordsString = new String[]{
				"B1","C1","D1","E1","F1","G1","H1","A2"};
		
		performMovementTest(expectedCoordsString, rook);
		performAttackTest(new String[] {"A3"}, rook);
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
		performMovementTest(expectedCoordsString, king);
		
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()} for the {@link main.pieces.Bishop} class.
	 */
	@Test
	public void testBishop() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Bishop bishop = new Bishop(p1);

		//first test
		b.setPiece(new ChessCoordinate("c3"), bishop);
		String[] expectedCoordsString = new String[]{
				"A1","B2","D4","E5","F6","G7","H8", // '/' diagonal
				"A5","B4","D2","E1" // '\' diagonal
		}; 
		performMovementTest(expectedCoordsString, bishop);
		//no enemies to attack
		performAttackTest(new String[] {}, bishop);
		
		//second test - black bishop at e5
		b.setPiece(new ChessCoordinate("e5"), new Bishop(p2));
		expectedCoordsString = new String[]{
				"A1","B2","D4","E5", // '/' diagonal
				"A5","B4","D2","E1" // '\' diagonal
		}; 
		performAttackTest(new String[] {"e5"}, bishop); //white bishop can take on e5
		performAttackTest(new String[] {"c3"}, b.at(new ChessCoordinate("e5"))); //black bishop can take on c3
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
		performMovementTest(expectedCoordsString, pawn);
		
		//second test, pawn moves to c4, can only move to c5
		try {
			pawn.move(new ChessCoordinate("c4"));
		} catch (InvalidMoveException e) {
			System.out.println(e);
		}
		expectedCoordsString = new String[]{
				"C5"
		};
		performMovementTest(expectedCoordsString, pawn);
		//third test, pawn has no moves, blocked by black pawn
		b.setPiece(new ChessCoordinate("c5"), new Pawn(p2));
		performMovementTest(new String[] {}, pawn);

		//fourth test, new black pawn at b5, white pawn can take
		b.setPiece(new ChessCoordinate("b5"), new Pawn(p2));
		performMovementTest(new String[] {}, pawn);
		performAttackTest(new String[] {"b5"}, pawn);
	}
	
	/**
	 * Test method for {@link main.ChessPiece#getPossibleMoves()} for the {@link main.pieces.Knight} class.
	 */
	@Test
	public void testKnight() {
		//first test
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Knight knight = new Knight(p1);

		b.setPiece(new ChessCoordinate("c4"), knight);
		String[] expectedCoordsString = new String[]{
				"b2","a3", //bottom left
				"a5","b6", //top left
				"d6","e5", //top right
				"e3","d2" //bottom right
				
		};
		performMovementTest(expectedCoordsString, knight);
		
		//second test
		//knight can take pawn on b2, remove b2 from expected moves to expected attack
		b.setPiece(new ChessCoordinate("b2"), new Pawn(p2));
		expectedCoordsString = new String[]{
				"a3", //bottom left
				"a5","b6", //top left
				"d6","e5", //top right
				"e3","d2" //bottom right
		};
		performAttackTest(new String[] {"b2"}, knight);
		performMovementTest(expectedCoordsString, knight);
		
		//third test, knight moves to a3
		try {
			knight.move(new ChessCoordinate("a3"));
		} catch (InvalidMoveException e) {
			System.out.println(e);
		}
		expectedCoordsString = new String[]{
				"b5","c4", //top right
				"b1","c2" //bottom right
		};		
		performMovementTest(expectedCoordsString, knight);
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
							
		performMovementTest(expectedCoordsString, queen);
	}
	/**
	 * Test method for {@link main.ChessPiece#captureConsequence()}
	 */
	@Test
	public void testCapturing() {
		//queen at b2 takes pawn at b3
		//check if these conditions are met as stated in junit comment.
//		1. Add to this piece's owner's captured list. 
//		2. Remove from the captured piece's owner's piece list. 
//		3. Set the captured piece's position to null. 

		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Queen queen = new Queen(p1);
		Piece blackPawn = new Pawn(p2);
		b.setPiece(new ChessCoordinate("b2"), queen);
		b.setPiece(new ChessCoordinate("b3"), blackPawn);
		
		//before capture
		//condition 1 - captured list is empty
		assertEquals(Arrays.asList(new Piece[] {}) , p1.getPiecesTaken());		
		//condition 2 - p2 has black pawn unit
		assertEquals(Arrays.asList(new Piece[] {blackPawn}) , p2.getMyPieces());		
		//condition 3 - blackPawn has a position at b3
		assertEquals(new ChessCoordinate("b3"), blackPawn.getPosition());	
		
		//make the capture
		try {
			queen.move(new ChessCoordinate("b3"));
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
		
		//after capture
		//condition 1
		assertEquals(Arrays.asList(new Piece[] {blackPawn}) , p1.getPiecesTaken());		
		//condition 2 - p2 has no units left
		assertEquals(Arrays.asList(new Piece[] {}) , p2.getMyPieces());		
		//condition 3 
		assertEquals(null, blackPawn.getPosition());		
	}
	
	/**
	 * Test method for {@link main.pieces.king#inCheck()}
	 */
	@Test
	public void testCheck() {
		ChessBoard b = new ChessBoard(Layout.EMPTY,players);
		Queen queen = new Queen(p1);
		King blackKing = new King(p2);
		//first test
		//initially not in check, queen is not attacking black king
		b.setPiece(new ChessCoordinate("a1"), queen);
		b.setPiece(new ChessCoordinate("b8"), blackKing);
		assertFalse(blackKing.inCheck());
		
		//second test, queen moves to b2, black king should be in check
		try {
			queen.move(new ChessCoordinate("b3"));
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
		assertTrue(blackKing.inCheck());
	}
}





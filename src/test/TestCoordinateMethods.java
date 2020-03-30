package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.ChessCoordinate;

public class TestCoordinateMethods {

	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTopLeft() {
		//top left
		ChessCoordinate c = new ChessCoordinate("A8");
		int[] ce = {0,0};
		assertArrayEquals(ce, c.getIndexes());
	}
	@Test
	public void testBottomLeft() {
		ChessCoordinate c2 = new ChessCoordinate("A1");
		int[] ce2 = {7,0};
		assertArrayEquals(ce2, c2.getIndexes());
	}
	
	@Test
	public void testBottomRight() {
		ChessCoordinate c3 = new ChessCoordinate("H1");
		int[] ce3 = {7,7};
		assertArrayEquals(ce3, c3.getIndexes());
	}
	
	@Test
	public void testTopRight() {
		ChessCoordinate c4 = new ChessCoordinate("H8");
		int[] ce4 = {0,7};
		assertArrayEquals(ce4, c4.getIndexes());
	}

	

}

package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FIBATest {
	private FIBAManager fiba;

	public void setupScenary1() throws IOException{
		fiba = new FIBAManager();
		
		fiba.addPlayer("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
		fiba.addPlayer("Samuel", 33, "Col", 100, 300, 200, 90, 123);
		fiba.addPlayer("Charles", 29, "Arg", 207, 500, 254, 70, 213);
		fiba.addPlayer("Aiden", 36, "Col", 105, 330, 120, 88, 103);
		fiba.addPlayer("Cobe", 28, "Mex", 208, 200, 204, 85, 216);
	}
	
	
	@Test
	public void testAddPlayer() throws IOException {
		setupScenary1();
		
		boolean added = fiba.addPlayer("Link", 37, "Mex", 313, 507, 230, 88, 162);

		assertTrue(added);
	}
	
	@Test
	public void testSearchPlayersByName() throws IOException {
		setupScenary1();
		fiba.addPlayer("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
		fiba.addPlayer("Samuel", 33, "Col", 100, 300, 200, 90, 123);
		fiba.addPlayer("Charles", 29, "Arg", 207, 500, 254, 70, 213);
		fiba.addPlayer("Aiden", 36, "Col", 105, 330, 120, 88, 103);
		fiba.addPlayer("Cobe", 28, "Mex", 208, 200, 204, 85, 216);
		
		Player player = new Player("Link", 37, "Mex", 313, 507, 230, 88, 162);
		fiba.addPlayer("Link", 37, "Mex", 313, 507, 230, 88, 162);

		assertEquals(player, fiba.searchPlayersByName("Link"));
	
	}
	
}

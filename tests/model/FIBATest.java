package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

		fiba.addPlayer("Link", 37, "Mex", 313, 507, 230, 88, 162);

		List<Player> list = fiba.searchPlayersByName("Link");
		for(Player p : list) {
			assertTrue(p.getName().contains("Link"));
		}

	}

	@Test
	public void testSearchPlayersByValue() throws IOException {
		setupScenary1();

		fiba.addPlayer("Link", 37, "Mex", 313, 507, 230, 88, 162);

		List<Player> list = fiba.searchPlayersByValue(Criteria.POINTS, 313);
		for(Player p : list) {
			assertEquals("Link",p.getName());
		}

	}
	
	@Test
	public void testChangePlayer() throws IOException {
		setupScenary1();
		Player old = new Player("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
		fiba.addPlayer("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
		fiba.addPlayer("Samuel", 33, "Col", 100, 300, 200, 90, 123);
		fiba.addPlayer("Charles", 29, "Arg", 207, 500, 254, 70, 213);
		fiba.addPlayer("Aiden", 36, "Col", 105, 330, 120, 88, 103);
		fiba.addPlayer("Cobe", 28, "Mex", 208, 200, 204, 85, 216);

		assertTrue(fiba.changePlayer(old, "Lanita", 25, "Guam", 313, 507, 230, 88, 162));

	}
	
	@Test
	/*public void testDeletePlayer() throws IOException {
		setupScenary1();
		
		fiba.addPlayer("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
		Player p1 = new Player("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
		fiba.addPlayer("Samuel", 33, "Col", 100, 300, 200, 90, 123);
		Player p2 = new Player("Samuel", 33, "Col", 100, 300, 200, 90, 123);
		
		assertTrue(fiba.deletePlayer(p1.getKey()));*/

	}

}

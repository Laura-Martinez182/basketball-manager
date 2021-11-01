package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlayerTest {

	private Player player;
	
	public void setupScenary1() {
		player = new Player("Lanita", 25, "Guam", 213, 847, 100, 78, 352);
	}
	
	
	@Test
	public void testGetName() {
		setupScenary1();
		assertEquals("Lanita", player.getName());
	}
	
	@Test
	public void testGetAge() {
		setupScenary1();
		assertEquals(25, player.getAge());
	}
	
	@Test
	public void testGetTeam() {
		setupScenary1();
		assertEquals("Guam", player.getTeam());
	}
	
	@Test
	public void testGetPoints() {
		setupScenary1();
		assertEquals(213, player.getPoints());
	}
	
	@Test
	public void testGetRebounds() {
		setupScenary1();
		assertEquals(847, player.getRebounds());
	}
	
	@Test
	public void testGetAssists() {
		setupScenary1();
		assertEquals(100, player.getAssists());
	}
	
	@Test
	public void testGetSteals() {
		setupScenary1();
		assertEquals(78, player.getSteals());
	}
	
	@Test
	public void testGetBlocks() {
		setupScenary1();
		assertEquals(352, player.getBlocks());
	}
	
	@Test
	public void testSetName() {
		setupScenary1();
		String newName = "Frank";
		player.setName(newName);
		assertEquals(newName, player.getName());
	}
	
	@Test
	public void testSetAge() {
		setupScenary1();
		int newAge = 26;
		player.setAge(newAge);
		assertEquals(newAge, player.getAge());
	}
	
	@Test
	public void testsetTeam() {
		setupScenary1();
		String newTeam = "Arg";
		player.setTeam(newTeam);
		assertEquals(newTeam, player.getTeam());
	}
	
	@Test
	public void testsetPoints() {
		setupScenary1();
		Double newPoints = 111.0;
		player.setPoints(newPoints);
		assertEquals(newPoints, player.getPoints());
	}
	
	@Test
	public void testsetRebounds() {
		setupScenary1();
		Double newRebounds = 234.0;
		player.setRebounds(newRebounds);
		assertEquals(newRebounds, player.getRebounds());
	}
	
	@Test
	public void testsetAssists() {
		setupScenary1();
		Double newAssists = 294.0;
		player.setAssists(newAssists);
		assertEquals(newAssists, player.getAssists());
	}
	
	@Test
	public void testsetSteals() {
		setupScenary1();
		Double newSteals = 114.0;
		player.setSteals(newSteals);
		assertEquals(newSteals, player.getSteals());
	}
	
	@Test
	public void testsetBlocks() {
		setupScenary1();
		Double newBlocks = 14.0;
		player.setBlocks(newBlocks);
		assertEquals(newBlocks, player.getBlocks());
	}
	
}
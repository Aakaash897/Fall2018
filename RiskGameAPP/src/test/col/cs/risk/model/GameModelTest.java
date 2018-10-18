package test.col.cs.risk.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import col.cs.risk.model.ContinentModel;
import col.cs.risk.model.GameModel;
import col.cs.risk.model.PlayerModel;
import col.cs.risk.model.TerritoryModel;

/**
 * Test cases for Game Model
 * 
 * @author Team
 *
 */
public class GameModelTest {

	// Game Model instance
	GameModel gameModel;

	// Map String to test
	StringBuilder mapString;

	/**
	 * Initialization before every test case
	 */
	@Before
	public void before() {
		gameModel = new GameModel();

		mapString = new StringBuilder();
		mapString.append("[Map]\nauthor=Sean O'Connor\n");
		mapString.append("[Continents]\nNorth America=5\n");
		mapString.append("South America=2\n");
		mapString.append("[Territories]\nAlaska,70,126,North America,Northwest Territory\n");
		mapString.append("Northwest Territory,148,127,North America,Alaska\n");
		mapString.append("Venezuala,259,303,South America,Alaska\n");
	}

	/**
	 * DeInitialization after every test case
	 */
	@After
	public void after() {
		gameModel = null;
		mapString = null;
	}

	/**
	 * Test case to check whether all tags are correct in map file
	 */
	@Test
	public void testisTagsCorrect() {
		String invalidMapString = (mapString.toString()).replace("Territories", "Hi");

		assertTrue(gameModel.isTagsCorrect(mapString.toString()));
		assertFalse(gameModel.isTagsCorrect(invalidMapString));
	}

	/**
	 * Test case to check whether all territories are connected in a map file
	 */
	@Test
	public void testisAllTerritoriesConnected() {
		String invalidMapString = (mapString.toString()).substring(0, mapString.length() - 7);

		assertTrue(gameModel.isAllTerritoriesConnected(mapString.toString()));
		assertFalse(gameModel.isAllTerritoriesConnected(invalidMapString));
	}

	/**
	 * Test case to check that whether all countries belong to the defined set of
	 * continents in map file
	 */
	@Test
	public void testcheckContinentsAreValid() {
		String invalidMapString = (mapString.toString()).replaceFirst("South America", "Hi");

		assertTrue(gameModel.checkContinentsAreValid(mapString.toString()));
		assertFalse(gameModel.checkContinentsAreValid(invalidMapString));
	}

	/**
	 * Test case to check that whether all countries belong to the defined set of
	 * continents in map file
	 */
	@Test
	public void testreadFile() {
		String fileName = "currMap.map";
		mapString = gameModel.readFile(mapString, fileName);

		assertTrue(mapString.length() > 0 ? true : false);
	}

	/**
	 * Test case to check that whether player is added
	 */
	@Test
	public void testaddPlayer() {
		int size = GameModel.getPlayers().size();
		gameModel.addPlayer(101, "playerName");
		// size increase by one
		assertTrue(GameModel.getPlayers().size() == (size + 1) ? true : false);
		assertFalse(GameModel.getPlayers().size() <= size ? true : false);
	}

	/**
	 * Test case to test if the armies have been moved
	 */
	@Test
	public void testmoveArmies() {
		TerritoryModel model1 = new TerritoryModel(201, "tname1", 10, 20, new ContinentModel(301, "cname1", 2));
		model1.setArmies(5);
		TerritoryModel model2 = new TerritoryModel(202, "tname2", 30, 40, new ContinentModel(302, "cname2", 3));
		model2.setArmies(5);
		Vector<TerritoryModel> vector = new Vector<>();
		vector.add(model1);
		vector.add(model2);
		gameModel.setTerritories(vector);
		gameModel.setMoveArmiesFromTerritory(model1);
		gameModel.setMoveArmiesToTerritory(model2);
		gameModel.setNoOfArmiesToMove(1);

		// moved armies successfully
		assertTrue(gameModel.moveArmies());
		assertEquals(4, model1.getArmies());
		assertEquals(6, model2.getArmies());

		// armies not moved
		gameModel.setNoOfArmiesToMove(8);
		assertFalse(gameModel.moveArmies());
		assertEquals(4, model1.getArmies());
		assertEquals(6, model2.getArmies());
	}

	/**
	 * Test case to test if the player changes turn wise
	 */

	@Test
	public void testNextPlayer() {
		PlayerModel playerModel = new PlayerModel(1, "name");
		gameModel.setCurrentPlayer(playerModel);
		Vector<PlayerModel> playerVector = new Vector<>();
		for (int i = 1; i < 5; i++) {
			PlayerModel tempPlayerModel = new PlayerModel(1, "name");
			playerVector.add(tempPlayerModel);

		}
		gameModel.setPlayers(playerVector);
		assertTrue(gameModel.getCurrentPlayer() == playerModel ? true : false);
	}
}

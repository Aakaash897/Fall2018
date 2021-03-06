package col.cs.risk.model;

import java.io.Serializable;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Continent Class Contains the getter setters for data of the continents. This
 * Class also contains some methods to verify the territory occupation,
 * addTerritoryetc.
 * 
 * @author Team25
 * 
 */
public class ContinentModel implements Serializable {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1L;

	/** continent id */
	private int id;

	/** continent name */
	private String name;

	/** score of the continent */
	private int score;

	/** territories belongs to this continent */
	private Vector<TerritoryModel> territories;

	/**
	 * Constructor with three parameters
	 * 
	 * @param id
	 *            It contains the Continent Identification Number
	 * @param name
	 *            It contains the Continent Name
	 * @param score
	 *            It contains the Score for Continent to allocate player upon
	 *            Conquer
	 */
	public ContinentModel(int id, String name, int score) {
		this.id = id;
		this.name = name;
		this.score = score;
	}

	/**
	 * Constructor with four parameters
	 * 
	 * @param id
	 *            It contains the Continent Identification Number
	 * @param name
	 *            It contains the Continent Name
	 * @param score
	 *            It contains the Score for Continent to allocate player upon
	 *            Conquer
	 * @param territories
	 *            It is a vector that holds the information of the territories
	 */
	public ContinentModel(int id, String name, int score, Vector<TerritoryModel> territories) {
		this(id, name, score);
		this.territories = territories;
	}

	/**
	 * Validates the continent occupied status
	 * 
	 * @param playerModel
	 *            It is an Reference of Player Model that holds Player's Details
	 * @return true if occupied else false
	 */
	public boolean isContinentOccupiedBy(PlayerModel playerModel) {
		boolean isOccupied = true;
		for (TerritoryModel territoryModel : territories) {
			if (territoryModel.getPlayerModel().getId() != playerModel.getId()) {
				isOccupied = false;
				break;
			}
		}
		return isOccupied;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the territories
	 */
	public Vector<TerritoryModel> getTerritories() {
		return territories;
	}

	/**
	 * @param territorries
	 *            the territories to set
	 */
	public void setTerritories(Vector<TerritoryModel> territorries) {
		this.territories = territorries;
	}

	/**
	 * Adds territory to the list of occupied territory
	 * 
	 * @param territory
	 *            add territory to the list
	 */
	public void addTerritory(TerritoryModel territory) {
		if (territories == null) {
			territories = new Vector<>();
		}
		territories.add(territory);
	}

	/**
	 * @return Continent details as string
	 */
	public String printContinent() {
		return "id = " + this.id + ", name = " + this.name + ", score = " + this.score + ", territories = "
				+ territories.stream().map(x -> x.getId()).collect(Collectors.toList());
	}

}

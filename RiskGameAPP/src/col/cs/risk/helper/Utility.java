package col.cs.risk.helper;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JOptionPane;

import col.cs.risk.model.Constants;

/**
 * This Utility class is a list of supporting APIs. This class contains all the
 * helping variables and methods that are used multiple times in the whole
 * project. These are all static methods.
 * 
 * @author Team25
 *
 */
public class Utility {

	/** Player and color combination */
	private static HashMap<Integer, Color> playerColor = new HashMap<>();

	/**
	 * Selected file path of the map
	 */
	public static String selectedMapFilePath;

	/**
	 * Map string having the whole map in the string format
	 */
	public static StringBuilder baseMapString;

	/** timer to close the window */
	public static boolean canShow = true;

	/** static block to initialize player color map */
	static {
		playerColor.put(0, Color.red);
		playerColor.put(1, Color.blue);
		playerColor.put(2, Color.green);
		playerColor.put(3, Color.orange);
		playerColor.put(4, Color.pink);
		playerColor.put(5, Color.darkGray);
	}

	/**
	 * Retrieves the path of the map file
	 * 
	 * @param filename
	 *            : Name of the map file
	 * @return String that has the complete path
	 */
	public static String getMapPath(String filename) {
		return "resources/risk/map/" + filename;
	}

	/**
	 * Returns the saved game Path
	 * 
	 * @return filename path
	 * 
	 */
	public static String getSaveGamePath(String filename) {
		return "resources/risk/games/" + filename;
	}

	/**
	 * Gets the path of the Application
	 * 
	 * @return path string
	 */
	public static String getApplicationPath() {
		return System.getProperty("user.dir");
	}

	/**
	 * Gets the path of the map files(text)
	 * 
	 * @return path string
	 */
	public static String getResouceMapPath() {
		StringBuilder path = new StringBuilder();
		path.append(getApplicationPath());
		path.append("/resources/risk/map");
		return path.toString();
	}

	/**
	 * Retrieves the image path
	 * 
	 * @param filename
	 *            of the image
	 * @return String having the full path of the image
	 */
	public static String getImagePath(String filename) {
		return "resources/risk/images/" + filename;
	}

	/**
	 * Retrieves the dice path
	 * 
	 * @param filename
	 *            of the dice
	 * @return String having the full path of the dice
	 */
	public static String getDicePath(String filename) {
		return "resources/risk/dice/" + filename;
	}

	/**
	 * Save path of the map file
	 * 
	 * @param mapFilePath
	 */
	public static void saveMapFilePath(String mapFilePath) {
		selectedMapFilePath = mapFilePath;
	}

	/**
	 * Current used map file
	 * 
	 * @return path string path of the current map file
	 */
	public static String getUsedMapFilePath() {
		return selectedMapFilePath;
	}

	/**
	 * Generates the random number includes 0 but not num
	 * 
	 * @param num
	 *            it must be >= 1
	 * @return returns the random number between 0 and num parameter
	 */
	public static int getRandomNumber(int num) {
		return new Random().nextInt(num);
	}

	/**
	 * Saving the modified map content
	 */
	public static void saveMapString() {
		baseMapString = new StringBuilder();
		File file = new File(selectedMapFilePath);
		BufferedReader buffReader;
		try {
			buffReader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = buffReader.readLine()) != null) {
				baseMapString.append(line + "\n");
			}
			buffReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write the content to file
	 * 
	 * @param fileName
	 *            name of the file to write
	 * @param result
	 *            String content to write
	 */
	public static void writeToFile(String fileName, String result) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Utility.getMapPath(fileName)));
			bufferedWriter.write(result);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Message pop up to show error message
	 * 
	 * @param errorMessage
	 *            : Contains the actual message to be shown
	 */
	public static void showMessagePopUp(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Message pop up to show error message
	 * 
	 * @param message
	 *            Contains the actual message to be shown
	 * @param title
	 */
	public static void showMessagePopUp(String message, String title) {
		if (canShow) {
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**
	 * to replace the part in message
	 * 
	 * @param message
	 *            : has message , to store the updated message and return it
	 * @param origin
	 *            : message part to be replaced
	 * @param replace
	 *            : new message part
	 * @returns updated message
	 */
	public static String replacePartInMessage(String message, String origin, String replace) {
		message = message.replace(origin, replace);
		return message;
	}

	/**
	 * Checks if is all territories are connected
	 * 
	 * @param result
	 * @returns true if connected
	 * @throws MapException
	 */
	public static boolean isConnectedMap(String result) throws MapException {
		try {
			InputStream is = new ByteArrayInputStream(result.getBytes());
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			HashMap<String, Integer> territoryNames = new HashMap<String, Integer>();

			// Add all territories to a list
			while ((line = reader.readLine()) != null) {
				if (line.equals("[Territories]")) {
					while ((line = reader.readLine()) != null) {
						if (!line.matches("")) {
							String[] str = line.split(",");
							territoryNames.put(str[0].trim(), 0);
						}
					}
					break;
				}
			}
			is = new ByteArrayInputStream(result.getBytes());
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				if (line.equals("[Territories]")) {
					while ((line = reader.readLine()) != null) {
						if (!line.matches("")) {
							String[] str = line.split(",");
							int adjacents = 0;
							for (int i = 4; i < str.length; i++) {
								boolean isValidTerritory = false;
								// Check each adjacent territory is present in the list of territories
								for (Entry<String, Integer> territory : territoryNames.entrySet()) {
									if (!str[0].trim().equalsIgnoreCase(str[i].trim())
											&& territory.getKey().equalsIgnoreCase(str[i].trim())) {
										isValidTerritory = true;
										adjacents++;
										break;
									}
								}
								if (!isValidTerritory) {
									throw new MapException(Constants.NOT_A_CONNECTED_MAP_MESSAGE + str[i]);
								}
							}
							territoryNames.put(str[0].trim(), adjacents);
						}
					}
					break;
				}
			}
			for (Entry<String, Integer> territory : territoryNames.entrySet()) {
				if (territory.getValue() == 0) {
					throw new MapException(Constants.NOT_A_CONNECTED_MAP_MESSAGE + territory.getKey());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Checks for complete connection of map
	 * @param result
	 * @return true if the connection of complete map exists 
	 * @throws MapException
	 */
	public boolean isCompleteConnectedMap(String result) throws MapException {
		try {
			InputStream is = new ByteArrayInputStream(result.getBytes());
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			HashMap<String, HashSet<String>> territoryNames = new HashMap<String, HashSet<String>>();

			// Add all territories to a list
			while ((line = reader.readLine()) != null) {
				if (line.equals("[Territories]")) {
					while ((line = reader.readLine()) != null) {
						if (!line.matches("")) {
							String[] str = line.split(",");
							territoryNames.put(str[0].trim(), new HashSet<>());
						}
					}
					break;
				}
			}
			is = new ByteArrayInputStream(result.getBytes());
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				if (line.equals("[Territories]")) {
					while ((line = reader.readLine()) != null) {
						if (!line.matches("")) {
							String[] str = line.split(",");
							HashSet<String> adjacentTerritories = territoryNames.get(str[0].trim());
							for (int i = 4; i < str.length; i++) {
								boolean isValidTerritory = false;
								// Check each adjacent territory is present in the list of territories
								for (Entry<String, HashSet<String>> territory : territoryNames.entrySet()) {
									if (!str[0].trim().equalsIgnoreCase(str[i].trim())
											&& territory.getKey().equalsIgnoreCase(str[i].trim())) {
										isValidTerritory = true;
										adjacentTerritories.add(territory.getKey());
										break;
									}
								}
								if (!isValidTerritory) {
									throw new MapException(Constants.NOT_A_CONNECTED_MAP_MESSAGE + str[i]);
								}
							}
							territoryNames.put(str[0].trim(), adjacentTerritories);
						}
					}
					break;
				}
			}
			for (Entry<String, HashSet<String>> territory : territoryNames.entrySet()) {
				if (territory.getValue().size() == 0) {
					throw new MapException(Constants.NOT_A_CONNECTED_MAP_MESSAGE + territory.getKey());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * return the player color according to player id
	 * 
	 * @return playerColor
	 */

	public static Color getColor(int id) {
		return playerColor.get(id);
	}

	/**
	 * Write log.
	 *
	 * @param text
	 *            the text
	 * @param isApplicationStart
	 *            the is application start
	 */
	public static void writeLog(String text, boolean... append) {
		boolean isAppend = true;
		if (append != null && append.length > 0) {
			isAppend = append[0];
		}
		try (FileWriter fw = new FileWriter("resources/risk/log/log.txt", isAppend); // append contents to file
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(text);
			out.println();
			out.close();
			bw.close();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Show error message pop up
	 * @param nO_SAVED_GAME_FOUND
	 * @return
	 */
	public static int showErrorMessagePopUp(String nO_SAVED_GAME_FOUND) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, nO_SAVED_GAME_FOUND, "Error", JOptionPane.ERROR_MESSAGE);
		return 1;
	}
}

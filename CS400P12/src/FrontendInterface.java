import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface defines functions that are needed in frontend development and integration.
 *
 * @author Chengtao Dai && Yuanhui Long && Ruize(Noah) Wang && Yuting Du && Sizhe Chen && Jinyu Shi
 */
public interface FrontendInterface {

  /**
   * This method interacts with users and encourage them to choose a function that they want.
   */
  public void runCommandLoop() throws IOException;

  /**
   * Displays the main menu and handles user input to navigate through various commands.
   */
  public void displayMainMenu() throws IOException;

  /**
   * This method loads data from the input file based on the fileName provided by the user.
   * @param fileName - the name of the file to be used
   * @return true if data successfully loaded otherwise false
   * @throws IOException If there is an error reading the file.
   */
  public boolean loadDataFromFile(String fileName) throws IOException;

  /**
   * This method lists songs as well as their corresponding danceability scores.
   * @param data - arraylist that store the data collected from the file.
   * @param danceability - the danceability score to be
   */
  public void listSongsByDanceability(ArrayList<Song> data, int danceability);

  /**
   * This method calculates the average level of music danceablity.
   * @param danceabilityScores - the danceablity scores of the music stored in the dataset
   * @return the average level of music danceablity
   */
  public double averageDanceability(ArrayList<Song> danceabilityScores);

  /**
   * This method tests if data from the input file is collected successfully.
   * @param fileName - the file from which data is to be collected.
   * @return true if the input is valid, false otherwise.
   */
  public boolean isInputValid(String fileName);

  /**
   * This method exits the app.
   */
  public void exitApp();
  
}

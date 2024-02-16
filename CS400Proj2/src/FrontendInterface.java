// --== CS400 Fall 2023 File Header Information ==--
// Name: Zachary Chariton
// Email: zchariton@wisc.edu
// Group: C40
// TA: Robert Nagel
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>


/**
 * This is the Interface for the Frontend
 */
public interface FrontendInterface {

  /**
   *  This is a placeholder constructor that will accept a reference to the backend and a scanner
   * @param backend This is a reference to the backend object that the frontend will call with its methods.
   * @param scnr Scanner for reading user input
   */
  //public IndividualFrontendInterface(Backend backend, java.util.Scanner scnr);

  /**
   * This method starts the program and runs the loop that interacts with the user.
   */
  public void mainMenu();

  /**
   * This method takes a file path as a string and calls the backend to load the data file into the graph
   * Returns true if the file is successfully loaded, false otherwise.
   */
  public boolean loadDataFile(String filePath);

  /**
   * This method prints out the total number of airports, flights, and miles for the dataset by calling the backend.
   */
  public void showStatistics();

  /**
   * This method prints out the shortest route between two airports including all airports along the way, the distance
   * for each segment, and the total number of miles from start to destination airport.
   *
   * @param startCode The three-letter IATA code designating the starting airport
   * @param destCode  The three-letter IATA code designation the starting airport
   */
  public void shortestRoute(String startCode, String destCode);

  /**
   * This method exits the app. It ensures the scanner is also closed.
   */
  public void exitApp();

}

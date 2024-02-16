// --== CS400 Fall 2023 File Header Information ==--
// Name: Zachary Chariton
// Email: zchariton@wisc.edu
// Group: C40
// TA: Robert Nagel
// Lecturer: Florian Heimerl
// Notes to Grader: source: https://www.geeksforgeeks.org/split-string-java-examples/#


import java.util.Scanner;

/**
 * This is the main class for the flight router frontend
 */
public class Frontend implements FrontendInterface {

  private BackendInterface backend;
  private Scanner scnr;

  /**
   * This is a placeholder constructor that will accept a reference to the backend and a scanner
   *
   * @param backend This is a reference to the backend object that the frontend will call with its methods.
   * @param scnr    Scanner for reading user input
   */
  public Frontend(BackendInterface backend, java.util.Scanner scnr) {
    this.backend = backend;
    this.scnr = scnr;
  }

  /**
   * This method starts the program and runs the loop that interacts with the user.
   */
  @Override
  public void mainMenu() {
    System.out.println("Welcome to the Flight Router App!\nPlease type in a filepath to begin or \"exit\" to leave the app");

    // Loop to load the file. The backend handles exceptions.
    boolean fileLoaded = false;
    while (!fileLoaded) {
      String input = scnr.nextLine().trim();
      if (input.toUpperCase().matches("EXIT")) {
        this.exitApp();
        return;
      }
      else if (loadDataFile(input)){
        fileLoaded = true;
        System.out.println("File loaded successfully!");
      }
      else System.out.println("Incorrect command or file name.\nPlease try again or type \"exit\" to leave the app");
    }

    // Loop that asks the user what they want to do with the data.
    boolean mainProgram = true;
    while (mainProgram) {
      System.out.println("\nPlease type a number corresponding to one of the following commands:\n" +
          "(1) List statistics about data file.\n" +
          "(2) Calculate the shortest route between two airports and list trip info.\n" +
          "(3) or \"exit\" to leave the app");

      String input = scnr.nextLine().toUpperCase().trim(); // user input
      if (!(input.matches("\\b[1-3]\\b") || input.matches("EXIT"))){
        System.out.println("Invalid command. Please enter number 1-3 or \"exit\".");
        continue;
      }
      // handling various commands
      switch (input){
        case "1":
          this.showStatistics();
          break;
        case "2":
          boolean correctInput = false;
          while (!correctInput) {
            System.out.println("Please type in a start and destination airport with a space in between and " +
                "press enter (ex. \"ORD OGG\") or type \"b\" to go back.");
            String routeInput = scnr.nextLine().toUpperCase(); // user input
            if (routeInput.matches("B")){
              break;
            }
            if (!routeInput.matches("[A-Z]{3} [A-Z]{3}") || routeInput.length() != 7) {
              System.out.println("Incorrect format.");
              continue;
            }
            String[] cityInput = routeInput.split("\\s", 2); // separate user input into list

            // test to make sure the three-digit codes are correct
            try {
              this.shortestRoute(cityInput[0], cityInput[1]); // if all passes up to here, call the method
            }
            catch (Exception e) {
              System.out.println("Invalid airport code");
              continue;
            }
            correctInput = true;
          }
          break;
        case "3", "EXIT":
          mainProgram = false;
          this.exitApp();
          break;
      }
    }
  }

  /**
   * This method takes a file path as a string and calls the backend to load the data file into the graph
   * Returns true if the file is successfully loaded, false otherwise.
   *
   * @param filePath
   */
  @Override
  public boolean loadDataFile(String filePath) {
    if (backend.readFlightDataFile(filePath)) {
      return true;
    }
    return false;
  }

  /**
   * This method prints out the total number of airports, flights, and miles for the dataset by calling the backend.
   */
  @Override
  public void showStatistics() {
    System.out.println(backend.getStatistics());
  }

  /**
   * This method prints out the shortest route between two airports including all airports along the way, the distance
   * for each segment, and the total number of miles from start to destination airport.
   *
   * @param startCode The three-letter IATA code designating the starting airport
   * @param destCode  The three-letter IATA code designation the starting airport
   */
  @Override
  public void shortestRoute(String startCode, String destCode) {
    ShortestRouteInterface shortestRoute = backend.getShortestRoute(startCode, destCode);
    System.out.println("-----------------------------");
    for (int i = 0; i < shortestRoute.getRoute().size() - 1; i++) {
      System.out.println(shortestRoute.getRoute().get(i) + " to " + shortestRoute.getRoute().get(i + 1)
          + " | Miles: " + shortestRoute.getMilesPerSegment().get(i));
    }
    System.out.println("-----------------------------");
    System.out.println("Total Miles from " + startCode + " to " + destCode + ": " + shortestRoute.getTotalMiles());
  }

  /**
   * This method exits the app. It ensures the scanner is also closed.
   */
  @Override
  public void exitApp() {
    System.out.println("Goodbye!");
    scnr.close();
    // System.exit(0); // this command seems to make the textUI tester class function incorrectly
  }

//  /**
//   * This is the main method that starts the command loop for the user
//   *
//   * @param args unused
//   */
//  public static void main(String[] args) {
//    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
//    BackendInterface backend = new Backend(graph);
//    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));
//    frontend.mainMenu();
//
//  }
}

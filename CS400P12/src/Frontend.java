// --== CS400 Fall 2023 File Header Information ==--
// Name: Chengtao Dai
// Email: cdai53@wisc.edu
// Group: C14
// TA: Matthew Schwennesen
// Lecturer: Florian Heimerl
// Notes to Grader: -

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * This class provides the minimum functionality needed to develop tests for FrontendDeveloperTests
 * and to help those tests pass.
 */
public class Frontend implements FrontendInterface{

  private Scanner userInput;
  private BackendInterface backendInterface;
  private Backend backend;
  private ArrayList<Song> danceabilityScores = new ArrayList<>();

  public Frontend(BackendInterface backend, Scanner userInput) {
    backendInterface = backend;
    this.userInput = userInput;
  }

  public Frontend(BackendInterface backend) {
    backendInterface = backend;
  }
  public Frontend(Backend backend) {
    this.backend = backend;
  }

  @Override
  public void runCommandLoop() throws IOException {
    userInput = new Scanner(System.in);
    System.out.println("Welcome to Badger Music!");
    boolean dataLoaded = false;
    String choice = "";

    while (!choice.equals("4")) {
      displayMainMenu();

      while(!userInput.hasNext()){
        System.out.println("Invalid command. Please enter a valid command (1/2/3/4).");
        userInput.next();
        userInput.nextLine();
      }
      choice = userInput.next();
      userInput.nextLine();

      /*
      while(userInput == null || !userInput.hasNextInt()){
        System.out.println("Invalid command. Please enter a valid command (1/2/3/4).");
        userInput.next();
      }

       */

      //userInput.nextLine(); // Consume newline

      // a command to specify and load a data file
      if (choice.equals("1")) {
        System.out.println("Enter the data file name: ");
        //String fileName = "test";

        String fileName = userInput.nextLine().trim();

        // Load data from the file
        dataLoaded = loadDataFromFile(fileName); // Possible IO exception
      } else if(choice.equals("2")){ // a command to list all songs that have a specific
        // danceability score
        if (!dataLoaded) { //first check if the dataset is successfully loaded
          System.out.println("Error: Data not loaded. Please load a data file first.");
        } else {
          System.out.print("Enter the danceability score: ");
          // invalid number is entered? eg not integer
          while(!userInput.hasNextInt()){
            System.out.println("Please enter an integer.");
            userInput.nextLine();
          }
          int danceability = userInput.nextInt();
          // maybe a userInput.nextLine() is needed here
          userInput.nextLine();
          listSongsByDanceability(danceabilityScores, danceability);
          // List songs with the specified danceability score
          // System.out for strings
          // Example: listSongsByDanceability(data, danceability);
        }
      } else if (choice.equals("3")) {// a command that shows the average danceablity score in
        // the loaded
        // dataset
        if (!dataLoaded) {
          System.out.println("Error: Data not loaded. Please load a data file first.");
        } else {
          double average = averageDanceability(danceabilityScores);
          System.out.println("Average Danceability Score: " + average);
        }
      } else if (choice.equals("4")) { // a command to exit the app
        exitApp();
        // System.out.println("Exiting the app. Goodbye!");
        userInput.close();
        return;
        //System.exit(0);
      } else {
        System.out.println("Invalid command. Please enter a valid command (1/2/3/4).");
      }

    }
  }

  @Override
  public void displayMainMenu(){
    System.out.println("Please choose one of the commands:");
    System.out.println("1. Load Data");
    System.out.println("2. List Songs by Danceability Score");
    System.out.println("3. Show Average Danceability Score");
    System.out.println("4. Exit");
    System.out.print("Enter the command (1/2/3/4): ");
  }

  /**
   * This method loads data from the input file based on the fileName provided by the user.
   * @param fileName - the name of the file to be used
   * @return true if data successfully loaded otherwise false
   * @throws IOException If there is an error reading the file.
   */
  @Override
  public boolean loadDataFromFile(String fileName) {
    if (this.backend != null) {
      try {
        if (this.backend.readFile(fileName)) {
          this.danceabilityScores = this.backend.getSongList();
          System.out.println("Load complete");
          return true;
        } else {
          //1System.out.println("File not found.");
          return false;
        }
      } catch (IOException e) {
        System.out.println("File not found. Please enter a CSV file.");
      }
    } else {
      // you should first instantiate a backend
      System.out.println("a valid backend expected");
      return false;
    }
    return false;
    // type to boolean so that we can determine if the data loading process is successful.
  }

  /**
   * This method lists songs as well as their corresponding danceability scores.
   * @param data - arraylist that store the data collected from the file.
   * @param danceability - the danceability score to be
   */
  @Override
  public void listSongsByDanceability(ArrayList<Song> data, int danceability) {
    ArrayList<Song> list = this.backend.getSongsAboveThreshold(danceability);
    if (list != null || !list.isEmpty()) {
      for (Song song : list) {
        System.out.println(song.toString());
      }
    } else {
      System.out.println("Don't exist a song whose danceability score is above " + danceability);
    }
  }

  /**
   * This method calculates the average level of music danceablity.
   * @param danceabilityScores - the danceablity scores of the music stored in the dataset
   * @return the average level of music danceablity
   */
  @Override
  public double averageDanceability(ArrayList<Song> danceabilityScores) {
    double averageScore = this.backend.getAverageDanceability();
    return averageScore;
  }

  @Override
  public boolean isInputValid(String fileName) {
    return false;
  }

  /**
   * This method exits the app.
   */
  @Override
  public void exitApp() {
    System.out.println("Exiting the app. Goodbye!");
  }

  /**
   * used for test
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
//    //TextUITester tester = new TextUITester("4\n");
//    //String output = tester.checkOutput();
//    //System.out.println(output);
//
//
//    Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
//    myProject.runCommandLoop();
//    //String output = tester.checkOutput();
      Frontend myProject = new Frontend(new Backend(new IterableMultiKeyRBT<>()));
      myProject.runCommandLoop();
//
//    //System.out.println(output);
//
//
  }
}

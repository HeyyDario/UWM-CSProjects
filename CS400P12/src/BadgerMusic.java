import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class BadgerMusic {

  /**
   * This method loads data from the input file based on the fileName provided by the user.
   * @param fileName - the name of the file to be used
   * @return true if data successfully loaded otherwise false
   */
  private static boolean loadDataFromFile(String fileName){
    ArrayList<String> data = new ArrayList<>();

    try {
      File file = new File(fileName);
      Scanner input = new Scanner(new FileInputStream(file));

      // Read data line by line from the file
      while (input.hasNextLine()) {
        String line = input.nextLine();
        data.add(line);
      }

      input.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: File not found.");
      return false;
    }

    // FIXME HERE how to store the data collected from the file?
    return true;
  }

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    boolean dataLoaded = false;
    ArrayList<Double> danceabilityScores = new ArrayList<>();

    while (true) { // FIXME HERE find a way to stop the loop ie exit
      System.out.println("Please choose one of the commands:");
      System.out.println("1. Load Data");
      System.out.println("2. List Songs by Danceability Score");
      System.out.println("3. Show Average Danceability Score");
      System.out.println("4. Exit");
      System.out.print("Enter the command (1/2/3/4): ");

      while(!input.hasNextInt()){
        System.out.println("Invalid command. Please enter a valid command (1/2/3/4).");
        input.next();
      }

      int choice = input.nextInt();
      input.nextLine(); // Consume newline

      // a command to specify and load a data file
      if (choice == 1) {
        System.out.print("Enter the data file name: ");
        String fileName = input.nextLine();
        // Load data from the file
        dataLoaded = loadDataFromFile(fileName);
        System.out.println("Data loaded successfully.");
      } else if(choice == 2){ // a command to list all songs that have a specific danceability score
        if (!dataLoaded) { //first check if the dataset is successfully loaded
          System.out.println("Error: Data not loaded. Please load a data file first.");
        } else {
          System.out.print("Enter the danceability score: ");
          double danceability = input.nextDouble();
          // List songs with the specified danceability score
          // System.out for strings
          // Example: listSongsByDanceability(data, danceability);
        }
      } else if (choice == 3) {// a command that shows the average danceablity score in the loaded dataset
        if (!dataLoaded) {
          System.out.println("Error: Data not loaded. Please load a data file first.");
        } else {
          double average = averageDanceability(danceabilityScores);
          System.out.println("Average Danceability Score: " + average);
        }
      } else if (choice == 4) { // a command to exit the app
        System.out.println("Exiting the app. Goodbye!");
        input.close();
        System.exit(0);
      } else {
        System.out.println("Invalid command. Please enter a valid command (1/2/3/4).");
      }

    }
  }

  /**
   * This method calculates the average level of music danceablity.
   * @param danceabilityScores - the danceablity scores of the music stored in the dataset
   * @return the average level of music danceablity
   */
  private static double averageDanceability(ArrayList<Double> danceabilityScores) {
    double sum = 0;
    for (Double element : danceabilityScores) {
      sum += element;
    }
    return sum / danceabilityScores.size();
  }
}

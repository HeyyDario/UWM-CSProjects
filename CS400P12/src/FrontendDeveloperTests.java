// --== CS400 Fall 2023 File Header Information ==--
// Name: Chengtao Dai
// Email: cdai53@wisc.edu
// Group: C14
// TA: Matthew Schwennesen
// Lecturer: Florian Heimerl
// Notes to Grader: -

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class tests if the command loop that is used to interact with user will run correctly.
 *
 * We mock users' choices in the following 4 scenarios: 1. if the choice is 1, then the program is
 * supposed to print out "Enter the data file name: " 2. if the choice is 2, then the program is
 * supposed to print "Enter the danceability score:" 3. if the choice is 3, then the program is
 * supposed to print "Average danceability score:" 4. if the choice is 4(exit), then the program is
 * supposed to print "Exiting the app. Goodbye!"
 */
public class FrontendDeveloperTests {

  /**
   * This method tests if the command loop that is used to interact with user will run correctly.
   *
   * This test includes one scenario: 1. if the input is invalid, say the user enters something like
   * "asdasdas", then "Invalid command. Please enter a valid command (1/2/3/4)." will be prompted.
   */
  @Test
  public void testRunCommandLoop() throws IOException {
    // Scenario1: invalid type
    TextUITester tester = new TextUITester("asd\n4\n");
    Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
    myProject.runCommandLoop();
    // myProject.launch();

    String output = tester.checkOutput();
    if (output.contains("Please choose one of the commands:") && output.contains(
        "Invalid command. Please enter a valid command (1/2/3/4)."))
      Assertions.assertTrue(true);
    else
      Assertions.fail("cannot form a loop if type is invalid");
  }

  /**
   * This method tests if the main menu is correct.
   */
  @Test
  public void testDisplayMainMenu() throws IOException {
    // how to simulate that nothing is typed?
    TextUITester tester = new TextUITester("6\n4\n");

    Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
    myProject.runCommandLoop();

    String output = tester.checkOutput();
    if (output.contains("Please choose one of the commands:"))
      Assertions.assertTrue(true);
    else
      Assertions.fail("cannot display main manu");
  }

  /**
   * This method tests if the prompt for loading data is correct.
   *
   * Three scenarios are considered: 1. non-existing file name is entered. 2. an existing file name
   * is entered
   */
  @Test
  public void testLoadDataFromFile() throws IOException {
    // Since we still don't know what the readFile() does, we only test that there is an existing
    // file name
    {
      TextUITester tester1 = new TextUITester("1\n4\n4\n");
      Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
      myProject.runCommandLoop();

      // not contain any file
      String output1 = tester1.checkOutput();
      if (output1.contains("Please choose one of the commands:") && output1.contains(
          "Enter the data file name: ") && output1.contains("a valid backend expected"))
        Assertions.assertTrue(true);
      else
        Assertions.fail("cannot enter file name when choice is 1");
    }

    // try to load an existing file
    {
      TextUITester tester2 = new TextUITester("1\nasdhas\n4\n");
      Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
      myProject.runCommandLoop();

      String output2 = tester2.checkOutput();
      if (output2.contains("Please choose one of the commands:") && output2.contains(
          "Enter the " + "data file name: ") && output2.contains("a valid backend expected"))
        Assertions.assertTrue(true);
      else
        Assertions.fail("cannot load data when file name is correct");
    }

    // load a file that does not exist
  }

  /**
   * This method tests the prompt for listing song information based on the danceability score
   * entered.
   *
   * Two scenarios are included: 1. Based on the fact that data is loaded successfully (i.e. first
   * enter 1 and then 2), data is listed correctly. 2. If the user doesn't load data before listing
   * the song, then the program cannot list songs by danceability and will prompt the user to load
   * data first.
   */
  @Test
  public void testListSongsByDanceability() throws IOException {
    //ArrayList<String> testAL = new ArrayList<>();
    //    {
    //      TextUITester tester = new TextUITester("1\n2\n80\n4\n");
    //      Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
    //      myProject.runCommandLoop();
    //
    //      String output = tester.checkOutput();
    //      if (output.contains("Please choose one of the commands:") && output.contains(
    //          "Enter the " + "danceability score: ") && output.contains(
    //          "list some information based on danceability here."))
    //        Assertions.assertTrue(true);
    //      else
    //        Assertions.fail("cannot get song information based on the danceability score");
    //    }

    {
      TextUITester tester = new TextUITester("2\n4\n");
      Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
      myProject.runCommandLoop();

      String output = tester.checkOutput();
      if (output.contains("Please choose one of the commands:") && output.contains(
          "Error: Data not loaded. Please load a data file first."))
        Assertions.assertTrue(true);
      else
        Assertions.fail("cannot get song information based on the danceability score");
    }
  }

  /**
   * This method tests the prompt for the average danceability score.
   *
   * In this test, two scenarios are tests: 1. if the user doesn't load data first (i.e not entering
   * 1 before entering 3), then the program will prompt the user to first load data. 2. if the user
   * load data first, then the program will give the user the average danceability score, as
   * desired.
   */
  @Test
  public void testAverageDanceability() throws IOException {
    //ArrayList<String> testAL = new ArrayList<>();
    {
      TextUITester tester = new TextUITester("3\n4\n");
      Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
      myProject.runCommandLoop();
      String output = tester.checkOutput();
      if (output.contains("Please choose one of the commands:") && output.contains(
          "Error: Data not loaded. Please load a data file first."))
        Assertions.assertTrue(true);
      else
        Assertions.fail("cannot get average danceability score");
    }

    //    {
    //      TextUITester tester = new TextUITester("1\n3\n4\n");
    //      Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
    //      myProject.runCommandLoop();
    //      String output = tester.checkOutput();
    //      if (output.contains("Please choose one of the commands:") && output.contains(
    //          "Average Danceability Score: 0.0"))
    //        Assertions.assertTrue(true);
    //      else
    //        Assertions.fail("cannot get average danceability score");
    //    }
  }

  /**
   * This method tests the prompt for exiting.
   *
   * This test include just one scenario, which is entering 4 directly.
   */
  @Test
  public void testExitApp() throws IOException {
    TextUITester tester = new TextUITester("4\n");
    Frontend myProject = new Frontend(new BackendPlaceholderFrontend());
    myProject.runCommandLoop();
    String output = tester.checkOutput();
    if (output.contains("Please choose one of the commands:") && output.contains(
        "Exiting the app. Goodbye!"))
      Assertions.assertTrue(true);
    else
      Assertions.fail("cannot exit the app");
  }

  /**
   * This method tests if readFile() can be implemented after the backend instance is specified.
   */
  @Test
  public void testIntegrationReadFile() throws IOException {
    TextUITester tester = new TextUITester("1\ntestSongs.csv\n4\n");
    Frontend myProject = new Frontend(new Backend(new IterableMultiKeyRBT<>()));
    myProject.runCommandLoop();

    String output = tester.checkOutput();
    if (output.contains("Enter the data file name: ") && output.contains(
        "Load complete") && output.contains("Please choose one of the commands:"))
      Assertions.assertTrue(true);
    else
      Assertions.fail("cannot load file successfully when choice is 1");

  }

  /**
   * This method tests if listSongsByDanceability() can be implemented after the backend instance is
   * specified.
   *
   * This test contains 2 scenarios:
   * 1. test music search engine with a small size sample where there are only 3 songs whose
   * danceability scores are above 50, the threshold in this test case. Information related to
   * these three songs are expected to be listed in the console. If not, the test doesn't pass.
   * 2. test music search engine with a large size sample where there are only 3 songs whose
   * danceability scores are above 96, the threshold in this test case. Information related to
   * these three songs are expected to be listed in the console. If not, the test doesn't pass.
   * <p>
   * How to test each song's information? That would be more convincing.
   */
  @Test
  public void testIntegrationListSongsByDanceability() throws IOException {
    {
      TextUITester tester = new TextUITester("1\ntestSongs.csv\n2\n50\n4\n");
      Frontend myProject = new Frontend(new Backend(new IterableMultiKeyRBT<>()));
      myProject.runCommandLoop();
      String expected1 =
          "Song: \"Hey Mama (feat. Nicki Minaj, Bebe Rexha & Afrojack)\", Artist: \"David Guetta\"," + " genre: \"dance pop\", year:" + " 2015, " + "dnce: 60";
      String expected2 = "Song: \"Marry You\", Artist: \"Bruno Mars\", genre: \"pop\", year: 2010, " + "dnce: 62";
      String expected3 =
          "Song: \"Love The Way You Lie\", Artist: \"Eminem\", genre: \"detroit hip hop\", year: " + "2010, dnce: 75";

      String output = tester.checkOutput();
      if (output.contains(expected3) && output.contains(expected2) && output.contains(expected1))
        Assertions.assertTrue(true);
      else
        Assertions.fail("In integration scenario1: cannot get correct average danceability when " +
            "choice is 2");
    }

    {
      TextUITester tester = new TextUITester("1\nsongs.csv\n2\n96\n4\n");
      Frontend myProject = new Frontend(new Backend(new IterableMultiKeyRBT<>()));
      myProject.runCommandLoop();
      String expected1 = "Song: Anaconda, Artist: Nicki Minaj, genre: dance pop, year: 2014, " +
          "dnce: 96";
      String expected2= "Song: Bad Liar, Artist: Selena Gomez, genre: dance pop, year: 2018, dnce: 97";
      String expected3= "Song: Drip (feat. Migos), Artist: Cardi B, genre: pop, year: 2018, dnce: 97";
      String output = tester.checkOutput();
      if (output.contains(expected3) && output.contains(expected2) && output.contains(expected1))
        Assertions.assertTrue(true);
      else
        Assertions.fail("In integration scenario2: cannot get correct average danceability when " +
            "choice is 2");
    }
  }

  /**
   * This method tests if getAdverageDanceability() can be implemented after the backend instance is
   * specified.
   *
   * This test contains 2 scenarios:
   * 1. test with a small size sample that only has 3 songs in it. The expected average is 197
   * .0/3.0 . If not, the test doesn't pass.
   * 2. test with a large size sample that has 600 songs in it. The expected average is 38594.0 /
   * 600.0 . If not, the test doesn't pass.
   */
  @Test
  public void testIntegrationGetAverageDanceability() throws IOException {
    {
      TextUITester tester = new TextUITester("1\ntestSongs.csv\n3\n4\n");
      Frontend myProject = new Frontend(new Backend(new IterableMultiKeyRBT<>()));
      myProject.runCommandLoop();
      double expected = 228.0 / 4.0;

      String output = tester.checkOutput();
      if (output.contains("Enter the data file name: ") && output.contains("Load complete") && output.contains("Average Danceability Score: " + expected))
        Assertions.assertTrue(true);
      else
        Assertions.fail("In test scenario1: cannot get correct average danceability when choice " +
            "is 3");
    }

    {
      TextUITester tester = new TextUITester("1\nsongs.csv\n3\n4\n");
      Frontend myProject = new Frontend(new Backend(new IterableMultiKeyRBT<>()));
      myProject.runCommandLoop();
      double expected = 38594.0 / 600.0;

      String output = tester.checkOutput();
      if (output.contains("Enter the data file name: ") && output.contains("Load complete") && output.contains("Average Danceability Score: " + expected))
        Assertions.assertTrue(true);
      else
        Assertions.fail("In test scenario2: cannot get correct average danceability when choice " +
            "is 3");
    }
  }

  /**
   * This method is used to test if getSongsWithMinDanceability() by BackendDeveloper works
   * correctly. Note that this is a basic check.
   * @throws IOException
   */
  @Test
  public void testForBDGetSongsWithMinDanceability() throws IOException{
    Backend backend = new Backend(new IterableMultiKeyRBT());
    backend.readFile("./songs.csv");
    // set the expected song which has minimum danceability
    Song expected = new Song("Million Years Ago","Adele","british soul",2016,0);
    List<Song> minSong =  backend.getSongsWithMinDanceability();
    // compare the min dcb
    Assertions.assertEquals(minSong.get(0).toString(),expected.toString());
  }

  /**
   * This method is used to test if getAverageDanceability() by BackendDeveloper works
   * correctly. Note that this is a basic check.
   * @throws IOException
   */
  @Test
  public void testForBDgetAverageDanceability() throws IOException{
    Backend backend = new Backend(new IterableMultiKeyRBT());
    // read file to store 3 songs.
    backend.readFile("./songs.csv");
    // set expected average score.
    double expected = 38594.0 / 600.0;
    // compare the average danceability.
    Assertions.assertEquals(backend.getAverageDanceability(), expected);
  }

  // the following are from dahl's TextUITester


  /**
   * This class can be used to test text based user interactions by 1) specifying a String of text
   * input (that will be fed to System.in as if entered by the user), and then 2) capturing the
   * output printed to System.out and System.err in String form so that it can be compared to the
   * expect output.
   *
   * @author dahl
   * @date 2021.10
   */
  public class TextUITester {

    /**
     * This is the code being tested by the main method above. It 1) prints out a welcome message,
     * 2) reads a String, a double, and a character from System.in, and then 3) prints out the
     * string followed by a number that is one greater than that double, if the character that it
     * read in was a (lower case) 'q'.
     */
    public static void run() {
      // Note to avoid instantiating more than one Scanner reading from System.in in your code!
      Scanner in = new Scanner(System.in);
      System.out.println("Welcome to the run program.");
      System.out.println("Enter a string, a double, and then q to quit: ");
      String s = in.nextLine();
      double d = in.nextDouble();
      in.nextLine(); // read newline after double
      char c = in.nextLine().charAt(0);
      if (c == 'q')
        System.out.println(s + (d + 1.0));
      in.close();
    }

    // Below is the code that actually implements the redirection of System.in and System.out,
    // and you are welcome to ignore this code: focusing instead on how the constructor and
    // checkOutput() method is used int he example above.

    private PrintStream saveSystemOut; // store standard io references to restore after test
    private PrintStream saveSystemErr;
    private InputStream saveSystemIn;
    private ByteArrayOutputStream redirectedOut; // where output is written to durring the test
    private ByteArrayOutputStream redirectedErr;

    /**
     * Creates a new test object with the specified string of simulated user input text.
     *
     * @param programInput the String of text that you want to simulate being typed in by the user.
     */
    public TextUITester(String programInput) {
      // backup standard io before redirecting for tests
      saveSystemOut = System.out;
      saveSystemErr = System.err;
      saveSystemIn = System.in;
      // create alternative location to write output, and to read input from
      System.setOut(new PrintStream(redirectedOut = new ByteArrayOutputStream()));
      System.setErr(new PrintStream(redirectedErr = new ByteArrayOutputStream()));
      System.setIn(new ByteArrayInputStream(programInput.getBytes()));
    }


    /**
     * Call this method after running your test code, to check whether the expected text was printed
     * out to System.out and System.err.  Calling this method will also un-redirect standard io, so
     * that the console can be used as normal again.
     *
     * @return captured text that was printed to System.out and System.err durring test.
     */
    public String checkOutput() {
      try {
        String programOutput = redirectedOut.toString() + redirectedErr.toString();
        return programOutput;
      } finally {
        // restore standard io to their pre-test states
        System.out.close();
        System.setOut(saveSystemOut);
        System.err.close();
        System.setErr(saveSystemErr);
        System.setIn(saveSystemIn);
      }
    }
  }

}


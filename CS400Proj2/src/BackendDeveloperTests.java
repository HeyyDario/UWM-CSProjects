import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

// at least 5 JUnit test methods.
public class BackendDeveloperTests {

  DijkstraGraph graph = new DijkstraGraph(new PlaceholderMap());
  Backend backend;

  @BeforeEach
  public void setUp() {
    backend = new Backend(graph);
  }

  /**
   * This method tests if readFlightDatafile can load data successfully if the file exists.
   */
  @Test
  public void testTrueReadFlightDataFile() {
    assertTrue(backend.readFlightDataFile("flights.dot"));
  }

  /**
   * This method tests the scenario where it returns a false variable. readFlightDataFile will
   * return false when: The file path is invalid (include file not found exception).
   */
  @Test
  public void testFalseReadFlightDataFile() {
    assertFalse(backend.readFlightDataFile("invalidFilePath.dot"));
    //assertThrows(FileNotFoundException.class, () -> backend.readFlightDataFile("invalidFilePath" +
    //  ".dot"));
  }

  /**
   * This method tests if correct shortest route could be printed correctly. Currently this method
   * will use assertNotNull. It'll be changed into specific sentences after discussion with group
   * members.
   */
  @Test
  public void testGetShortestRoute() {
    backend.readFlightDataFile("flights.dot");
    //ShortestRouteInterface route = backend.getShortestRoute("ORD", "DTW");
    ShortestRoute route = backend.getShortestRoute("RNO", "SMF");

    // test airports
    String[] airportsExpected = new String[] {"RNO", "SMF"};
    int i = 0;
    for (String airport : route.getRoute()) {
      assertEquals(airport, airportsExpected[i]);
      i++;
    }

    //test miles
    Double[] milesExpected = new Double[] {113.0};
    int j = 0;
    for (Double mile : route.getMilesPerSegment()) {
      assertEquals(mile, milesExpected[j]);
      j++;
    }
  }

  /**
   * This method tests the scenarios where there's no path between the start node and the
   * destination node. Either because the start node does not exist or the destination node does not
   * exist or both. 1. Airport doesn't exist in the file. 2. There's no path between start and
   * destination.
   */
  @Test
  public void testCanNotGetShortestRoute() {
    {
      Assertions.assertThrows(NoSuchElementException.class,
          () -> backend.getShortestRoute("InvalidAirport1", "InvalidAirport2"));
    }
    {
      this.backend.readFlightDataFile("flights.dot");
      Assertions.assertThrows(NoSuchElementException.class,
          () -> backend.getShortestRoute("ORD", "DTW"));
    }
  }

  /**
   * This methods tests if statistics about the dataset "flights.dot" can be printed correctly.
   */
  @Test
  public void testGetStatistics() {
    backend.readFlightDataFile("flights.dot");
    String actual = backend.getStatistics();
    //assertNotNull(actual);
    String expected = "There are 58 airports, 1598 flights and in total 2142457.0 miles.";
    Assertions.assertEquals(actual, expected);
  }

  /**
   * This method tests if no data is loaded, statistics show this correctly.
   */
  @Test
  public void testEmptyGetStatistics() {
    String actual = backend.getStatistics();
    String expected = "There are 0 airports, 0 flights and in total 0.0 miles.";
    Assertions.assertEquals(actual, expected);
  }


  /**
   * This method tests if "exit" works at the very start since it's always been used in other tests.
   * If it works, we expect to see the output "Goodbye!", otherwise false.
   */
  @Test
  public void testIntegrationExit() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    TextUITester tester = new TextUITester("exit\n");
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));

    frontend.mainMenu();
    String output = tester.checkOutput();
    if (!(output.contains("Goodbye!"))) {
      Assertions.fail("Program cannot exit successfully.");
    }
  }


  /**
   * This method tests if "upload data" works correctly after integration.
   * For mid-week tests, only valid filePath (i.e. flights.dot) is tested. At the end of the
   * week, I'll add more scenarios in this test. For example, I'll test if an invalid filePath is
   * typed.
   */
  @Test
  public void testIntegrationUploadData() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    TextUITester tester = new TextUITester("flights.dot\n3\n");
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));

    frontend.mainMenu();
    String output = tester.checkOutput();
    if (!(output.contains("File loaded successfully!")) || !(output.contains(
        "Please type a number corresponding to one of the following commands:")) || !(output.contains(
        "Goodbye!"))) {
      Assertions.fail("Program cannot upload data successfully.");
    }
  }

  /**
   * This method tests if "list statistics about data file" works correctly after integration.
   * For mid-week tests, only valid filePath is tested.
   */
  @Test
  public void testIntegrationListStatistics() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    TextUITester tester = new TextUITester("flights.dot\n1\n3\n");
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));

    frontend.mainMenu();
    String output = tester.checkOutput();
    if (!(output.contains("There are 58 airports, 1598 flights and in total 2142457.0 miles."))) {
      Assertions.fail("Program cannot list statistics about data file successfully.");
    }
  }

  /**
   * This method tests if "Calculate the shortest route between two airports and list trip info"
   * works correctly after integration.
   */
  @Test
  public void testIntegrationCalculation() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    TextUITester tester = new TextUITester("flights.dot\n2\nRNO SMF\n3\n");
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));

    frontend.mainMenu();
    String output = tester.checkOutput();
    if (!(output.contains("RNO to SMF | Miles: 113.0")) || !(output.contains(
        "Total Miles from RNO to SMF: 113.0")) || !(output.contains(
        "Please type in a start and destination airport with a space in between and press enter " +
            "(ex. \"ORD OGG\") or type \"b\" to go back."))) {
      Assertions.fail(
          "Program cannot calculate the shortest route between two airports and list " + "trip " + "info" + "successfully.");
    }
  }

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


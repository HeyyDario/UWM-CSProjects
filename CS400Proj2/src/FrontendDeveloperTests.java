// --== CS400 Fall 2023 File Header Information ==--
// Name: Zachary Chariton
// Email: zchariton@wisc.edu
// Group: C40
// TA: Robert Nagel
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Scanner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * This class contains various test methods to test the FrontEnd interface based on input from the user.
 */
public class FrontendDeveloperTests {

  /**
   * This test checks that the loadDataFile method returns correctly when given a good file
   */
  @Test
  public void test1() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));
    boolean test = frontend.loadDataFile("flights.dot");
    if (test != true) {
      Assertions.fail("File not loaded successfully");
    }
  }

  /**
   * This test ensures that the showStatistics method prints a correct string with stats when called
   */
  @Test
  public void test2() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);
    TextUITester tester = new TextUITester("flights.dot\n1\n3\n");
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));

    frontend.mainMenu();
    String output = tester.checkOutput();
    if (!(output.contains("Airports") && output.contains("Flights") && output.contains("Miles"))) {
      Assertions.fail("Program did not return correct data");
    }
  }

  /**
   * This test makes sure shortestRoute method prints out a string with the correct data when called
   */
  @Test
  public void test3() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);
    TextUITester tester = new TextUITester("flights.dot\n2\nATL FSO\n3\n");
    FrontendInterface frontend = new Frontend(backend, new Scanner(System.in));

    frontend.mainMenu();
    String output = tester.checkOutput();
    if (!(output.contains("ATL") && output.contains("MEM") && output.contains("SFO") && output.contains("Miles"))) {
      Assertions.fail("Program did not return correct data");
    }

  }

  /**
   * This test makes sure that the app closes properly when exitApp is called.
   */
  @Test
  public void test4() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);
    TextUITester tester = new TextUITester("flights.dot\n3\n");
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));

    frontend.mainMenu();
    String output = tester.checkOutput();
    if (!output.contains("Goodbye!")) {
      Assertions.fail("Program did not close correctly");
    }
  }

  /**
   * This test checks that bad file input throws an error
   */
  @Test
  public void test5() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));
    boolean test = frontend.loadDataFile("badfile.dot");
    if (test != false) {
      Assertions.fail("Load file method returned true when it should have returned false");
    }
  }

  /**
   * This test makes sure calling the backend methods getShortestRoute and getStatistics return the correct info.
   */
  @Test
  public void integrationTest1() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    BackendInterface backend = new Backend(graph);
    TextUITester tester = new TextUITester("flights.dot\n2\nATL FSO\n1\n3\n");
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));

    frontend.mainMenu();
    String output = tester.checkOutput();
    // Test the getShortestRoute method
    if (!(output.contains("ATL") && output.contains("SFO"))) {
      Assertions.fail("Program did not return correct data");
    }
    //test the getStatistics method
    if (!(output.toUpperCase().contains("AIRPORTS")
        && output.toUpperCase().contains("FLIGHTS")
        && output.toUpperCase().contains("TOTAL MILES"))) {
      Assertions.fail("Program did not return correct data");
    }
  }

  /**
   * This test checks that the backend can correctly load a file
   */
  @Test
  public void integrationTest2() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    BackendInterface backend = new Backend(graph);
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));
    try {
      boolean test = frontend.loadDataFile("badfile");
      if (test){
        Assertions.fail(); // if test reaches here it fails
      }
    }
    catch (Exception e){
      System.out.println("Backend has an uncaught error when loading a bad file");
    }

    try {
      boolean test = frontend.loadDataFile("flights.dot");
      if (!test){
        Assertions.fail("Backend failed with a good file");
      }
    }
    catch (Exception e){
      Assertions.fail("Backed threw an exception with a good file (error)");
    }

  }

  /**
   * This class can be used to test text based user interactions by 1) specifying
   * a String of text input (that will be fed to System.in as if entered by the user),
   * and then 2) capturing the output printed to System.out and System.err in String
   * form so that it can be compared to the expect output.
   * @author dahl
   * @date 2021.10
   */
  public class TextUITester {

    /**
     * This is the code being tested by the main method above.
     * It 1) prints out a welcome message,
     *    2) reads a String, a double, and a character from System.in, and then
     *    3) prints out the string followed by a number that is one greater than that double,
     *       if the character that it read in was a (lower case) 'q'.
     */
    public static void run() {
      // Note to avoid instantiating more than one Scanner reading from System.in in your code!
      Scanner in = new Scanner(System.in);
      System.out.println("Welcome to the run program.");
      System.out.println("Enter a string, a double, and then q to quit: ");
      String s = in.nextLine();
      double d = in.nextDouble(); in.nextLine(); // read newline after double
      char c = in.nextLine().charAt(0);
      if(c == 'q')
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
     * Call this method after running your test code, to check whether the expected
     * text was printed out to System.out and System.err.  Calling this method will
     * also un-redirect standard io, so that the console can be used as normal again.
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

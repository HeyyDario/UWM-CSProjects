// --== CS400 Fall 2023 File Header Information ==--
// Name: Jinyu Shi
// Email: jshi278@wisc.edu
// Group: C14
// TA: Matthew Schwennesen
// Lecturer: Florian Heimerl

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class BackendDeveloperTests  {
  IterableMultiKeyRBT redBlackTree = new IterableMultiKeyRBT();
  Backend backend;

  @BeforeEach
  public void setUp(){

    backend = new Backend(redBlackTree);

  }

  /**
   * This method is used to test if readFile from Backend works.
   * The test file in the method is "testSongs.csv"
   * By outputting the arraylist inside readFile to compare the song information with
   * song1 to song4 to check if readFile works.
   * @throws IOException
   */
  @Test
  public void testReadFile() throws IOException {
    Song song1 = new Song("\"Hard\"","\"Rihanna\"","\"barbadian pop\"",2010,31);
    Song song2 = new Song("\"Love The Way You Lie\"","\"Eminem\"","\"detroit hip hop\"",2010,75);
    Song song3 = new Song("\"Marry You\"","\"Bruno Mars\"","\"pop\"",2010,62);
    Song song4 = new Song("\"Hey Mama (feat. Nicki Minaj, Bebe Rexha & Afrojack)\"","\"David Guetta\"",
        "\"dance pop\"",2015,60);
    // read file
    backend.readFile("./testSongs.csv");

    //compare the if the contents in arraylist from backend is same with song1, 2 , 3.
    Assertions.assertEquals(backend.getSongList().get(0).toString(),song1.toString());
    Assertions.assertEquals(backend.getSongList().get(1).toString(),song2.toString());
    Assertions.assertEquals(backend.getSongList().get(2).toString(),song3.toString());
    Assertions.assertEquals(backend.getSongList().get(3).toString(),song4.toString());
  }

  /**
   * This method is used to test if getAverageDanceability() from Backend works.
   * The test file in the method is "testSongs.csv"
   * By outputting a double to compare expected average dance-ability, return true if works, fails if fails.
   * @throws IOException
   */
  @Test
  public void testGetAverageDanceability() throws IOException{
    // read file to store 3 songs.
    backend.readFile("./testSongs.csv");
    // set expected average score.
    double expected = 228.0 / 4.0;
    // compare the average danceability.
    Assertions.assertEquals(backend.getAverageDanceability(), expected);
  }

  /**
   * This method is used to test if GetSongsWithMinDanceability() works from backend.
   * The test file in the method is "testSongs.csv".
   * @throws IOException
   */
  @Test
  public void testGetSongsWithMinDanceability() throws IOException{
    backend.readFile("./testSongs.csv");
    // set the expected song which has minimum danceability
    Song expected = new Song("\"Hard\"","\"Rihanna\"","\"barbadian pop\"",2010,31);
    List<Song> minSong =  backend.getSongsWithMinDanceability();
    // compare the min dcb
    Assertions.assertEquals(minSong.get(0).toString(),expected.toString());
  }

  /**
   * This method is used to test if getSongsAboveThreshold() works from backend.
   * The test file in the method is "testSongs.csv".
   * Check if output arraylist contains same song-instances with expected song1-3.
   * @throws IOException
   */
  @Test
  public void testGetSongsAboveThreshold() throws IOException{
    backend.readFile("./testSongs.csv");
    // set the Threshold.
    int threshold  = 50;
    Song song1 = new Song("\"Love The Way You Lie\"","\"Eminem\"","\"detroit hip hop\"",2010,75);
    Song song2 = new Song("\"Marry You\"","\"Bruno Mars\"","\"pop\"",2010,62);
    Song song3 = new Song("\"Hey Mama (feat. Nicki Minaj, Bebe Rexha & Afrojack)\"","\"David Guetta\"",
        "\"dance pop\"",2015,60);
    List<Song> songsAboveThreshold =  backend.getSongsAboveThreshold(threshold);
    // check if the songs that above threshold is song1 and song2.
    Assertions.assertEquals(songsAboveThreshold.get(0).toString(), song3.toString());
    Assertions.assertEquals(songsAboveThreshold.get(1).toString(), song2.toString());
    Assertions.assertEquals(songsAboveThreshold.get(2).toString(), song1.toString());
  }

  /**
   * This method is to compare if CompareTo() in Song class works.
   *
   */
  @Test
  public void testSongCompare(){
    Song song1 = new Song("\"Hard\"","\"Rihanna\"","\"barbadian pop\"",2010,31);
    Song song2 = new Song("\"Love The Way You Lie\"","\"Eminem\"","\"detroit hip hop\"",2010,75);
    // compare the score between song1 and song2, return -1.
    int expected = -1;
    Assertions.assertEquals(song1.compareTo(song2), expected);song1.compareTo(song2);
  }

  /**
   *  This method is used to test both readFile() in backend and frontend works,
   *  it firstly checks if readFile works in backend, then it check if the loadDataFromFile()
   *  from the frontend works.
   */
  @Test
  public void testLoadDataFromFileIteration() {
    boolean read = false;
    try {
      read = backend.readFile("./testSongs.csv");
    } catch(IOException e) {
      Assertions.fail("Reading file failed.");
    }

    // Assert that 'read' is true, meaning the file was successfully read
    Assertions.assertTrue(read, "Backend failed to read the file.");

    FrontendInterface frontend = new Frontend(backend);

    // Test the 'loadDataFromFile' method of Frontend
    // return true if successful.
    boolean loaded = false;
    try {
      loaded = frontend.loadDataFromFile("./testSongs.csv");
    } catch(IOException e) {
      Assertions.fail("Frontend failed to read the file.");
    }
    // Return true if successful
    Assertions.assertTrue(loaded, "Frontend failed to load data from file.");
  }

  /**
   * This method is used to test the Integration of backend and frontend.
   * it firstly checks if readFile() and getAverageDanceability() works in backend,
   * then it check if the averageDanceability() from the frontend works.
   *
   */
  @Test
  public void testGetAverageDanceabilityIntegration() {
    // read file
    try {
      backend.readFile("./testSongs.csv");
    } catch(IOException e) {
      Assertions.fail("Reading file failed.");
    }
    double expected = backend.getAverageDanceability();
    // initiate the instance of frontend.
    FrontendInterface frontend = new Frontend(backend);
    // check if actual is equals to expected
    double actual = frontend.averageDanceability(backend.getSongList());
    Assertions.assertEquals(expected,actual);
  }
  /**
   *  This method is to test if runCommandLoop works from frontend.
   *  Test by Jinyu Shi (backend)
   */
  @Test
  public void testRunCommandLoop() throws IOException {
    TextUITester tester = new TextUITester("4\n");
    Frontend test1 = new Frontend(new Backend(new IterableMultiKeyRBT()));
    test1.runCommandLoop();
    String output = tester.checkOutput();
    // check if output contains the display
    boolean result = false;
    if (output.contains("Please choose one of the commands:") && output.contains(
        "Exiting the app. Goodbye!")){
      result = true;
    }
    Assertions.assertTrue(result, "cannot load file successfully when choice is 1");
  }

  /**
   *  This method is to test if listSongsByDanceability works from frontend.
   *  Test by Jinyu Shi (backend)
   */
  @Test
  public void testListSongsByDanceability() throws IOException {
    TextUITester tester = new TextUITester("1\ntestSongs.csv\n2\n74\n4\n");
    Frontend test2 = new Frontend(new Backend(new IterableMultiKeyRBT<>()));
    test2.runCommandLoop();

    String expected =
        "Song: \"Love The Way You Lie\", Artist: \"Eminem\", genre: \"detroit hip hop\", year: " + "2010, dnce: 75";

    String output = tester.checkOutput();
    boolean result = false;
    if (output.contains(expected)){
      result = true;
    }

    Assertions.assertTrue(result, "In integration scenario1: cannot get correct average danceability when " +
        "choice is 2");



  }

  private class TextUITester {

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
    private TextUITester(String programInput) {
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
    private String checkOutput() {
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

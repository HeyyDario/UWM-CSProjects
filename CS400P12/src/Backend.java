// --== CS400 Fall 2023 File Header Information ==--
// Name: Jinyu Shi
// Email: jshi278@wisc.edu
// Group: C14
// TA: Matthew Schwennesen
// Lecturer: Florian Heimerl
// Notes: get one idea about checking song's name from Jinhan yang (in readingFile)

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is the Backend class which implements the interface named BackendInterface.
 * It contians 5 methods : readFile, getAverageDanceability, getSongsWithMinDanceability,
 * getSongsAboveThreshold, and getSongList. Note that getSongList is only used in BackendDeveloperTests to print
 * the information of songs.
 */
public class Backend implements BackendInterface{
  private ArrayList<Song> songList;
  private  IterableMultiKeySortedCollectionInterface<Song> RBT;

  //use IterableMultiKeySortedCollectionInterface RBT to be BackendPlaceholder's parameter
  Backend(IterableMultiKeySortedCollectionInterface RBT){
    this.songList = new ArrayList<>();
    this.RBT = RBT;
  }
  // note: readFile cannot read complicated song name (like song name contains ",") yet, need improvement.
  public static void main(String[] args) throws IOException {
    // initiate an instance of frontend and backend.
    Frontend myProject = new Frontend(new Backend(new IterableMultiKeyRBT<>()));
    // run the project (both backend and frontend)
    myProject.runCommandLoop();
  }

  /**
   * The method is used to read a csv format file, use both arraylist and IterableMultiKeyRBT to store
   * the information (songs). Return true if successful, false if fails.
   * The method uses BufferedReader to simulate CSV file reader, which can separates sentences with ",",
   * and contains things inside "".
   * @param filePath The path to the CSV file.
   * @return a boolean type
   * @stores an arraylist and an instance of IterableMultiKeyRBT
   * @throws IOException
   */
  @Override
  public boolean readFile(String filePath) throws IOException{
    // clear the arraylist and red black tree so that we can read files multiple times.
    if(this.RBT != null && this.songList != null){
      this.RBT.clear();
      this.songList.clear();
    }
    // read file
    FileReader file = null;
    // check if file exists.
    try {
      file = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      System.out.println("File not found. Please enter a CSV file.");
      return false;
    }

    BufferedReader reader = new BufferedReader(file);
    String line;
    // skip the first line which is the label.
    reader.readLine();
    // assign values from each line to arraylist, split with ",".
    try{
      while((line = reader.readLine()) != null && !line.isEmpty()){
        // use a string array list story each line's variable.
        String[] temp = line.split(",");
        // variables
        String title = "";
        String artist = "";
        String genre = "";
        int year = 0;
        int score = 0;
        // check if song name contains ",". Get the idea from Jinhan Yang.
        int adSongName = temp.length - 14;
        // if the song name contains ","
        if(adSongName > 0){
          // use the for loop to connect song name which has ",".
          for (int i = 0; i <= adSongName; i++){
            if (i != 0) {
              title += "," + temp[i];
            } else {
              title += temp[i];
            }
          }
          artist = temp[1 + adSongName];
          genre = temp[2 + adSongName];
          year = Integer.parseInt(temp[3 + adSongName]);
          score = Integer.parseInt(temp[6 + adSongName]);
        } else {
          title = temp[0];
          artist = temp[1];
          genre = temp[2];
          year = Integer.parseInt(temp[3]);
          // 8th column is dcs.
          score = Integer.parseInt(temp[6]);
        }

        Song song = new Song(title, artist, genre, year, score);
        // adding songs to rbt and arraylist. Choose either or both.
        this.songList.add(song);
        this.RBT.insertSingleKey(song);
      }
    }catch(IOException e) {
      System.out.println("read file fails, please make sure the format of your csv file is right!");
      return false;
    }
    // close the file after reading it.
    try{
      reader.close();
    }catch(IOException e){
      System.out.println("read file fails: file cannot close.");
      return false;
    }
    return true;
  }

  /**
   * This method is used to get the average dance-ability by using arraylist
   * because its iteration speed is faster than the red black tree.
   * @return the average decidability (double)
   */
  @Override
  public double getAverageDanceability() {
    //check if songList is empty.
    if(this.songList == null || this.songList.isEmpty()) {
      throw new IllegalStateException("Song list is empty!");
    }

    double averageDBT = 0;
    double sum = 0;
    for (Song song: this.songList){
      sum += song.getDanceabilityScore();
    }
    averageDBT = sum / songList.size();

    return averageDBT;
  }
  /**
   * This method is used to get minimum dance-ability score by using red black tree(IterableMultiKeyRBT)
   * because it will sort the tree once inserting a node. (faster)
   * @return an arraylist which contains song-instance.
   *
   */
  @Override
  public ArrayList<Song> getSongsWithMinDanceability() {
    ArrayList <Song> minDBTSong = new ArrayList<>();

    if(this.RBT == null) {
      throw new IllegalStateException("The Red-Black Tree is not initialized !");
    }

    Iterator<Song> iterator = this.RBT.iterator();

    // get all keys from the first node.
    while (iterator.hasNext()){
      Song song = iterator.next();
      // add the first song which has smallest score into the arraylist.
      if(minDBTSong.isEmpty()){
        minDBTSong.add(song);
      } else if (minDBTSong.get(0).compareTo(song) == 0){
        minDBTSong.add(song);
      } else {

        break;
      }
    }
    return minDBTSong;
  }
  /**
   * This method is used to get minimum dance-ability score which greater than the integer that user inputs.
   * Using red black tree(IterableMultiKeyRBT) in this method because it will sort the tree once inserting
   * a node. (faster)
   *
   *  @return an arraylist which contains song-instance.
   *
   */
  @Override
  public ArrayList<Song> getSongsAboveThreshold(int threshold) {
    // check if threshold is
    // initialize the arraylist to store songs with dbs above the threshold.
    ArrayList <Song> minDBTSong = new ArrayList<>();
    // check if RBT is null.
    if(this.RBT == null) {
      throw new IllegalStateException("The Red-Black Tree is not initialized !");
    }
    // create the test song so that we can set start point.
    Song testSong = new Song("test","test","1",1,threshold);
    // initialize RBT iterator to find keys that greater than threshold.
    this.RBT.setIterationStartPoint(testSong);
    Iterator<Song> iterator = this.RBT.iterator();
    // get all keys from the nodes that greater than threshold.
    while (iterator.hasNext()){
      Song song = iterator.next();
      minDBTSong.add(song);
    }
    // change the start point back to null
    this.RBT.setIterationStartPoint(null);
    return minDBTSong;
  }

  /**
   * this method is only for test methods:
   * to verify if readFile is successful
   */

  public ArrayList<Song> getSongList(){
    if (songList == null || songList.isEmpty()){
      throw new IllegalStateException("Song list is empty!");
    }
    return this.songList;
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

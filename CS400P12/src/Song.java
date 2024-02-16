// --== CS400 Fall 2023 File Header Information ==--
// Name: Jinyu Shi
// Email: jshi278@wisc.edu
// Group: C14
// TA: Matthew Schwennesen
// Lecturer: Florian Heimerl
// Notes to Grader: used rotate method from Professor Heimerl because the previous one doesn't work

public class Song implements SongInterface{
  //fields
  private String artist;
  private String title;
  private int year;
  private String genre;
  private int score;
  //constructor
  Song(String title, String artist, String genre,int year, int score){
    this.title = title;
    this.artist = artist;
    this.genre = genre;
    this.year = year;
    this.score = score;
  }
  // get methods
  @Override
  public String getArtist() { return this.artist; }
  @Override
  public int getYear() { return this.year; }
  @Override
  public String getTitle() {
    return null;
  }
  @Override
  public String getGenre() { return this.genre; }

  @Override
  public int getDanceabilityScore() { return this.score; }
  // set methods
  @Override
  public void setTitle(String title) {
    if (title != null && !title.isEmpty()) {
      this.title = title;
    }
  }
  @Override
  public void setArtist(String artist) {
    if (artist != null && !artist.isEmpty()) {
      this.artist = artist;
    }
  }
  @Override
  public void setYear(int year) {
    if (year > 0){
      this.year = year;
    }
  }
  @Override
  public void setGenre(String genre) {
    if (genre != null && !genre.isEmpty()) {
      this.genre = genre;
    }
  }
  @Override
  public void setDanceabilityScore(int score) {
    if (score > 0) {
      this.score = score;
    }
  }

  @Override
  public int compareTo(Song song) {
    if (this.score > song.score) return 1;
    else if (this.score < song.score) return -1;
    else return 0;
  }

  @Override
  public String toString(){
    return "Song: " + this.title + ", Artist: " + this.artist + ", genre: " + this.genre + ", year: " + this.year
        + ", dnce: " + this.score;
  }
}

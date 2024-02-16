public interface SongInterface extends Comparable<Song> {
    // get methods
    String getArtist();
    int getYear();
    String getTitle();
    String getGenre();
    int getDanceabilityScore();
    // set methods
    void setTitle(String title);
    void setArtist(String artist);
    void setYear(int year);

    void setGenre(String genre);

    void setDanceabilityScore(int score);

    @Override
    public int compareTo(Song song);


}

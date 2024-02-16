import java.io.IOException;
import java.util.List;

/**
 * This interface defines the backend functionality exposed to the frontend.
 */
public interface BackendInterface {
    /**
     * create an instance of BackendInterface 。
     *
     * @param collection A collection instance that implements the IterableMultiKeySortedCollectionInterface
     * interface for data manipulation in the backend。
     */


    /**
     * Reads data from a specified CSV file.
     *
     * @param filePath The path to the CSV file.
     */
    boolean readFile(String filePath) throws IOException;


    /**
     * Returns the average danceability score of all songs in the loaded data.
     *
     * @return Average danceability score.
     */
    double getAverageDanceability();

    /**
     * Retrieves a list of songs with a minimum danceability score
     * that is at or above a specified threshold.
     *
     * @return List of songs with a minimum danceability score.
     */
    List<Song> getSongsWithMinDanceability();

    /**
     * Retrieves a list of songs with a danceability score above the threshold
     * that is at or above a specified threshold.
     *
     * @param threshold The danceability threshold.
     * @return List of songs above the threshold.
     */
    List<Song> getSongsAboveThreshold(int threshold);
}





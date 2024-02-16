import java.io.FileNotFoundException;
import java.util.List;

public class BackendPlaceholderFrontend implements BackendInterface{
  @Override
  public boolean readFile(String filePath) {
    return false;
  }

  @Override
  public double getAverageDanceability() {
    return 0;
  }

  @Override
  public List<Song> getSongsWithMinDanceability() {
    return null;
  }

  @Override
  public List<Song> getSongsAboveThreshold(int threshold) {
    return null;
  }

}

import java.util.List;

/**
 * This interface defines functions that are needed to store the shortest path.
 */
public interface ShortestRouteInterface {
  /**
   * Creates a 'ShortestRoute' object.
   *
   * @param airports airports flown to.
   * @param miles miles flown from each airport.
   */
  // ShortestRoute(List<String> airports, List<Double> miles) { }

  /**
   * Gets the names of the airports flown to.
   *
   * @return names of the airports flown to.
   */
  List<String> getRoute();

  /**
   * Gets the miles flown from each airport.
   *
   * @return miles flown from each airport.
   */
  List<Double> getMilesPerSegment();

  /**
   * Gets the total miles flown.
   *
   * @return total miles flown.
   */
  Double getTotalMiles();
}


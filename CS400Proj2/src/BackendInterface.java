/**
 * Interacts with the flight data.
 *
 * Serves as middle-man between frontend and flight data.
 */
public interface BackendInterface {

  /**
   * Creates a Backend object.
   *
   * @param graphADT graph used to store flight data.
   */
  //    BackendInterface(GraphADT<String, Double> graphADT);


  /**
   * This method reads data from a file and returns a value related to the process. If data is
   * loaded successfully, return true; otherwise false.
   *
   * @param filePath the name of the DOT file to be loaded.
   *
   * @return - true if data is loaded successfully, false otherwise.
   */
  boolean readFlightDataFile(String filePath);

  /**
   * Gets the shortest flight route from 'src' to 'dst'.
   *
   * @param start airport that serves as the start of the route.
   * @param destination airport that serves as the destination of the route.
   *
   * @return shortest flight route from 'start' to 'destination'.
   */
  ShortestRouteInterface getShortestRoute(String start, String destination);

  /**
   * This method gets a string with statistics about the dataset that includes the number of nodes
   * (airports), the number of edges (flights), and the total miles (sum of weights) for all edges in the graph.
   *
   * @return - a string with statistics about the dataset that includes the number of nodes
   * (airports), the number of edges (flights), and the total miles (sum of weights) for all edges in the graph.
   */
  String getStatistics();
}

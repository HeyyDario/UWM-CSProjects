// --== CS400 Fall 2023 File Header Information ==--
// Name: Chengtao Dai
// Email: cdai53@wisc.edu
// Group: G09
// TA:
// Lecturer: Florian Heimerl
// Notes to Grader: none
import java.util.List;

/**
 * This interface defines functions that are needed in backend development and integration.
 *
 * @author Chengtao Dai
 */
public interface IndividualBackendInterface {
  //Representative Tasks Performed Using this Application:
  //1.Search for and list the flights,
  //including the number of miles of each flight, to travel from ATL to SFO.
  //2.Show the number of airports, flights, and total number of miles that the dataset contains.

  //writes code that reads in graph data from a DOT file, inserts it into a graph data structure
  //that implements the GraphADT.java interface

  /**
   * Constructor expected here.
   * public IndividualBackendInterface(GraphADT someGraphADTHere);
   */

  /**
   * This method reads data from a file and returns a value related to the process. If data is
   * loaded successfully, return true; otherwise false.
   * @param fileName the name of the DOT file to be loaded.
   * @return - true if data is loaded successfully, false otherwise.
   */
  public boolean readFile(String fileName);

  /**
   * This method gets the shortest route from a start to a destination airport in the dataset.
   * @return - the shortest route from a start to a destination airport in the dataset.
   */
  public ShortestRouteInterface getShortestRoute();

  /**
   * This method gets a string with statistics about the dataset that includes the number of nodes
   * (airports), the number of edges (flights), and the total miles (sum of weights) for all edges in the graph.
   * @return - a string with statistics about the dataset that includes the number of nodes
   * (airports), the number of edges (flights), and the total miles (sum of weights) for all edges in the graph.
   */
  public String getStatistics();

  // we may also need a getter for the data from the file apart from the shortest path
}

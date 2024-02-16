// --== CS400 Fall 2023 File Header Information ==--
// Name: Chengtao Dai
// Email: cdai53@wisc.edu
// Group: C14
// TA: Matthew Schwennesen
// Lecturer: Florian Heimerl
// Notes to Grader: -

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Backend implements BackendInterface {
  private DijkstraGraph graph;
  private ArrayList<String> airportDic; // store the airports in the dataset
  private ArrayList<Double> milesDic; // store the distances in the dataset

  /**
   * Creates a Backend object.
   *
   * @param graph graph used to store flight data.
   */
  public Backend(DijkstraGraph graph) {
    this.graph = graph;
    airportDic = new ArrayList<>();
    milesDic = new ArrayList<>();
  }

  /**
   * This method reads data from a file and returns a value related to the process. If data is
   * loaded successfully, return true; otherwise false.
   *
   * @param filePath the name of the DOT file to be loaded.
   * @return - true if data is loaded successfully, false otherwise.
   */
  @Override
  public boolean readFlightDataFile(String filePath){

    File file = new File(filePath);
    Scanner scnr;
    try {
      scnr = new Scanner(file);
      while (scnr.hasNextLine()) {
        String line = scnr.nextLine();
        // Assuming each line in the DOT file is formatted as "Airport1 -- Airport2
        // [weight=distance]"
        String[] parts = line.split(" -- | \\[miles=");
        if (parts.length == 3) {
          String startAirport = parts[0].replace("\"", "").trim();
          String endAirport = parts[1].replace("\"", "").trim();
          double distance = Double.parseDouble(parts[2].replace("];", "").trim());
          milesDic.add(distance);

          // Add the nodes and edge to the graph
          graph.insertNode(startAirport);
          graph.insertNode(endAirport);
          graph.insertEdge(startAirport, endAirport, distance);
        }

        if (parts.length == 1) {
          if (parts[0].contains("[city")) {
            String[] airportInfo = parts[0].split("\\[city");
            String airport = airportInfo[0].replace("\"", "").trim();
            airportDic.add(airport);
          }
        }
      }
      return true;
    } catch (IOException e) {
      //e.printStackTrace();
      //System.out.println("File not found. Please upload a valid dot file.");
      return false;
    }
  }

  /**
   * Gets the shortest flight route from 'src' to 'dst'.
   *
   * @param start       airport that serves as the start of the route.
   * @param destination airport that serves as the destination of the route.
   * @return shortest flight route from 'start' to 'destination'.
   */
  @Override
  public ShortestRoute getShortestRoute(String start, String destination) {
    List<String> airports = this.graph.shortestPathData(start, destination); // if no path,
    // exception will be thrown here, so no need to worry about array being null
    List<Double> miles = new ArrayList<>();
    String[] arrayAirports = airports.toArray(new String[airports.size()]);
    for (int i = 0; i < arrayAirports.length - 1; i++) {
      miles.add(this.graph.getEdge(arrayAirports[i], arrayAirports[i + 1]).doubleValue());
    }
    return new ShortestRoute(airports, miles);
  }

  /**
   * This method gets a string with statistics about the dataset that includes the number of nodes
   * (airports), the number of edges (flights), and the total miles (sum of weights) for all edges
   * in the graph.
   *
   * @return - a string with statistics about the dataset that includes the number of nodes
   * (airports), the number of edges (flights), and the total miles (sum of weights) for all edges
   * in the graph.
   */
  @Override
  public String getStatistics() {
    double totalMiles = calcTotalMiles();
    String toReturn =
        "There are " + this.graph.getNodeCount() + " airports, " + this.graph.getEdgeCount() + " " + "flights " +
            "and " + "in total " + totalMiles + " miles.";
    return toReturn;
  }

  /**
   * This is a helper method to calculate the total miles in the given dataset.
   * @return the total miles in the given dataset.
   */
  private double calcTotalMiles(){
    if (airportDic == null || airportDic.isEmpty() || airportDic.size() == 0)
      return 0;
    if (milesDic == null || milesDic.isEmpty() || milesDic.size() == 0)
      return 0;
    double sum = 0;
    for (Double mile : milesDic){
      sum += mile;
    }
    return sum;
  }

  /**
   * This is the main method that starts the command loop for the user
   *
   * @param args unused
   */
  public static void main(String[] args) {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    BackendInterface backend = new Backend(graph);
    FrontendInterface frontend = new Frontend(backend, new java.util.Scanner(System.in));
    frontend.mainMenu();

  }
}

// --== CS400 File Header Information ==--
// Name: Chengtao Dai
// Email: cdai53@wisc.edu
// Group and Team: G09
// Group TA: Robert Nagel
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for computing the total
 * cost and list of node data along the shortest path connecting a provided starting to ending
 * nodes. This class makes use of Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode contains data about one
   * specific path between the start node and another node in the graph. The final node in this path
   * is stored in its node field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referened by the predecessor field (this field is
   * null within the SearchNode containing the starting node in its node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
   * highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   *
   * @param map the map that the graph uses to map a data object to the node object it is stored in
   */
  public DijkstraGraph(MapADT<NodeType, Node> map) {
    super(map);
  }


  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    if (!nodes.containsKey(start)) {
      throw new NoSuchElementException();
    }
    if (!nodes.containsKey(end)) {
      throw new NoSuchElementException();
    }
    // implement in step 5.3
    PlaceholderMap visited = new PlaceholderMap<>();
    PriorityQueue<SearchNode> pq = new PriorityQueue<>();
    SearchNode startSearchNode = new SearchNode(nodes.get(start), 0, null);
    pq.add(startSearchNode);
    while (!pq.isEmpty()) {
      SearchNode current = pq.poll();
      if (current.node.data.equals(end)){
        return current;
      }

      if (!visited.containsKey(current.node.data)) {
        visited.put(current.node.data, current);
        for(Edge edge : current.node.edgesLeaving){
          if (!visited.containsKey(edge.successor.data)) {
            pq.add(new SearchNode(edge.successor, edge.data.doubleValue() + current.cost, current));
          }
        }
      }

    }
    if (!startSearchNode.node.data.equals(end))
      throw new NoSuchElementException(
          "There is no directed path that connects from the start " + "node to the end node.");
    return null;
  }
  /**
   * This helper method creates a network of SearchNodes while computing the shortest path between
   * the provided start and end locations. The SearchNode that is returned by this method is
   * represents the end of the shortest path that is found: it's cost is the cost of that shortest
   * path, and the nodes linked together through predecessor references represent all of the nodes
   * along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath1(NodeType start, NodeType end) {
    // implement in step 5.3
    if (!nodes.containsKey(start))
      throw new NoSuchElementException("The start node doesn't exist in the graph.");
    if (!nodes.containsKey(end))
      throw new NoSuchElementException("The end node doesn't exist in the graph.");

    // instantiations
    PlaceholderMap<NodeType, SearchNode> visited = new PlaceholderMap<>(); // Tracks visited nodes
    PriorityQueue<SearchNode> pq = new PriorityQueue<>();

    SearchNode searchNode = new SearchNode(nodes.get(start), 0, null);
    pq.add(searchNode);

    // implement dijkstra's algorithm here
    while (!pq.isEmpty()) {
      // find the minimum cost to remove
      //SearchNode current = findMin(pq);
      //pq.remove(current);
      SearchNode current = pq.poll();

      // if already visited, skip the current iteration
      if (visited.containsKey(current.node.data))
        continue;
      // else if
      if (!visited.containsKey(current.node.data)) {
        // if the node in the searchnode is unvisited, mark node as visited
        visited.put(current.node.data, current);
        // set predecessor to Pre, and cost to Cost
        //        searchNode.predecessor = searchNode;
        //        searchNode.node = current.node;
        //        searchNode.cost += current.cost;

        if (current.node.data.equals(end))
          return current;

        // for each
        for (Edge edge : current.node.edgesLeaving) {
          if (!visited.containsKey(edge.successor.data)) {
            pq.add(new SearchNode(edge.successor, current.cost + edge.data.doubleValue(), current));
          }
        }

        //searchNode = current;
      }
    }
    if (!searchNode.node.data.equals(end))
      throw new NoSuchElementException(
          "There is no directed path that connects from the start " + "node to the end node.");

    return null;
  }

  private SearchNode findMin(PriorityQueue<SearchNode> pq){
    SearchNode[] searchNodes = (SearchNode[]) pq.toArray();
    if (searchNodes == null)
      return null;
    else {
      SearchNode min = searchNodes[0];
      for (SearchNode searchNode : searchNodes) {
        if (searchNode.compareTo(min) <= 0 ){
          min = searchNode;
        }
      }
      return min;
    }
  }

  /**
   * Returns the list of data values from nodes along the shortest path from the node with the
   * provided start value through the node with the provided end value. This list of data values
   * starts with the start value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // implement in step 5.4
    SearchNode current = computeShortestPath(start, end);
    List<NodeType> shortestPathData = new ArrayList<>();
    while (current != null) {
      shortestPathData.add(current.node.data);
      current = current.predecessor;
    }

    NodeType[] reverse = (NodeType[]) shortestPathData.toArray();
    shortestPathData.clear();

    for (int i = reverse.length - 1; i >= 0; i--) {
      shortestPathData.add(reverse[i]);
    }
    return shortestPathData;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest path freom the node
   * containing the start data to the node containing the end data. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    // implement in step 5.4
    SearchNode current = computeShortestPath(start, end);
    if (!current.node.data.equals(end))
      return 0;
    return current.cost;
  }

  // TODO: implement 3+ tests in step 4.1

//  /**
//   * This method makes use of an example in lecture, and confirm that the results of my
//   * implementation match what I previously computed by hand.
//   * <p>
//   * Specifically, for the graph if the start node is "A" and the end node is "H", the data path
//   * is expected to be "[A, B, D, F, H]" and the cost over the path is expected to be 9.
//   */
//  @Test
//  public void testInClassExample() {
//    DijkstraGraph<String, Integer> diGraph = new DijkstraGraph<>(new PlaceholderMap<>());
//    diGraph.insertNode("A");
//    diGraph.insertNode("B");
//    diGraph.insertNode("E");
//    diGraph.insertEdge("A", "B", 4);
//    diGraph.insertEdge("A", "E", 15);
//    diGraph.insertEdge("B", "E", 10);
//
//    diGraph.insertNode("C");
//    diGraph.insertNode("D");
//    diGraph.insertEdge("A", "C", 2);
//    diGraph.insertEdge("B", "D", 1);
//    diGraph.insertEdge("C", "D", 5);
//    diGraph.insertEdge("D", "E", 3);
//
//    diGraph.insertNode("F");
//    diGraph.insertNode("H");
//    diGraph.insertNode("G");
//    diGraph.insertEdge("D", "F", 0);
//    diGraph.insertEdge("F", "D", 2);
//    diGraph.insertEdge("F", "H", 4);
//    diGraph.insertEdge("G", "H", 4);
//
//    //test costs
//    assertEquals(9, diGraph.shortestPathCost("A", "H"));
//
//    //test path data
//    Assertions.assertEquals("[A, B, D, F, H]", diGraph.shortestPathData("A", "H").toString());
//  }
//
//  /**
//   * This method using the same graph as I did for the test above, but check the cost and
//   * sequence of data along the shortest path between a different start and end node.
//   * <p>
//   * Specifically, for the graph if the start node changes to "B" and the end node changes to
//   * "F", the data path is expected to be "[B, D, F]" and the cost over the path is expected to
//   * be 1.
//   */
//  @Test
//  public void testDifferentStartOfInClassExample() {
//    DijkstraGraph<String, Integer> diGraph = new DijkstraGraph<>(new PlaceholderMap<>());
//    diGraph.insertNode("A");
//    diGraph.insertNode("B");
//    diGraph.insertNode("E");
//    diGraph.insertEdge("A", "B", 4);
//    diGraph.insertEdge("A", "E", 15);
//    diGraph.insertEdge("B", "E", 10);
//
//    diGraph.insertNode("C");
//    diGraph.insertNode("D");
//    diGraph.insertEdge("A", "C", 2);
//    diGraph.insertEdge("B", "D", 1);
//    diGraph.insertEdge("C", "D", 5);
//    diGraph.insertEdge("D", "E", 3);
//
//    diGraph.insertNode("F");
//    diGraph.insertNode("H");
//    diGraph.insertNode("G");
//    diGraph.insertEdge("D", "F", 0);
//    diGraph.insertEdge("F", "D", 2);
//    diGraph.insertEdge("F", "H", 4);
//    diGraph.insertEdge("G", "H", 4);
//
//    //test costs
//    assertEquals(1, diGraph.shortestPathCost("B", "F"));
//
//    //test path data
//    Assertions.assertEquals("[B, D, F]", diGraph.shortestPathData("B", "F").toString());
//  }
//
//  /**
//   * This method checks the behavior of your implementation when the node that you are
//   * searching for a path between exist in the graph, but there is no sequence of directed edges
//   * that connects them from the start to the end.
//   * <p>
//   * Specifically, for the graph, if end node changes to "G", there is no data through the path
//   * since the node "G" is never connected to any node in the graph. A NoSuchElementException is
//   * expected here.
//   */
//  @Test
//  public void testNoPathOfInClassExample() {
//    PlaceholderMap map = new PlaceholderMap();
//    DijkstraGraph<String, Integer> diGraph = new DijkstraGraph<String, Integer>(map);
//    diGraph.insertNode("A");
//    diGraph.insertNode("B");
//    diGraph.insertNode("E");
//    diGraph.insertEdge("A", "B", 4);
//    diGraph.insertEdge("A", "E", 15);
//    diGraph.insertEdge("B", "E", 10);
//
//    diGraph.insertNode("C");
//    diGraph.insertNode("D");
//    diGraph.insertEdge("A", "C", 2);
//    diGraph.insertEdge("B", "D", 1);
//    diGraph.insertEdge("C", "D", 5);
//    diGraph.insertEdge("D", "E", 3);
//
//    diGraph.insertNode("F");
//    diGraph.insertNode("H");
//    diGraph.insertNode("G");
//    diGraph.insertEdge("D", "F", 0);
//    diGraph.insertEdge("F", "D", 2);
//    diGraph.insertEdge("F", "H", 4);
//    diGraph.insertEdge("G", "H", 4);
//
//    //test costs
//    Assertions.assertThrows(NoSuchElementException.class,
//        () -> diGraph.computeShortestPath("A", "G"));
//
//  }
//
//  /**
//   * This method tests 2 scenarios.
//   * 1. Start node does not exist.
//   * 2. End node does not exitst.
//   */
//  @Test
//  public void testNoNode(){
//    {
//      PlaceholderMap map = new PlaceholderMap();
//      DijkstraGraph<String, Integer> diGraph = new DijkstraGraph<String, Integer>(map);
//      diGraph.insertNode("A");
//      diGraph.insertNode("B");
//      diGraph.insertNode("E");
//      diGraph.insertEdge("A", "B", 4);
//      diGraph.insertEdge("A", "E", 15);
//      diGraph.insertEdge("B", "E", 10);
//
//      diGraph.insertNode("C");
//      diGraph.insertNode("D");
//      diGraph.insertEdge("A", "C", 2);
//      diGraph.insertEdge("B", "D", 1);
//      diGraph.insertEdge("C", "D", 5);
//      diGraph.insertEdge("D", "E", 3);
//
//      diGraph.insertNode("F");
//      diGraph.insertNode("H");
//      diGraph.insertNode("G");
//      diGraph.insertEdge("D", "F", 0);
//      diGraph.insertEdge("F", "D", 2);
//      diGraph.insertEdge("F", "H", 4);
//      diGraph.insertEdge("G", "H", 4);
//
//      //test costs
//      Assertions.assertThrows(NoSuchElementException.class,
//          () -> diGraph.computeShortestPath("P", "G"));
//    }
//
//    {
//      PlaceholderMap map = new PlaceholderMap();
//      DijkstraGraph<String, Integer> diGraph = new DijkstraGraph<String, Integer>(map);
//      diGraph.insertNode("A");
//      diGraph.insertNode("B");
//      diGraph.insertNode("E");
//      diGraph.insertEdge("A", "B", 4);
//      diGraph.insertEdge("A", "E", 15);
//      diGraph.insertEdge("B", "E", 10);
//
//      diGraph.insertNode("C");
//      diGraph.insertNode("D");
//      diGraph.insertEdge("A", "C", 2);
//      diGraph.insertEdge("B", "D", 1);
//      diGraph.insertEdge("C", "D", 5);
//      diGraph.insertEdge("D", "E", 3);
//
//      diGraph.insertNode("F");
//      diGraph.insertNode("H");
//      diGraph.insertNode("G");
//      diGraph.insertEdge("D", "F", 0);
//      diGraph.insertEdge("F", "D", 2);
//      diGraph.insertEdge("F", "H", 4);
//      diGraph.insertEdge("G", "H", 4);
//
//      //test costs
//      Assertions.assertThrows(NoSuchElementException.class,
//          () -> diGraph.computeShortestPath("A", "Z"));
//    }
//  }
//
//  @Test
//  public void testComplexGraph(){}
//
//  //more test cases if possible
}

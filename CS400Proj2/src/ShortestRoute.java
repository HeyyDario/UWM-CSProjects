import java.util.List;

public class ShortestRoute implements ShortestRouteInterface{

  private List<String> airports;
  private List<Double> miles;

  /**
   * Creates a 'ShortestRoute' object.
   *
   * @param airports airports flown to.
   * @param miles miles flown from each airport.
   */
   public ShortestRoute(List<String> airports, List<Double> miles) {
     this.airports = airports;
     this.miles = miles;
   }

  /**
   * Gets the names of the airports flown to.
   *
   * @return names of the airports flown to.
   */
  public List<String> getRoute(){
    return this.airports;
  }

  /**
   * Gets the miles flown from each airport.
   *
   * @return miles flown from each airport.
   */
  public List<Double> getMilesPerSegment() {
    return this.miles;
  }

  /**
   * Gets the total miles flown.
   *
   * @return total miles flown.
   */
  public Double getTotalMiles() {
    double sum = 0;
    for (Double mile : miles) {
      sum += mile;
    }
    return sum;
  }
}

public class BackendPlaceholder implements  IndividualBackendInterface{
  @Override
  public boolean readFile(String fileName) {
    return false;
  }

  @Override
  public ShortestRouteInterface getShortestRoute() {
    return null;
  }

  @Override
  public String getStatistics() {
    return null;
  }
}

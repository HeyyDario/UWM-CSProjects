public class demo {

  public static void main(String[] args){
    String info = "course=cs400&name=Ada Lovelace";

    MapADT<String, String> queryParams = new PlaceholderMap();

    if (info != null && !info.isEmpty()) {
      String[] pairs = info.split("&");
      for (String pair : pairs) {
        String[] keyValue = pair.split("=");
        String key = keyValue[0];
        String value = keyValue.length > 1 ? keyValue[1] : "";
        queryParams.put(key, value);
      }
    }
    String dateTime = String.valueOf(java.time.LocalDateTime.now());
    String userName = queryParams.get("name");
  }
}

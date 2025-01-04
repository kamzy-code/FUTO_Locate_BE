package kamzy.io.FutoLocate.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class Geocode {

    private static final String API_KEY = "YOUR_GOOGLE_MAPS_API_KEY";

            public static void mainMethod() {
                String address = "University of XYZ, Campus Location";
                try {
                    String[] coordinates = getCoordinates(address);
                    if (coordinates != null) {
                        System.out.println("Latitude: " + coordinates[0]);
                        System.out.println("Longitude: " + coordinates[1]);
                    } else {
                        System.out.println("Coordinates not found.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public static String[] getCoordinates(String address) throws Exception {
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                        address.replace(" ", "%20") + "&key=" + API_KEY;
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray results = jsonResponse.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject location = results.getJSONObject(0)
                            .getJSONObject("geometry")
                            .getJSONObject("location");
                    String lat = location.getString("lat");
                    String lng = location.getString("lng");
                    return new String[]{lat, lng};
                }
                return null;
            }


}

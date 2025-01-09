package kamzy.io.FutoLocate.service;

import kamzy.io.FutoLocate.Utility.ValidationHelper;
import kamzy.io.FutoLocate.enums.ModeOfTransport;
import kamzy.io.FutoLocate.model.Landmarks;
import kamzy.io.FutoLocate.model.Routes;
import kamzy.io.FutoLocate.repository.LandmarkRepository;
import kamzy.io.FutoLocate.repository.RouteRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepo;

    @Autowired
    LandmarkRepository landmarkRepo;
    @Autowired
    ValidationHelper validationHelper;

    private static final double AVERAGE_WALKING_SPEED_KMH = 5.0; // Walking speed in km/h
    private static final double AVERAGE_BIKING_SPEED_KMH = 15.0; // Biking speed in km/h
    private static final double EARTH_RADIUS_KM = 6371.0; // Radius of Earth in kilometers

//    public List<Routes> getAllRoutes(int start_landmark_id, int end_landmark_id) {
//        validationHelper.validateLandmarkIds(start_landmark_id, end_landmark_id);
//        return routeRepo.getAllRoutesBetweenTwoLandmark(start_landmark_id, end_landmark_id);
//    }
//
//    public Routes getRouteDetails(int route_id) {
//        return routeRepo.getRouteById(route_id);
//    }

    public Routes CreateRoute (double startLat, double startLong, double endLat, double endLong, ModeOfTransport transport){

        Routes route = new Routes();
        route.setDistance(calculateDistance(startLat, startLong, endLat, endLong));
        try {
            route.setPath_coordinates(fetchRoutePathviaOSM(startLat, startLong, endLat, endLong));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        route.setEstimated_time(getEstimatedTime(startLat, startLong, endLat, endLong, transport));
        route.setModeOfTransport(transport);
//        routeRepo.save(route);
        return route;
    }

    public double calculateDistance(double startLat, double startLong, double endLat, double endLong) {
        // Convert degrees to radians
        double latDistance = Math.toRadians(endLat - startLat);
        double longDistance = Math.toRadians(endLong - startLong);

        // Apply Haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat))
                * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance in kilometers
        return EARTH_RADIUS_KM * c;
    }

    public String fetchRoutePathviaOSM(double startLat, double startLng, double endLat, double endLng) throws Exception {
        String urlStr2 = String.format("http://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=full&geometries=geojson\n",
                startLng, startLat, endLng, endLat
        );
        System.out.println(urlStr2);

        // Make HTTP request
        URL url = new URL(urlStr2);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println("Step 1");

        // Read response
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        System.out.println("Step 2");

        // Parse JSON
        JSONObject jsonResponse = new JSONObject(response.toString());
        System.out.println("API Response: " + jsonResponse.toString());
        JSONArray routes = jsonResponse.getJSONArray("routes");
        System.out.println("Route Array: " + routes.toString());
        System.out.println("Step 3");
        if (!routes.isEmpty()) {
//            JSONObject overviewPolyline = routes.getJSONObject(0).getJSONObject("overview_polyline");
            JSONObject geometry = routes.getJSONObject(0).getJSONObject("geometry");
            JSONArray coordinates = geometry.getJSONArray("coordinates");
            System.out.println("Step 4");
//            JSONArray coordinates = geometry.getJSONArray("coordinates");
//            return overviewPolyline.getString("points"); // Encoded polyline
            return coordinates.toString();
        }

        throw new Exception("No route found.");
    }

    public int getEstimatedTime(double startLat, double startLong, double endLat, double endLong, ModeOfTransport modeOfTransport) {
        // Calculate distance between landmarks
        double distance = calculateDistance(startLat, startLong, endLat, endLong);

        // Determine average speed based on mode of transport
        double averageSpeed;
        if (modeOfTransport.equals(ModeOfTransport.biking)) {
            averageSpeed = AVERAGE_BIKING_SPEED_KMH;
        } else { // Default to walking
            averageSpeed = AVERAGE_WALKING_SPEED_KMH;
        }

        // Calculate time in hours
        double timeInHours = distance / averageSpeed;

        // Convert to minutes and return
        return (int) Math.ceil(timeInHours * 60); // Return estimated time in minutes
    }

}

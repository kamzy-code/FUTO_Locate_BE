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
import java.util.Optional;

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

    public List<Routes> getAllRoutes(int start_landmark_id, int end_landmark_id) {
        validationHelper.validateLandmarkIds(start_landmark_id, end_landmark_id);
        return routeRepo.getAllRoutesBetweenTwoLandmark(start_landmark_id, end_landmark_id);
    }

    public Routes getRouteDetails(int route_id) {
        return routeRepo.getRouteById(route_id);
    }

    public Routes CreateRoute (int startLandmarkId, int endLandmarkId, ModeOfTransport transport){
        validationHelper.validateLandmarkIds(startLandmarkId, endLandmarkId);
        Landmarks startLandmark = landmarkRepo.getLandmarkById(startLandmarkId);
        Landmarks endLandmark = landmarkRepo.getLandmarkById(endLandmarkId);

        Routes route = new Routes();
        route.setStart_landmark_id(startLandmark.getLandmark_id());
        route.setEnd_landmark_id(endLandmark.getLandmark_id());
        route.setDistance(calculateDistance(startLandmark.getLandmark_id(), endLandmark.getLandmark_id()));
        try {
            route.setPath_coordinates(fetchRoutePath(startLandmark.getLatitude(), startLandmark.getLongitude(), endLandmark.getLatitude(), endLandmark.getLongitude()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        route.setEstimated_time(getEstimatedTime(startLandmarkId, endLandmarkId, transport));
        route.setModeOfTransport(transport);
//        routeRepo.save(route);
        return route;
    }

    public double calculateDistance(int startLandmarkId, int endLandmarkId) {
        validationHelper.validateLandmarkIds(startLandmarkId, endLandmarkId);
        // Retrieve start and end landmark data
        Landmarks startLandmark = landmarkRepo.getLandmarkById(startLandmarkId);
        Landmarks endLandmark = landmarkRepo.getLandmarkById(endLandmarkId);

        if (startLandmark == null || endLandmark == null) {
            throw new IllegalArgumentException("Invalid landmark ID(s).");
        }

        // Extract coordinates
        double startLat = startLandmark.getLatitude();
        double startLong = startLandmark.getLongitude();
        double endLat = endLandmark.getLatitude();
        double endLong = endLandmark.getLongitude();

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

    public String fetchRoutePath(double startLat, double startLng, double endLat, double endLng) throws Exception {
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
            System.out.println("Step 4");
//            JSONArray coordinates = geometry.getJSONArray("coordinates");
//            return overviewPolyline.getString("points"); // Encoded polyline
            return geometry.toString();
        }

        throw new Exception("No route found.");
    }

    public int getEstimatedTime(int startLandmarkId, int endLandmarkId, ModeOfTransport modeOfTransport) {
        validationHelper.validateLandmarkIds(startLandmarkId, endLandmarkId);
        // Calculate distance between landmarks
        double distance = calculateDistance(startLandmarkId, endLandmarkId);

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

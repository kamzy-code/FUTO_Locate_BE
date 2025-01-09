package kamzy.io.FutoLocate.repository;

import kamzy.io.FutoLocate.model.Routes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Routes, Integer> {

//    @Query("SELECT r FROM Routes r WHERE r.start_landmark_id = :startLandmarkId AND " +
//            "r.end_landmark_id = :endLandmarkId")
//    List<Routes> getAllRoutesBetweenTwoLandmark(int startLandmarkId, int endLandmarkId);
//
//    @Query("SELECT r FROM Routes r WHERE r.route_id = :route_id")
//    Routes getRouteById(int route_id);
}
